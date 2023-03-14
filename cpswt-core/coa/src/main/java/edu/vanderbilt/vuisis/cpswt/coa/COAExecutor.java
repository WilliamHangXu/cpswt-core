/*
 * Certain portions of this software are Copyright (C) 2006-present
 * Vanderbilt University, Institute for Software Integrated Systems.
 *
 * Certain portions of this software are contributed as a public service by
 * The National Institute of Standards and Technology (NIST) and are not
 * subject to U.S. Copyright.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above Vanderbilt University copyright notice, NIST contribution
 * notice and this permission and disclaimer notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE. THE AUTHORS OR COPYRIGHT HOLDERS SHALL NOT HAVE
 * ANY OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS,
 * OR MODIFICATIONS.
 */

package edu.vanderbilt.vuisis.cpswt.coa;

import edu.vanderbilt.vuisis.cpswt.hla.InteractionRootInterface;
import edu.vanderbilt.vuisis.cpswt.hla.SynchronizedFederate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import edu.vanderbilt.vuisis.cpswt.coa.node.COAAction;
import edu.vanderbilt.vuisis.cpswt.coa.node.COAAwaitN;
import edu.vanderbilt.vuisis.cpswt.coa.node.COADuration;
import edu.vanderbilt.vuisis.cpswt.coa.node.COAFork;
import edu.vanderbilt.vuisis.cpswt.coa.node.COANode;
import edu.vanderbilt.vuisis.cpswt.coa.node.COAOutcome;
import edu.vanderbilt.vuisis.cpswt.coa.node.COAOutcomeFilter;
import edu.vanderbilt.vuisis.cpswt.coa.node.COAProbabilisticChoice;
import edu.vanderbilt.vuisis.cpswt.coa.node.COARandomDuration;
import edu.vanderbilt.vuisis.cpswt.coa.node.COASyncPoint;
import edu.vanderbilt.vuisis.cpswt.coa.node.COANodeType;
import edu.vanderbilt.vuisis.cpswt.hla.InteractionRoot;
import edu.vanderbilt.vuisis.cpswt.hla.InteractionRoot_p.C2WInteractionRoot;
import edu.vanderbilt.vuisis.cpswt.hla.InteractionRoot_p.C2WInteractionRoot_p.SimulationControl_p.SimEnd;

import java.lang.reflect.Method;
import java.util.*;

/**
 * COA executor for FederationManager
 */
public class COAExecutor {
    private static final Logger logger = LogManager.getLogger(COAExecutor.class);

    private COAGraph _coaGraph = new COAGraph();

    // Cache class and methods for COAOutcomeFilter evaluation
    private Class<?> outcomeFilterEvaluatorClass = null;
    private final HashMap<COAOutcomeFilter, Method> _outcomeFilter2EvalMethodMap = new HashMap<>();

    private final String federationId;
    private final String federateId;
    private final double lookahead;
    private final boolean terminateOnCoaFinish;
    private SynchronizedFederate synchronizedFederate;

    private final Map<String, ArrayList<ArrivedInteraction>> _arrived_interactions = new HashMap<>();

    private COAExecutorEventListener coaExecutorEventListener;
    public void setCoaExecutorEventListener(COAExecutorEventListener listener) {
        this.coaExecutorEventListener = listener;
    }

    public COAExecutor(
            String federationId,
            String federateId,
            double lookahead,
            boolean terminateOnCoaFinish,
            SynchronizedFederate synchronizedFederate
    ) {
        this.federationId = federationId;
        this.federateId = federateId;
        this.lookahead = lookahead;
        this.terminateOnCoaFinish = terminateOnCoaFinish;
        this.synchronizedFederate = synchronizedFederate;
    }

    public void setCOAGraph(COAGraph graph) {
        this._coaGraph = graph;
    }

    public void setSynchronizedFederate(SynchronizedFederate synchronizedFederate) {
        this.synchronizedFederate = synchronizedFederate;
    }

    public void initializeCOAGraph() {
    // this._coaGraph.setCurrentRootNodesAsActive();
        this._coaGraph.initialize(this.federationId, this.synchronizedFederate.getRTI());
    }

    private void terminateSimulation() {
        if(this.coaExecutorEventListener != null) {
            this.coaExecutorEventListener.onTerminateRequested();
        }
    }
    private double getCurrentTime() {
        if(this.coaExecutorEventListener != null) {
            return this.coaExecutorEventListener.onCurrentTimeRequested();
        }
        return 0.0;
    }

