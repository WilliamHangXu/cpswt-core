/*
 * Copyright (c) 2008, Institute for Software Integrated Systems, Vanderbilt University
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose, without fee, and without written agreement is
 * hereby granted, provided that the above copyright notice, the following
 * two paragraphs and the author appear in all copies of this software.
 *
 * IN NO EVENT SHALL THE VANDERBILT UNIVERSITY BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 * OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE VANDERBILT
 * UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * THE VANDERBILT UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 * ON AN "AS IS" BASIS, AND THE VANDERBILT UNIVERSITY HAS NO OBLIGATION TO
 * PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 * 
 * @author Himanshu Neema
 */

package org.cpswt.coa;

/**
 * Represents a Synchronization point in the sequence graph.
 */
public class COASyncPt extends COANode {

	private double _syncTime = 0.0;

	private int _numBranchesToFinish = 0;

	private int _numBranchesFinished = 0;

	public COASyncPt(String nodeName, String uniqueID, double syncTime,
			int numBranchesToFinish) {
		super(nodeName, uniqueID, NODE_TYPE.NODE_SYNC_PT);

		this._syncTime = syncTime;
		this._numBranchesToFinish = numBranchesToFinish;
	}

	@Override
	public String toString() {
		return super.toString() + ", SyncTime: " + _syncTime
				+ ", No. of branches to finish: " + _numBranchesToFinish;
	}

	public double getSyncTime() {
		return _syncTime;
	}

	public int getNumBranchesToFinish() {
		return _numBranchesToFinish;
	}

	public void incrementBranchesFinished() {
		_numBranchesFinished++;
	}

	public int getNumBranchesFinished() {
		return _numBranchesFinished;
	}

	public boolean getIsRequiredNumOfBranchesFinished() {
		return _numBranchesFinished >= _numBranchesToFinish;
	}
}
