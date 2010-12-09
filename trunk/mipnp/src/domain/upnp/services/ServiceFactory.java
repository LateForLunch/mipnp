/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
public class ServiceFactory {

    /**
     * initializes a service to the correct object (contentdirectory, connectionmanager)
     * @param service the service to be initialized
     * @param serviceType the type of the service
     * @return initialized service
     */
    public static Service initializeService(Service service, String serviceType) {
        if (serviceType.equalsIgnoreCase("contentdirectory")) {
            return initializeContentDirectory(service);
        } else if (serviceType.equalsIgnoreCase("connectionmanager")) {
            return initializeConnectionManager(service);
        } else {
            throw new IllegalArgumentException("Service type: " + serviceType + " not supported!");
        }
    }

    /**
     * 
     * @param service the service to be initialized
     * @return initialized service
     */
    private static Service initializeContentDirectory(Service service) {
        //create actionlist
        List<Action> actions = new ArrayList<Action>();

        //create statevariables
        List<String> allowedValues = new ArrayList<String>();
        allowedValues.add("BrowseMetadata");
        allowedValues.add("BrowseDirectChildren");
        StateVariable s1 = new StateVariable("A_ARG_TYPE_BrowseFlag", "string", false);
        s1.setAllowedValueList(allowedValues);

        StateVariable s2 = new StateVariable("SystemUpdateID", "ui4", true);
        StateVariable s2bis = new StateVariable("A_ARG_TYPE_Count", "ui4", false);
        StateVariable s3 = new StateVariable("A_ARG_TYPE_SortCriteria", "string", false);
        StateVariable s4 = new StateVariable("SortCapabilities", "string", false);
        StateVariable s5 = new StateVariable("A_ARG_TYPE_Index", "ui4", false);
        StateVariable s6 = new StateVariable("A_ARG_TYPE_ObjectID", "string", false);
        StateVariable s7 = new StateVariable("A_ARG_TYPE_UpdateID", "ui4", false);
        StateVariable s8 = new StateVariable("A_ARG_TYPE_TagValueList", "string", false);
        StateVariable s9 = new StateVariable("A_ARG_TYPE_Result", "string", false);
        StateVariable s10 = new StateVariable("SearchCapabilities", "string", false);
        StateVariable s11 = new StateVariable("A_ARG_TYPE_Filter", "string", false);

        //create action "Browse"
        Action browse = new Action("Browse");
        //add arguments to action "browse"
        browse.addArgument(new Argument("ObjectID", "in", s6));
        browse.addArgument(new Argument("BrowseFlag", "in", s1));
        browse.addArgument(new Argument("Filter", "in", s11));
        browse.addArgument(new Argument("StartingIndex", "in", s5));
        browse.addArgument(new Argument("RequestedCount", "in", s2bis));
        browse.addArgument(new Argument("SortCriteria", "in", s3));
        browse.addArgument(new Argument("Result", "out", s9));
        browse.addArgument(new Argument("NumberReturned", "out", s2bis));
        browse.addArgument(new Argument("TotalMatches", "out", s2bis));
        browse.addArgument(new Argument("UpdateID", "out", s7));
        //add to action list
        actions.add(browse);


        //create action "DestroyObject"
        Action destroyObject = new Action("DestroyObject");
        //add arguments to action "destroy object"
        destroyObject.addArgument(new Argument("ObjectID", "in", s6));
        //add to action list
        actions.add(destroyObject);


        //create action "GetSystemUpdateID"
        Action getSystemUpdateID = new Action("GetSystemUpdateID");
        //add arguments to action "GetSystemUpdateID"
        getSystemUpdateID.addArgument(new Argument("Id", "out", s2));
        //add action to list
        actions.add(getSystemUpdateID);


        //create action "GetSearchCapabilities"
        Action getSearchCapabilities = new Action("GetSearchCapabilities");
        //add arguments to action "GetSearchCapabilities"
        getSearchCapabilities.addArgument(new Argument("SearchCaps", "out", s10));
        //add action to list
        actions.add(getSearchCapabilities);


        //create action "GetSortCapabilities"
        Action getSortCapabilities = new Action("GetSortCapabilities");
        //add arguments to action "GetSortCapabilities"
        getSortCapabilities.addArgument(new Argument("SortCaps", "out", s4));
        //add action to list
        actions.add(getSortCapabilities);


        //create action "UpdateObject"
        Action updateObject = new Action("UpdateObject");
        //add arguments to action "UpdateObject"
        updateObject.addArgument(new Argument("ObjectID", "in", s6));
        updateObject.addArgument(new Argument("CurrentTagValue", "in", s8));
        updateObject.addArgument(new Argument("NewTagValue", "in", s8));
        //add action to list
        actions.add(updateObject);


        //set actions to parent (service)
        service.setActions(actions);


        //set service state table
        List<StateVariable> serviceStateTable = new ArrayList<StateVariable>();

        serviceStateTable.add(s1);
        serviceStateTable.add(s2);
        serviceStateTable.add(s2bis);
        serviceStateTable.add(s3);
        serviceStateTable.add(s4);
        serviceStateTable.add(s5);
        serviceStateTable.add(s6);
        serviceStateTable.add(s7);
        serviceStateTable.add(s8);
        serviceStateTable.add(s9);
        serviceStateTable.add(s10);
        serviceStateTable.add(s11);

        service.setServiceStateTable(serviceStateTable);

        return service;
    }

