package c2w.hla;

import java.util.HashSet;
import java.util.Set;

import hla.rti.*;

/**
 * The FederateObject class implements the FederateObject object in the
 * c2w.hla simulation.
 */
public class FederateObject extends ObjectRoot {

    /**
     * Default constructor -- creates an instance of the FederateObject object
     * class with default attribute values.
     */
    public FederateObject() {
    }

    private static int _FederateHandle_handle;
    private static int _FederateType_handle;
    private static int _FederateHost_handle;
    private static int _FederateIsLateJoiner_handle;


    /**
     * Returns the handle (RTI assigned) of the "FederateHandle" attribute of
     * its containing object class.
     *
     * @return the handle (RTI assigned) of the "FederateHandle" attribute
     */
    public static int get_FederateHandle_handle() {
        return _FederateHandle_handle;
    }

    /**
     * Returns the handle (RTI assigned) of the "FederateType" attribute of
     * its containing object class.
     *
     * @return the handle (RTI assigned) of the "FederateType" attribute
     */
    public static int get_FederateType_handle() {
        return _FederateType_handle;
    }

    /**
     * Returns the handle (RTI assigned) of the "FederateHost" attribute of
     * its containing object class.
     *
     * @return the handle (RTI assigned) of the "FederateHost" attribute
     */
    public static int get_FederateHost_handle() {
        return _FederateHost_handle;
    }

    public static int get_FederateIsLateJoiner_handle() { return _FederateIsLateJoiner_handle; }

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
     * Returns the handle (RTI assigned) of the FederateObject object class.
     * Note: As this is a static method, it is NOT polymorphic, and so, if called on
     * a reference will return the handle of the class pertaining to the reference,\
     * rather than the handle of the class for the instance referred to by the reference.
     * For the polymorphic version of this method, use {@link #getClassHandle()}.
     */
    public static int get_handle() {
        return _handle;
    }

    /**
     * Returns the fully-qualified (dot-delimited) name of the FederateObject
     * object class.
     * Note: As this is a static method, it is NOT polymorphic, and so, if called on
     * a reference will return the name of the class pertaining to the reference,\
     * rather than the name of the class for the instance referred to by the reference.
     * For the polymorphic version of this method, use {@link #getClassName()}.
     */
    public static String get_class_name() {
        return "ObjectRoot.Manager.Federate";
    }

    /**
     * Returns the simple name (the last name in the dot-delimited fully-qualified
     * class name) of the FederateObject object class.
     */
    public static String get_simple_class_name() {
        return "FederateObject";
    }

    private static Set<String> _datamemberNames = new HashSet<String>();
    private static Set<String> _allDatamemberNames = new HashSet<String>();

    /**
     * Returns a set containing the names of all of the non-hidden attributes in the
     * FederateObject object class.
     * Note: As this is a static method, it is NOT polymorphic, and so, if called on
     * a reference will return a set of parameter names pertaining to the reference,\
     * rather than the parameter names of the class for the instance referred to by
     * the reference.  For the polymorphic version of this method, use
     * {@link #getAttributeNames()}.
     */
    public static Set<String> get_attribute_names() {
        return new HashSet<String>(_datamemberNames);
    }


    /**
     * Returns a set containing the names of all of the attributes in the
     * FederateObject object class.
     * Note: As this is a static method, it is NOT polymorphic, and so, if called on
     * a reference will return a set of parameter names pertaining to the reference,\
     * rather than the parameter names of the class for the instance referred to by
     * the reference.  For the polymorphic version of this method, use
     * {@link #getAttributeNames()}.
     */
    public static Set<String> get_all_attribute_names() {
        return new HashSet<String>(_allDatamemberNames);
    }


    private static AttributeHandleSet _publishedAttributeHandleSet;
    private static Set<String> _publishAttributeNameSet = new HashSet<String>();

    private static AttributeHandleSet _subscribedAttributeHandleSet;
    private static Set<String> _subscribeAttributeNameSet = new HashSet<String>();


    static {
        _classNameSet.add("ObjectRoot.Manager.Federate");
        _classNameClassMap.put("ObjectRoot.Manager.Federate", FederateObject.class);

        _datamemberClassNameSetMap.put("ObjectRoot.Manager.Federate", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.Manager.Federate", _allDatamemberNames);


        _datamemberNames.add("FederateHandle");
        _datamemberNames.add("FederateType");
        _datamemberNames.add("FederateHost");
        _datamemberNames.add("FederateIsLateJoiner");


        _allDatamemberNames.add("FederateHandle");
        _allDatamemberNames.add("FederateType");
        _allDatamemberNames.add("FederateHost");
        _allDatamemberNames.add("FederateIsLateJoiner");


        _datamemberTypeMap.put("FederateHandle", "int");
        _datamemberTypeMap.put("FederateType", "String");
        _datamemberTypeMap.put("FederateHost", "String");
        _datamemberTypeMap.put("FederateIsLateJoiner", "boolean");


        _classNamePublishAttributeNameMap.put("ObjectRoot.Manager.Federate", _publishAttributeNameSet);
        _publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        _classNamePublishedAttributeMap.put("ObjectRoot.Manager.Federate", _publishedAttributeHandleSet);

        _classNameSubscribeAttributeNameMap.put("ObjectRoot.Manager.Federate", _subscribeAttributeNameSet);
        _subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        _classNameSubscribedAttributeMap.put("ObjectRoot.Manager.Federate", _subscribedAttributeHandleSet);


    }


    private static String initErrorMessage = "Error:  ObjectRoot.Manager.Federate:  could not initialize:  ";

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while (isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.Manager.Federate");
                isNotInitialized = false;
            } catch (FederateNotExecutionMember f) {
                System.err.println(initErrorMessage + "Federate Not Execution Member");
                f.printStackTrace();
                return;
            } catch (NameNotFound n) {
                System.err.println(initErrorMessage + "Name Not Found");
                n.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(50);
                } catch (Exception e1) {
                }
            }
        }