    private void executeCOAAction(COAAction nodeAction) {
        // Create interaction to be sent
        logger.trace("COAExecutor:executeCOAAction: Trying to executed node: {}", nodeAction);
        String interactionClassName = nodeAction.getInteractionClassName();
        logger.trace("COAExecutor:executeCOAAction: Got interaction class name: {}... now trying to create interaction..", interactionClassName);

        InteractionRoot interactionRoot = InteractionRoot.create_interaction(interactionClassName);
        interactionRoot.publishInteraction(synchronizedFederate.getRTI());
        for(
                Map.Entry<InteractionRootInterface.ClassAndPropertyName, Object> entry :
                nodeAction.getNameValueParamPairs().entrySet()
        ) {
            interactionRoot.setParameter(entry.getKey(), entry.getValue());
        }

        // First check for simulation termination
        if (SimEnd.match(interactionRoot.getClassHandle())) {
            terminateSimulation();
        }


        // Create timestamp for the interaction
        double tmin = getCurrentTime() + lookahead + (lookahead / 10000.0);

        // Send the interaction
        try {
            C2WInteractionRoot.update_federate_sequence(interactionRoot, this.federateId);
            synchronizedFederate.sendInteraction(interactionRoot, tmin);
            logger.info("Successfully sent interaction '{}' at time '{}'", interactionClassName, tmin);
        } catch (Exception e) {
            logger.error("Failed to send interaction: " + interactionRoot);
            logger.error(e);
        }
    }

