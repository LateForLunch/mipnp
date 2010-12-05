/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.upnp.services;

import domain.upnp.Action;
import domain.upnp.Argument;
import domain.upnp.Service;
import domain.upnp.StateVariable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeroen De Wilde
 */
public class ConnectionManager extends Service {

    public ConnectionManager(){

        //create action list
    List<Action> actions = new ArrayList<Action>();

    //create statevariables
//      <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_ProtocolInfo</name>
//            <dataType>string</dataType>
//        </stateVariable>
    StateVariable s1 = new StateVariable("A_ARG_TYPE_ProtocolInfo","string",false);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_ConnectionStatus</name>
//            <dataType>string</dataType>
//            <allowedValueList>
//                <allowedValue>OK</allowedValue>
//                <allowedValue>ContentFormatMismatch</allowedValue>
//                <allowedValue>InsufficientBandwidth</allowedValue>
//                <allowedValue>UnreliableChannel</allowedValue>
//                <allowedValue>Unknown</allowedValue>
//            </allowedValueList>
//        </stateVariable>
    List<String>allowedValues_s2 = new ArrayList<String>();
    allowedValues_s2.add("OK");
    allowedValues_s2.add("ContentFormatMismatch");
    allowedValues_s2.add("InsufficientBandwidth");
    allowedValues_s2.add("UnreliableChannel");
    allowedValues_s2.add("Unknown");
    StateVariable s2 = new StateVariable("A_ARG_TYPE_ConnectionStatus","string",false);
    s2.setAllowedValueList(allowedValues_s2);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_AVTransportID</name>
//            <dataType>i4</dataType>
//        </stateVariable>
    StateVariable s3 = new StateVariable("A_ARG_TYPE_AVTransportID","i4",false);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_RcsID</name>
//            <dataType>i4</dataType>
//        </stateVariable>
    StateVariable s4 = new StateVariable("A_ARG_TYPE_RcsID","i4",false);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_ConnectionID</name>
//            <dataType>i4</dataType>
//        </stateVariable>
    StateVariable s5 = new StateVariable("A_ARG_TYPE_ConnectionID","i4",false);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_ConnectionManager</name>
//            <dataType>string</dataType>
//        </stateVariable>
    StateVariable s6 = new StateVariable("A_ARG_TYPE_ConnectionManager","string",false);
//        <stateVariable sendEvents="yes">
//            <name>SourceProtocolInfo</name>
//            <dataType>string</dataType>
//        </stateVariable>
    StateVariable s7 = new StateVariable("SourceProtocolInfo","string",true);
//        <stateVariable sendEvents="yes">
//            <name>SinkProtocolInfo</name>
//            <dataType>string</dataType>
//        </stateVariable>
    StateVariable s8 = new StateVariable("SinkProtocolInfo","string",true);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_Direction</name>
//            <dataType>string</dataType>
//            <allowedValueList>
//                <allowedValue>Input</allowedValue>
//                <allowedValue>Output</allowedValue>
//            </allowedValueList>
//        </stateVariable>
//          </stateVariable>
    List<String>allowedValues_s9 = new ArrayList<String>();
    allowedValues_s9.add("Input");
    allowedValues_s9.add("Output");
    StateVariable s9 = new StateVariable("A_ARG_TYPE_Direction","string",false);
    s9.setAllowedValueList(allowedValues_s9);
//        <stateVariable sendEvents="yes">
//            <name>CurrentConnectionIDs</name>
//            <dataType>string</dataType>
//        </stateVariable>
    StateVariable s10 = new StateVariable("CurrentConnectionIDs","string",true);


    //create action "GetCurrentConnectionInfo"
    Action getCurrentConnectionInfo = new Action("GetCurrentConnectionInfo");
    //add arguments to "GetCurrentConnectionInfo"
    getCurrentConnectionInfo.addArgument(new Argument("ConnectionID","in",s5));
    getCurrentConnectionInfo.addArgument(new Argument("RcsID","out",s4));
    getCurrentConnectionInfo.addArgument(new Argument("AVTransportID","out",s3));
    getCurrentConnectionInfo.addArgument(new Argument("ProtocolInfo","out",s1));
    getCurrentConnectionInfo.addArgument(new Argument("PeerConnectionManager","out",s6));
    getCurrentConnectionInfo.addArgument(new Argument("PeerConnectionID","out",s5));
    getCurrentConnectionInfo.addArgument(new Argument("Direction","out",s9));
    getCurrentConnectionInfo.addArgument(new Argument("Status","out",s2));
    //add to action list
    actions.add(getCurrentConnectionInfo);



    //create action "GetProtocolInfo"
    Action getProtocolInfo = new Action("GetProtocolInfo");
    //add arguments to "GetProtocolInfo"
    getProtocolInfo.addArgument(new Argument("Source","out",s7));
    getProtocolInfo.addArgument(new Argument("Sink","out",s8));
    //add to action list
    actions.add(getProtocolInfo);



    //create action "GetCurrentConnectionIDs"
    Action getCurrentConnectionIDs = new Action("GetCurrentConnectionIDs");
    //add arguments to "GetCurrentConnectionIDs"
    getCurrentConnectionIDs.addArgument(new Argument("ConnectionIDs","out",s10));
    //add to action list
    actions.add(getCurrentConnectionIDs);



     //set actions to parent (service)
    super.setActions(actions);


    //set service state table
    List<StateVariable> serviceStateTable = new ArrayList<StateVariable>();

    serviceStateTable.add(s1);
    serviceStateTable.add(s2);
    serviceStateTable.add(s3);
    serviceStateTable.add(s4);
    serviceStateTable.add(s5);
    serviceStateTable.add(s6);
    serviceStateTable.add(s7);
    serviceStateTable.add(s8);
    serviceStateTable.add(s9);
    serviceStateTable.add(s10);

    super.setServiceStateTable(serviceStateTable);



    }

}