        _classNameHandleMap.put("ObjectRoot.Manager.Federate", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.Manager.Federate");
        _classHandleSimpleNameMap.put(get_handle(), "FederateObject");


        isNotInitialized = true;
        while (isNotInitialized) {
            try {

                _FederateHandle_handle = rti.getAttributeHandle("FederateHandle", get_handle());
                _FederateType_handle = rti.getAttributeHandle("FederateType", get_handle());
                _FederateHost_handle = rti.getAttributeHandle("FederateHost", get_handle());
                _FederateIsLateJoiner_handle = rti.getAttributeHandle("FederateIsLateJoiner", get_handle());
                isNotInitialized = false;
            } catch (FederateNotExecutionMember f) {
                System.err.println(initErrorMessage + "Federate Not Execution Member");
                f.printStackTrace();
                return;
            } catch (ObjectClassNotDefined i) {
                System.err.println(initErrorMessage + "Object Class Not Defined");
                i.printStackTrace();
                return;
            } catch (NameNotFound n) {
                System.err.println(initErrorMessage + "Name Not Found");
                n.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(50);
                } catch (Exception e1) {
                }
            }
        }


        _datamemberNameHandleMap.put("ObjectRoot.Manager.Federate,FederateHandle", get_FederateHandle_handle());
        _datamemberNameHandleMap.put("ObjectRoot.Manager.Federate,FederateType", get_FederateType_handle());
        _datamemberNameHandleMap.put("ObjectRoot.Manager.Federate,FederateHost", get_FederateHost_handle());
        _datamemberNameHandleMap.put("ObjectRoot.Manager.Federate,FederateIsLateJoiner", get_FederateIsLateJoiner_handle());