    public void executeCOAGraph() {
        HashSet<COANode> currentRootNodes = new HashSet<COANode>(_coaGraph.getCurrentRootNodes());

        // If at any point, there are no COA nodes remaining to execute, and
        // the experiment was configured to terminate when all COA nodes have
        // been executed, terminate the federation.
        if (currentRootNodes.size() == 0 && terminateOnCoaFinish) {
            terminateSimulation();
        }


        // There may be COA nodes still to be executed, see if some root nodes
        // can be executed.
        boolean nodeExecuted = false;
        for (COANode n : currentRootNodes) {
            COANodeType nodeType = n.getNodeType();
            if (nodeType == COANodeType.SyncPoint) {
                COASyncPoint nodeSyncPt = (COASyncPoint) n;
                double timeToReachSyncPt = nodeSyncPt.getSyncTime() - getCurrentTime();
                if (timeToReachSyncPt <= 0.0) {
                    // SyncPt reached, mark executed
                    _coaGraph.markNodeExecuted(n, getCurrentTime());
                    logger.trace("COAExecutor:executeCOAGraph: SyncPt node executed: {}", nodeSyncPt);
                    nodeExecuted = true;
                }
            } else if (nodeType == COANodeType.AwaitN) {
                COAAwaitN nodeAwaitN = (COAAwaitN) n;
                if (!nodeAwaitN.getIsRequiredNumOfBranchesFinished()) {
                    logger.trace("AwaitN is not reached, nothing to be done");
                } else {
                    logger.trace("AwaitN reached, mark executed");
                    logger.trace("COAExecutor:executeCOAGraph: AwaitN node executed: {}", nodeAwaitN);
                    _coaGraph.markNodeExecuted(n, getCurrentTime());
                    nodeExecuted = true;
                }
            } else if (nodeType == COANodeType.Dur || nodeType == COANodeType.RandomDur) {
                COADuration nodeDuration = null;
                if (nodeType == COANodeType.Dur) {
                    nodeDuration = (COADuration) n;
                } else {
                    nodeDuration = (COARandomDuration) n;
                }

                if (!nodeDuration.getIsTimerOn()) {
                    logger.trace("Start executing duration element");
                    nodeDuration.startTimer(getCurrentTime());
                } else {
                    // Check if the duration node has executed
                    if (getCurrentTime() >= nodeDuration.getEndTime()) {
                        logger.trace("Duration node finished, mark executed: {}", nodeDuration);
                        _coaGraph.markNodeExecuted(n, getCurrentTime());
                        nodeExecuted = true;
                    }
                }
            } else if (nodeType == COANodeType.Fork) {
                COAFork nodeFork = (COAFork) n;
                boolean isDecisionPoint = nodeFork.isDecisionPoint(); // TODO: handle decision points

                // As of now Fork is always executed as soon as it is encountered
                _coaGraph.markNodeExecuted(n, getCurrentTime());
                logger.trace("COAExecutor:executeCOAGraph: Fork node executed: {}", nodeFork);
                nodeExecuted = true;
            } else if (nodeType == COANodeType.ProbabilisticChoice) {
                COAProbabilisticChoice nodeProbChoice = (COAProbabilisticChoice) n;
                boolean isDecisionPoint = nodeProbChoice.isDecisionPoint(); // TODO: handle decision points

                // As of now Probabilistic Choice is always executed as soon as it is encountered
                _coaGraph.markNodeExecuted(n, getCurrentTime());
                logger.trace("COAExecutor:executeCOAGraph: ProbabilisticChoice node executed: {}", nodeProbChoice);
                nodeExecuted = true;
            } else if (nodeType == COANodeType.Action) {
                COAAction nodeAction = (COAAction) n;

                // As of now Action is always executed as soon as it is encountered
                logger.trace("COAExecutor:executeCOAGraph: Trying to execute action node: {}", nodeAction);
                executeCOAAction(nodeAction);
                logger.trace("COAExecutor:executeCOAGraph: Action node executed: {}", nodeAction);
                _coaGraph.markNodeExecuted(n, getCurrentTime());
                nodeExecuted = true;
            } else if (nodeType == COANodeType.Outcome) {
                COAOutcome nodeOutcome = (COAOutcome) n;
                if (!nodeOutcome.getIsTimerOn()) {
                    // Start executing Outcome element
                    nodeOutcome.startTimer(getCurrentTime());
                } else {
                    boolean outcomeExecutable = checkIfOutcomeExecutableAndUpdateArrivedInteraction(nodeOutcome);
                    logger.trace("COAExecutor:executeCOAGraph: Checking if outcome node is executable: {}", nodeOutcome);
                    if (outcomeExecutable) {
                        _coaGraph.markNodeExecuted(n, getCurrentTime());
                        logger.trace("COAExecutor:executeCOAGraph: Outcome node executed: {}", nodeOutcome);
                        nodeExecuted = true;
                    }
                }
            } else if (nodeType == COANodeType.OutcomeFilter) {
                COAOutcomeFilter outcomeFilter = (COAOutcomeFilter) n;
                COAOutcome outcomeToFilter = outcomeFilter.getOutcome();

                // Update last arrived interaction in the corresponding Outcome node
                checkIfOutcomeExecutableAndUpdateArrivedInteraction(outcomeToFilter);

                boolean filterEvaluation = false;
                if (outcomeToFilter == null) {
                    logger.warn("OutcomeFilter not connected to an Outcome: {}", outcomeFilter);
                    filterEvaluation = true;
                } else {
                    // Evaluate filter, first get evaluator class
                    if (outcomeFilterEvaluatorClass == null) {
                        String class2Load = this.federationId + ".COAOutcomeFilterEvaluator";
                        try {
                            outcomeFilterEvaluatorClass = Class.forName(class2Load);
                            if (outcomeFilterEvaluatorClass == null) {
                                logger.error("Cannot find evaluator class for OutcomeFilter: {}", outcomeFilter);
                            }
                        } catch (Exception e) {
                            logger.error("Exception caught while evaluating OutcomeFilter: {}", outcomeFilter);
                            logger.error(e);
                        }
                    }

                    // Now, get the filter method to invoke for evaluation in the evaluator class
                    if (outcomeFilterEvaluatorClass != null) {
                        Method outcomeFilterEvalMethod = null;
                        if (_outcomeFilter2EvalMethodMap.containsKey(outcomeFilter)) {
                            // Method already exists in the cache, no need to use reflection loading
                            outcomeFilterEvalMethod = _outcomeFilter2EvalMethodMap.get(outcomeFilter);
                        } else {
                            // Method doesn't exist in the cache, use reflection to load it
                            String filterID = outcomeFilter.getId();
                            filterID = filterID.replaceAll("-", "_");
                            String method2Load = "evaluateFilter_" + filterID;

                            try {
                                outcomeFilterEvalMethod = outcomeFilterEvaluatorClass.getMethod(method2Load, COAOutcome.class);
                                if (outcomeFilterEvalMethod == null) {
                                    logger.error("Cannot find evaluation method in {} for OutcomeFilter: {}", outcomeFilterEvaluatorClass.getName(), outcomeFilter);
                                } else {
                                    _outcomeFilter2EvalMethodMap.put(outcomeFilter, outcomeFilterEvalMethod);
                                }
                            } catch (Exception e) {
                                logger.error("Exception caught while finding evaluation method in for OutcomeFilter: {}", outcomeFilterEvaluatorClass.getName(), outcomeFilter);
                                logger.error(e);
                            }
                        }
                        if (outcomeFilterEvalMethod != null) {
                            // Method loaded, now call evaluation function to evaluate OutcomeFilter
                            try {
                                Object retval = outcomeFilterEvalMethod.invoke(null, outcomeToFilter);
                                if (retval instanceof Boolean) {
                                    filterEvaluation = (Boolean) retval;
                                }
                            } catch (Exception e) {
                                logger.error("Exception caught while evaluating OutcomeFilter: {}", outcomeFilter);
                                logger.error(e);
                            }
                        } else {
                            logger.error("ERROR! Failed to load OutcomeFilter evaluation method for OutcomeFilter: {}", outcomeFilter);
                        }
                    }
                }

                if (filterEvaluation) {
                    _coaGraph.markNodeExecuted(n, getCurrentTime());
                    logger.trace("COAExecutor:executeCOAGraph: OutcomeFilter node executed: {}", outcomeFilter);
                    nodeExecuted = true;
                }
                logger.trace("Result of evaluation of filter for outcome: {} = {}. Interaction it contained was: {}",
                        outcomeToFilter.getName(), filterEvaluation, outcomeToFilter.getLastArrivedInteraction());
            }
        }

        if (nodeExecuted) {
            // Some paths were executed, execute more enabled nodes, if any
            executeCOAGraph();
        }

        // Clear arrived interactions that we no longer need to keep in memory
        clearUnusedArrivedInteractionsForOutcomes();
    }