    /**
     * initialize a connectionmanager
     * @param service the service to be initialized
     * @return initialized service
     */
    private static Service initializeConnectionManager(Service service) {

        //create action list
        List<Action> actions = new ArrayList<Action>();

        //create statevariables
//      <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_ProtocolInfo</name>
//            <dataType>string</dataType>
//        </stateVariable>
        StateVariable s1 = new StateVariable("A_ARG_TYPE_ProtocolInfo", "string", false);
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
        List<String> allowedValues_s2 = new ArrayList<String>();
        allowedValues_s2.add("OK");
        allowedValues_s2.add("ContentFormatMismatch");
        allowedValues_s2.add("InsufficientBandwidth");
        allowedValues_s2.add("UnreliableChannel");
        allowedValues_s2.add("Unknown");
        StateVariable s2 = new StateVariable("A_ARG_TYPE_ConnectionStatus", "string", false);
        s2.setAllowedValueList(allowedValues_s2);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_AVTransportID</name>
//            <dataType>i4</dataType>
//        </stateVariable>
        StateVariable s3 = new StateVariable("A_ARG_TYPE_AVTransportID", "i4", false);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_RcsID</name>
//            <dataType>i4</dataType>
//        </stateVariable>
        StateVariable s4 = new StateVariable("A_ARG_TYPE_RcsID", "i4", false);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_ConnectionID</name>
//            <dataType>i4</dataType>
//        </stateVariable>
        StateVariable s5 = new StateVariable("A_ARG_TYPE_ConnectionID", "i4", false);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_ConnectionManager</name>
//            <dataType>string</dataType>
//        </stateVariable>
        StateVariable s6 = new StateVariable("A_ARG_TYPE_ConnectionManager", "string", false);
//        <stateVariable sendEvents="yes">
//            <name>SourceProtocolInfo</name>
//            <dataType>string</dataType>
//        </stateVariable>
        StateVariable s7 = new StateVariable("SourceProtocolInfo", "string", true);
//        <stateVariable sendEvents="yes">
//            <name>SinkProtocolInfo</name>
//            <dataType>string</dataType>
//        </stateVariable>
        StateVariable s8 = new StateVariable("SinkProtocolInfo", "string", true);
//        <stateVariable sendEvents="no">
//            <name>A_ARG_TYPE_Direction</name>
//            <dataType>string</dataType>
//            <allowedValueList>
//                <allowedValue>Input</allowedValue>
//                <allowedValue>Output</allowedValue>
//            </allowedValueList>
//        </stateVariable>
//          </stateVariable>
        List<String> allowedValues_s9 = new ArrayList<String>();
        allowedValues_s9.add("Input");
        allowedValues_s9.add("Output");
        StateVariable s9 = new StateVariable("A_ARG_TYPE_Direction", "string", false);
        s9.setAllowedValueList(allowedValues_s9);
//        <stateVariable sendEvents="yes">
//            <name>CurrentConnectionIDs</name>
//            <dataType>string</dataType>
//        </stateVariable>
        StateVariable s10 = new StateVariable("CurrentConnectionIDs", "string", true);


        //create action "GetCurrentConnectionInfo"
        Action getCurrentConnectionInfo = new Action("GetCurrentConnectionInfo");
        //add arguments to "GetCurrentConnectionInfo"
        getCurrentConnectionInfo.addArgument(new Argument("ConnectionID", "in", s5));
        getCurrentConnectionInfo.addArgument(new Argument("RcsID", "out", s4));
        getCurrentConnectionInfo.addArgument(new Argument("AVTransportID", "out", s3));
        getCurrentConnectionInfo.addArgument(new Argument("ProtocolInfo", "out", s1));
        getCurrentConnectionInfo.addArgument(new Argument("PeerConnectionManager", "out", s6));
        getCurrentConnectionInfo.addArgument(new Argument("PeerConnectionID", "out", s5));
        getCurrentConnectionInfo.addArgument(new Argument("Direction", "out", s9));
        getCurrentConnectionInfo.addArgument(new Argument("Status", "out", s2));
        //add to action list
        actions.add(getCurrentConnectionInfo);



        //create action "GetProtocolInfo"
        Action getProtocolInfo = new Action("GetProtocolInfo");
        //add arguments to "GetProtocolInfo"
        getProtocolInfo.addArgument(new Argument("Source", "out", s7));
        getProtocolInfo.addArgument(new Argument("Sink", "out", s8));
        //add to action list
        actions.add(getProtocolInfo);



        //create action "GetCurrentConnectionIDs"
        Action getCurrentConnectionIDs = new Action("GetCurrentConnectionIDs");
        //add arguments to "GetCurrentConnectionIDs"
        getCurrentConnectionIDs.addArgument(new Argument("ConnectionIDs", "out", s10));
        //add to action list
        actions.add(getCurrentConnectionIDs);



        //set actions to parent (service)
        service.setActions(actions);


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

        service.setServiceStateTable(serviceStateTable);
        return service;
    }
}