        _datamemberHandleNameMap.put(get_FederateHandle_handle(), "FederateHandle");
        _datamemberHandleNameMap.put(get_FederateType_handle(), "FederateType");
        _datamemberHandleNameMap.put(get_FederateHost_handle(), "FederateHost");
        _datamemberHandleNameMap.put(get_FederateIsLateJoiner_handle(), "FederateIsLateJoiner");

    }


    private static boolean _isPublished = false;
    private static String publishErrorMessage = "Error:  ObjectRoot.Manager.Federate:  could not publish:  ";

    /**
     * Publishes the FederateObject object class for a federate.
     *
     * @param rti handle to the RTI
     */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);


        _publishedAttributeHandleSet.empty();
        for (String attributeName : _publishAttributeNameSet) {
            try {
                _publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Manager.Federate," + attributeName));
            } catch (Exception e) {
                System.err.println(publishErrorMessage + "Could not publish \"" + attributeName + "\" attribute.");
            }
        }


        synchronized (rti) {
            boolean isNotPublished = true;
            while (isNotPublished) {
                try {
                    rti.publishObjectClass(get_handle(), _publishedAttributeHandleSet);
                    isNotPublished = false;
                } catch (FederateNotExecutionMember f) {
                    System.err.println(publishErrorMessage + "Federate Not Execution Member");
                    f.printStackTrace();
                    return;
                } catch (ObjectClassNotDefined i) {
                    System.err.println(publishErrorMessage + "Object Class Not Defined");
                    i.printStackTrace();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e1) {
                    }
                }
            }
        }

        _isPublished = true;
    }

    private static String unpublishErrorMessage = "Error:  ObjectRoot.Manager.Federate:  could not unpublish:  ";

    /**
     * Unpublishes the FederateObject object class for a federate.
     *
     * @param rti handle to the RTI
     */
    public static void unpublish(RTIambassador rti) {
        if (!_isPublished) return;

        init(rti);
        synchronized (rti) {
            boolean isNotUnpublished = true;
            while (isNotUnpublished) {
                try {
                    rti.unpublishObjectClass(get_handle());
                    isNotUnpublished = false;
                } catch (FederateNotExecutionMember f) {
                    System.err.println(unpublishErrorMessage + "Federate Not Execution Member");
                    f.printStackTrace();
                    return;
                } catch (ObjectClassNotDefined i) {
                    System.err.println(unpublishErrorMessage + "Object Class Not Defined");
                    i.printStackTrace();
                    return;
                } catch (ObjectClassNotPublished i) {
                    System.err.println(unpublishErrorMessage + "Object Class Not Published");
                    i.printStackTrace();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e1) {
                    }
                }
            }
        }

        _isPublished = false;
    }

    private static boolean _isSubscribed = false;
    private static String subscribeErrorMessage = "Error:  ObjectRoot.Manager.Federate:  could not subscribe:  ";

    /**
     * Subscribes a federate to the FederateObject object class.
     *
     * @param rti handle to the RTI
     */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        _subscribedAttributeHandleSet.empty();
        for (String attributeName : _subscribeAttributeNameSet) {
            try {
                _subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Manager.Federate," + attributeName));
            } catch (Exception e) {
                System.err.println(subscribeErrorMessage + "Could not subscribe to \"" + attributeName + "\" attribute.");
            }
        }


        synchronized (rti) {
            boolean isNotSubscribed = true;
            while (isNotSubscribed) {
                try {
                    rti.subscribeObjectClassAttributes(get_handle(), _subscribedAttributeHandleSet);
                    isNotSubscribed = false;
                } catch (FederateNotExecutionMember f) {
                    System.err.println(subscribeErrorMessage + "Federate Not Execution Member");
                    f.printStackTrace();
                    return;
                } catch (ObjectClassNotDefined i) {
                    System.err.println(subscribeErrorMessage + "Object Class Not Defined");
                    i.printStackTrace();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e1) {
                    }
                }
            }
        }

        _isSubscribed = true;
    }

    private static String unsubscribeErrorMessage = "Error:  ObjectRoot.Manager.Federate:  could not unsubscribe:  ";

    /**
     * Unsubscribes a federate from the FederateObject object class.
     *
     * @param rti handle to the RTI
     */
    public static void unsubscribe(RTIambassador rti) {
        if (!_isSubscribed) return;

        init(rti);
        synchronized (rti) {
            boolean isNotUnsubscribed = true;
            while (isNotUnsubscribed) {
                try {
                    rti.unsubscribeObjectClass(get_handle());
                    isNotUnsubscribed = false;
                } catch (FederateNotExecutionMember f) {
                    System.err.println(unsubscribeErrorMessage + "Federate Not Execution Member");
                    f.printStackTrace();
                    return;
                } catch (ObjectClassNotDefined i) {
                    System.err.println(unsubscribeErrorMessage + "Object Class Not Defined");
                    i.printStackTrace();
                    return;
                } catch (ObjectClassNotSubscribed i) {
                    System.err.println(unsubscribeErrorMessage + "Object Class Not Subscribed");
                    i.printStackTrace();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e1) {
                    }
                }
            }
        }

        _isSubscribed = false;
    }

    /**
     * Return true if "handle" is equal to the handle (RTI assigned) of this class
     * (that is, the FederateObject object class).
     *
     * @param handle handle to compare to the value of the handle (RTI assigned) of
     *               this class (the FederateObject object class).
     * @return "true" if "handle" matches the value of the handle of this class
     * (that is, the FederateObject object class).
     */
    public static boolean match(int handle) {
        return handle == get_handle();
    }

    /**
     * Returns the handle (RTI assigned) of this instance's object class .
     *
     * @return the handle (RTI assigned) if this instance's object class
     */
    public int getClassHandle() {
        return get_handle();
    }

    /**
     * Returns the fully-qualified (dot-delimited) name of this instance's object class.
     *
     * @return the fully-qualified (dot-delimited) name of this instance's object class
     */
    public String getClassName() {
        return get_class_name();
    }

    /**
     * Returns the simple name (last name in its fully-qualified dot-delimited name)
     * of this instance's object class.
     *
     * @return the simple name of this instance's object class
     */
    public String getSimpleClassName() {
        return get_simple_class_name();
    }

    /**
     * Returns a set containing the names of all of the non-hiddenattributes of an
     * object class instance.
     *
     * @return set containing the names of all of the attributes of an
     * object class instance
     */
    public Set<String> getAttributeNames() {
        return get_attribute_names();
    }

    /**
     * Returns a set containing the names of all of the attributes of an
     * object class instance.
     *
     * @return set containing the names of all of the attributes of an
     * object class instance
     */
    public Set<String> getAllAttributeNames() {
        return get_all_attribute_names();
    }

    /**
     * Publishes the object class of this instance of the class for a federate.
     *
     * @param rti handle to the RTI
     */
    public void publishObject(RTIambassador rti) {
        publish(rti);
    }

    /**
     * Unpublishes the object class of this instance of this class for a federate.
     *
     * @param rti handle to the RTI
     */
    public void unpublishObject(RTIambassador rti) {
        unpublish(rti);
    }

    /**
     * Subscribes a federate to the object class of this instance of this class.
     *
     * @param rti handle to the RTI
     */
    public void subscribeObject(RTIambassador rti) {
        subscribe(rti);
    }

    /**
     * Unsubscribes a federate from the object class of this instance of this class.
     *
     * @param rti handle to the RTI
     */
    public void unsubscribeObject(RTIambassador rti) {
        unsubscribe(rti);
    }


    /**
     * Returns a data structure containing the handles of all attributes for this object
     * class that are currently marked for subscription.  To actually subscribe to these
     * attributes, a federate must call <objectclassname>.subscribe( RTIambassador rti ).
     *
     * @return data structure containing the handles of all attributes for this object
     * class that are currently marked for subscription
     */
    public AttributeHandleSet getSubscribedAttributeHandleSet() {
        return _subscribedAttributeHandleSet;
    }


    public String toString() {
        return "FederateObject("


                + "FederateHandle:" + get_FederateHandle()
                + "," + "FederateType:" + get_FederateId()
                + "," + "FederateHost:" + get_FederateHost()
                + "," + "FederateIsLateJoiner:" + get_FederateIsLateJoiner()
                + ")";
    }


    /**
     * Publishes the "FederateHandle" attribute of the attribute's containing object
     * class for a federate.
     * Note:  This method only marks the "FederateHandle" attribute for publication.
     * To actually publish the attribute, the federate must (re)publish its containing
     * object class.
     * (using <objectClassName>.publish( RTIambassador rti ) ).
     */
    public static void publish_FederateHandle() {
        _publishAttributeNameSet.add("FederateHandle");
    }

    /**
     * Unpublishes the "FederateHandle" attribute of the attribute's containing object
     * class for a federate.
     * Note:  This method only marks the "FederateHandle" attribute for unpublication.
     * To actually publish the attribute, the federate must (re)publish its containing
     * object class.
     * (using <objectClassName>.publish( RTIambassador rti ) ).
     */
    public static void unpublish_FederateHandle() {
        _publishAttributeNameSet.remove("FederateHandle");
    }

    /**
     * Subscribes a federate to the "FederateHandle" attribute of the attribute's
     * containing object class.
     * Note:  This method only marks the "FederateHandle" attribute for subscription.
     * To actually subscribe to the attribute, the federate must (re)subscribe to its
     * containing object class.
     * (using <objectClassName>.subscribe( RTIambassador rti ) ).
     */
    public static void subscribe_FederateHandle() {
        _subscribeAttributeNameSet.add("FederateHandle");
    }

    /**
     * Unsubscribes a federate from the "FederateHandle" attribute of the attribute's
     * containing object class.
     * Note:  This method only marks the "FederateHandle" attribute for unsubscription.
     * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
     * containing object class.
     * (using <objectClassName>.subscribe( RTIambassador rti ) ).
     */
    public static void unsubscribe_FederateHandle() {
        _subscribeAttributeNameSet.remove("FederateHandle");
    }


    /**
     * Publishes the "FederateType" attribute of the attribute's containing object
     * class for a federate.
     * Note:  This method only marks the "FederateType" attribute for publication.
     * To actually publish the attribute, the federate must (re)publish its containing
     * object class.
     * (using <objectClassName>.publish( RTIambassador rti ) ).
     */
    public static void publish_FederateType() {
        _publishAttributeNameSet.add("FederateType");
    }

    /**
     * Unpublishes the "FederateType" attribute of the attribute's containing object
     * class for a federate.
     * Note:  This method only marks the "FederateType" attribute for unpublication.
     * To actually publish the attribute, the federate must (re)publish its containing
     * object class.
     * (using <objectClassName>.publish( RTIambassador rti ) ).
     */
    public static void unpublish_FederateType() {
        _publishAttributeNameSet.remove("FederateType");
    }

    /**
     * Subscribes a federate to the "FederateType" attribute of the attribute's
     * containing object class.
     * Note:  This method only marks the "FederateType" attribute for subscription.
     * To actually subscribe to the attribute, the federate must (re)subscribe to its
     * containing object class.
     * (using <objectClassName>.subscribe( RTIambassador rti ) ).
     */
    public static void subscribe_FederateType() {
        _subscribeAttributeNameSet.add("FederateType");
    }

    /**
     * Unsubscribes a federate from the "FederateType" attribute of the attribute's
     * containing object class.
     * Note:  This method only marks the "FederateType" attribute for unsubscription.
     * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
     * containing object class.
     * (using <objectClassName>.subscribe( RTIambassador rti ) ).
     */
    public static void unsubscribe_FederateType() {
        _subscribeAttributeNameSet.remove("FederateType");
    }


    /**
     * Publishes the "FederateHost" attribute of the attribute's containing object
     * class for a federate.
     * Note:  This method only marks the "FederateHost" attribute for publication.
     * To actually publish the attribute, the federate must (re)publish its containing
     * object class.
     * (using <objectClassName>.publish( RTIambassador rti ) ).
     */
    public static void publish_FederateHost() {
        _publishAttributeNameSet.add("FederateHost");
    }

    /**
     * Unpublishes the "FederateHost" attribute of the attribute's containing object
     * class for a federate.
     * Note:  This method only marks the "FederateHost" attribute for unpublication.
     * To actually publish the attribute, the federate must (re)publish its containing
     * object class.
     * (using <objectClassName>.publish( RTIambassador rti ) ).
     */
    public static void unpublish_FederateHost() {
        _publishAttributeNameSet.remove("FederateHost");
    }

    /**
     * Subscribes a federate to the "FederateHost" attribute of the attribute's
     * containing object class.
     * Note:  This method only marks the "FederateHost" attribute for subscription.
     * To actually subscribe to the attribute, the federate must (re)subscribe to its
     * containing object class.
     * (using <objectClassName>.subscribe( RTIambassador rti ) ).
     */
    public static void subscribe_FederateHost() {
        _subscribeAttributeNameSet.add("FederateHost");
    }

    /**
     * Unsubscribes a federate from the "FederateHost" attribute of the attribute's
     * containing object class.
     * Note:  This method only marks the "FederateHost" attribute for unsubscription.
     * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
     * containing object class.
     * (using <objectClassName>.subscribe( RTIambassador rti ) ).
     */
    public static void unsubscribe_FederateHost() {
        _subscribeAttributeNameSet.remove("FederateHost");
    }

    public static void publish_FederateIsLateJoiner() { _publishAttributeNameSet.add("FederateIsLateJoiner"); }
    public static void unpublish_FederateIsLateJoiner() { _publishAttributeNameSet.remove("FederateIsLateJoiner"); }
    public static void subscribe_FederateIsLateJoiner() { _subscribeAttributeNameSet.add("FederateIsLateJoiner"); }
    public static void unsubscribe_FederateIsLateJoiner() { _subscribeAttributeNameSet.remove("FederateIsLateJoiner"); }

    private Attribute<Integer> _FederateHandle =
            new Attribute<Integer>(new Integer(0));

    /**
     * Set the value of the "FederateHandle" attribute to "value" for this object.
     *
     * @param value the new value for the "FederateHandle" attribute
     */
    public void set_FederateHandle(int value) {
        _FederateHandle.setValue(value);
        _FederateHandle.setTime(getTime());
    }

    /**
     * Returns the value of the "FederateHandle" attribute of this object.
     *
     * @return the value of the "FederateHandle" attribute
     */
    public int get_FederateHandle() {
        return _FederateHandle.getValue();
    }

    /**
     * Returns the current timestamp of the "FederateHandle" attribute of this object.
     *
     * @return the current timestamp of the "FederateHandle" attribute
     */
    public double get_FederateHandle_time() {
        return _FederateHandle.getTime();
    }


    private Attribute<String> _FederateId =
            new Attribute<String>("");

    /**
     * Set the value of the "FederateType" attribute to "value" for this object.
     *
     * @param value the new value for the "FederateType" attribute
     */
    public void set_FederateId(String value) {
        _FederateId.setValue(value);
        _FederateId.setTime(getTime());
    }

    /**
     * Returns the value of the "FederateType" attribute of this object.
     *
     * @return the value of the "FederateType" attribute
     */
    public String get_FederateId() {
        return _FederateId.getValue();
    }

    /**
     * Returns the current timestamp of the "FederateType" attribute of this object.
     *
     * @return the current timestamp of the "FederateType" attribute
     */
    public double get_FederateId_time() {
        return _FederateId.getTime();
    }


    private Attribute<String> _FederateHost =
            new Attribute<String>("");

    /**
     * Set the value of the "FederateHost" attribute to "value" for this object.
     *
     * @param value the new value for the "FederateHost" attribute
     */
    public void set_FederateHost(String value) {
        _FederateHost.setValue(value);
        _FederateHost.setTime(getTime());
    }

    /**
     * Returns the value of the "FederateHost" attribute of this object.
     *
     * @return the value of the "FederateHost" attribute
     */
    public String get_FederateHost() {
        return _FederateHost.getValue();
    }

    /**
     * Returns the current timestamp of the "FederateHost" attribute of this object.
     *
     * @return the current timestamp of the "FederateHost" attribute
     */
    public double get_FederateHost_time() {
        return _FederateHost.getTime();
    }

    private Attribute<Boolean> _FederateIsLateJoiner = new Attribute<>(false);

    public void set_FederateIsLateJoiner(boolean value) {
        _FederateIsLateJoiner.setValue(value);
        _FederateIsLateJoiner.setTime(getTime());
    }
    public boolean get_FederateIsLateJoiner() {
        return _FederateIsLateJoiner.getValue();
    }

    protected FederateObject(ReflectedAttributes datamemberMap, boolean initFlag) {
        super(datamemberMap, false);
        if (initFlag) setAttributes(datamemberMap);
    }

    protected FederateObject(ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag) {
        super(datamemberMap, logicalTime, false);
        if (initFlag) setAttributes(datamemberMap);
    }


    /**
     * Creates an instance of the FederateObject object class, using
     * "datamemberMap" to initialize its attribute values.
     * "datamemberMap" is usually acquired as an argument to an RTI federate
     * callback method, such as "receiveInteraction".
     *
     * @param datamemberMap data structure containing initial values for the
     *                      attributes of this new FederateObject object class instance
     */
    public FederateObject(ReflectedAttributes datamemberMap) {
        this(datamemberMap, true);
    }

    /**
     * Like {@link #FederateObject(ReflectedAttributes datamemberMap)}, except this
     * new FederateObject object class instance is given a timestamp of
     * "logicalTime".
     *
     * @param datamemberMap data structure containing initial values for the
     *                      attributes of this new FederateObject object class instance
     * @param logicalTime   timestamp for this new FederateObject object class
     *                      instance
     */
    public FederateObject(ReflectedAttributes datamemberMap, LogicalTime logicalTime) {
        this(datamemberMap, logicalTime, true);
    }

    /**
     * Creates a new FederateObject object class instance that is a duplicate
     * of the instance referred to by FederateObject_var.
     *
     * @param FederateObject_var FederateObject object class instance of which
     *                           this newly created FederateObject object class instance will be a
     *                           duplicate
     */
    public FederateObject(FederateObject FederateObject_var) {
        super(FederateObject_var);


        set_FederateHandle(FederateObject_var.get_FederateHandle());
        set_FederateId(FederateObject_var.get_FederateId());
        set_FederateHost(FederateObject_var.get_FederateHost());
        set_FederateIsLateJoiner(FederateObject_var.get_FederateIsLateJoiner());
    }


    /**
     * Returns the value of the attribute whose name is "datamemberName"
     * for this object.
     *
     * @param datamemberName name of attribute whose value is to be
     *                       returned
     * @return value of the attribute whose name is "datamemberName"
     * for this object
     */
    public Object getAttribute(String datamemberName) {


        if ("FederateHandle".equals(datamemberName)) return get_FederateHandle();
        else if ("FederateType".equals(datamemberName)) return get_FederateId();
        else if ("FederateHost".equals(datamemberName)) return get_FederateHost();
        else if("FederateIsLateJoiner".equals(datamemberName)) return get_FederateIsLateJoiner();
        else return super.getAttribute(datamemberName);
    }

    /**
     * Returns the value of the attribute whose handle (RTI assigned)
     * is "datamemberHandle" for this object.
     *
     * @param datamemberHandle handle (RTI assigned) of attribute whose
     *                         value is to be returned
     * @return value of the attribute whose handle (RTI assigned) is
     * "datamemberHandle" for this object
     */
    public Object getAttribute(int datamemberHandle) {


        if (get_FederateHandle_handle() == datamemberHandle) return get_FederateHandle();
        else if (get_FederateType_handle() == datamemberHandle) return get_FederateId();
        else if (get_FederateHost_handle() == datamemberHandle) return get_FederateHost();
        else if (get_FederateIsLateJoiner_handle() == datamemberHandle) return get_FederateIsLateJoiner();
        else return super.getAttribute(datamemberHandle);
    }

    protected boolean setAttributeAux(int param_handle, String val) {
        boolean retval = true;


        if (param_handle == get_FederateHandle_handle()) set_FederateHandle(Integer.parseInt(val));
        else if (param_handle == get_FederateType_handle()) set_FederateId(val);
        else if (param_handle == get_FederateHost_handle()) set_FederateHost(val);
        else if (param_handle == get_FederateIsLateJoiner_handle()) set_FederateIsLateJoiner(Boolean.parseBoolean(val));
        else retval = super.setAttributeAux(param_handle, val);

        return retval;
    }

    protected boolean setAttributeAux(String datamemberName, String val) {
        boolean retval = true;


        if ("FederateHandle".equals(datamemberName)) set_FederateHandle(Integer.parseInt(val));
        else if ("FederateType".equals(datamemberName)) set_FederateId(val);
        else if ("FederateHost".equals(datamemberName)) set_FederateHost(val);
        else if ("FederateIsLateJoiner".equals(datamemberName)) set_FederateIsLateJoiner(Boolean.parseBoolean(val));
        else retval = super.setAttributeAux(datamemberName, val);

        return retval;
    }

    protected boolean setAttributeAux(String datamemberName, Object val) {
        boolean retval = true;


        if ("FederateHandle".equals(datamemberName)) set_FederateHandle((Integer) val);
        else if ("FederateType".equals(datamemberName)) set_FederateId((String) val);
        else if ("FederateHost".equals(datamemberName)) set_FederateHost((String) val);
        else if("FederateIsLateJoiner".equals(datamemberName)) set_FederateIsLateJoiner((Boolean) val);
        else retval = super.setAttributeAux(datamemberName, val);

        return retval;
    }

    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = super.createSuppliedDatamembers(force);


        boolean isPublished = false;


        try {
            isPublished = _publishedAttributeHandleSet.isMember(get_FederateHandle_handle());
        } catch (Exception e) {
            System.err.println("ERROR:  ObjectRoot.Manager.Federate.createSuppliedAttributes:  could not determine if FederateHandle is published.");
            isPublished = false;
        }
        if (isPublished && _FederateHandle.shouldBeUpdated(force)) {
            datamembers.add(get_FederateHandle_handle(), Integer.toString(get_FederateHandle()).getBytes());
            _FederateHandle.setHasBeenUpdated();
        }
        try {
            isPublished = _publishedAttributeHandleSet.isMember(get_FederateType_handle());
        } catch (Exception e) {
            System.err.println("ERROR:  ObjectRoot.Manager.Federate.createSuppliedAttributes:  could not determine if FederateType is published.");
            isPublished = false;
        }
        if (isPublished && _FederateId.shouldBeUpdated(force)) {
            datamembers.add(get_FederateType_handle(), get_FederateId().getBytes());
            _FederateId.setHasBeenUpdated();
        }
        try {
            isPublished = _publishedAttributeHandleSet.isMember(get_FederateHost_handle());
        } catch (Exception e) {
            System.err.println("ERROR:  ObjectRoot.Manager.Federate.createSuppliedAttributes:  could not determine if FederateHost is published.");
            isPublished = false;
        }
        if (isPublished && _FederateHost.shouldBeUpdated(force)) {
            datamembers.add(get_FederateHost_handle(), get_FederateHost().getBytes());
            _FederateHost.setHasBeenUpdated();
        }
        try {
            isPublished = _publishedAttributeHandleSet.isMember(get_FederateIsLateJoiner_handle());
        } catch (Exception e) {
            System.err.println("ERROR:  ObjectRoot.Manager.Federate.createSuppliedAttributes:  could not determine if FederateIsLateJoiner is published.");
            isPublished = false;
        }
        if (isPublished && _FederateIsLateJoiner.shouldBeUpdated(force)) {
            datamembers.add(get_FederateIsLateJoiner_handle(), Boolean.toString(get_FederateIsLateJoiner()).getBytes());
            _FederateIsLateJoiner.setHasBeenUpdated();
        }
        return datamembers;
    }


    public void copyFrom(Object object) {
        super.copyFrom(object);
        if (object instanceof FederateObject) {
            FederateObject data = (FederateObject) object;


            _FederateHandle = data._FederateHandle;
            _FederateId = data._FederateId;
            _FederateHost = data._FederateHost;
            _FederateIsLateJoiner = data._FederateIsLateJoiner;

        }
    }
}