    private boolean checkIfOutcomeExecutableAndUpdateArrivedInteraction(COAOutcome nodeOutcome) {
        // Check if the outcome can be executed
        boolean outcomeExecutable = false;
        if (_arrived_interactions.containsKey(nodeOutcome.getInteractionClassName())) {
            ArrayList<ArrivedInteraction> arrivedIntrs = _arrived_interactions.get(nodeOutcome.getInteractionClassName());
            if (arrivedIntrs != null) {
                for (ArrivedInteraction arrivedIntr : arrivedIntrs) {
                    if (arrivedIntr.getArrivalTime() > nodeOutcome.getAwaitStartTime()) {
                        // Awaited interaction arrived after outcome was initiated
                        logger.trace("Setting last arrived interaction in outcome node {}: {}", nodeOutcome.getName(), arrivedIntr.getInteractionRoot());
                        nodeOutcome.setLastArrivedInteraction(arrivedIntr.getInteractionRoot());
                        outcomeExecutable = true;
                    }
                }
                // We shouldn't clear the arrivedIntrs here because all parallel
                // Outcomes could wait for the same interaction
                // Instead we use clearUnusedArrivedInteractionsForOutcomes()
                // at the end of a step of COA execution
            }
        }

        return outcomeExecutable;
    }

    private void clearUnusedArrivedInteractionsForOutcomes() {
        Collection<ArrayList<ArrivedInteraction>> allArrivedIntrLists = _arrived_interactions.values();
        for (ArrayList<ArrivedInteraction> aArrivedIntrList : allArrivedIntrLists) {
            aArrivedIntrList.clear();
        }
    }

    // This method and arrivalTimes are used by the COA Orchestrator while executing
    // Outcome elements of the COA sequence graph.
    public void updateArrivedInteractions(InteractionRoot interactionRoot) {
        logger.trace("COAExecutor:updateArrivedInteractions: Received interaction: {}", interactionRoot.toString());
        ArrayList<ArrivedInteraction> intrArrivalTimeList;
        String interactionClassName = interactionRoot.getInstanceHlaClassName();
        if (!_arrived_interactions.containsKey(interactionClassName)) {
            intrArrivalTimeList = new ArrayList<>();
            _arrived_interactions.put(interactionClassName, intrArrivalTimeList);
        } else {
            intrArrivalTimeList = _arrived_interactions.get(interactionClassName);
        }

        double time = interactionRoot.getTime() >= 0 ? interactionRoot.getTime() : getCurrentTime();

        ArrivedInteraction arrivedIntr = new ArrivedInteraction(interactionRoot, time);
        intrArrivalTimeList.add(arrivedIntr);
        logger.trace("COAExecutor:updateArrivedInteractions: Adding interaction to arrived list: {}", interactionRoot);
    }
}
