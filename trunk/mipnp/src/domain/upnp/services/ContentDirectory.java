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
public class ContentDirectory /*extends Service*/ {

    public static Service initializeContentDirectory(Service service){
    //create actionlist
    List<Action> actions = new ArrayList<Action>();

    //create statevariables
    List<String>allowedValues = new ArrayList<String>();
    allowedValues.add("BrowseMetadata");
    allowedValues.add("BrowseDirectChildren");
    StateVariable s1 = new StateVariable("A_ARG_TYPE_BrowseFlag","string",false);
    s1.setAllowedValueList(allowedValues);

    StateVariable s2 = new StateVariable("SystemUpdateID","ui4",true);
    StateVariable s2bis = new StateVariable("A_ARG_TYPE_Count","ui4",false);
    StateVariable s3 = new StateVariable("A_ARG_TYPE_SortCriteria","string",false);
    StateVariable s4 =   new StateVariable("SortCapabilities","string",false);
    StateVariable s5 =   new StateVariable("A_ARG_TYPE_Index","ui4",false);
    StateVariable s6 =  new StateVariable("A_ARG_TYPE_ObjectID","string",false);
    StateVariable s7 =  new StateVariable("A_ARG_TYPE_UpdateID","ui4",false);
    StateVariable s8 =  new StateVariable("A_ARG_TYPE_TagValueList","string",false);
    StateVariable s9 =  new StateVariable("A_ARG_TYPE_Result","string",false);
    StateVariable s10 =   new StateVariable("SearchCapabilities","string",false);
    StateVariable s11 =   new StateVariable("A_ARG_TYPE_Filter","string",false);

    //create action "Browse"
    Action browse = new Action("Browse");
    //add arguments to action "browse"
    browse.addArgument(new Argument("ObjectID","in",s6));
    browse.addArgument(new Argument("BrowseFlag","in",s1));
    browse.addArgument(new Argument("Filter","in",s11));
    browse.addArgument(new Argument("StartingIndex","in",s5));
    browse.addArgument(new Argument("RequestedCount","in",s2bis));
    browse.addArgument(new Argument("SortCriteria","in",s3));
    browse.addArgument(new Argument("Result","out",s9));
    browse.addArgument(new Argument("NumberReturned","out",s2bis));
    browse.addArgument(new Argument("TotalMatches","out",s2bis));
    browse.addArgument(new Argument("UpdateID","out",s7));
    //add to action list
    actions.add(browse);


    //create action "DestroyObject"
    Action destroyObject = new Action("DestroyObject");
    //add arguments to action "destroy object"
    destroyObject.addArgument(new Argument("ObjectID","in",s6));
    //add to action list
    actions.add(destroyObject);


    //create action "GetSystemUpdateID"
    Action getSystemUpdateID = new Action("GetSystemUpdateID");
    //add arguments to action "GetSystemUpdateID"
    getSystemUpdateID.addArgument(new Argument("Id","out",s2));
    //add action to list
    actions.add(getSystemUpdateID);


    //create action "GetSearchCapabilities"
    Action getSearchCapabilities = new Action("GetSearchCapabilities");
    //add arguments to action "GetSearchCapabilities"
    getSearchCapabilities.addArgument(new Argument("SearchCaps","out",s10));
    //add action to list
    actions.add(getSearchCapabilities);


     //create action "GetSortCapabilities"
    Action getSortCapabilities = new Action("GetSortCapabilities");
    //add arguments to action "GetSortCapabilities"
    getSortCapabilities.addArgument(new Argument("SortCaps","out",s4));
     //add action to list
    actions.add(getSortCapabilities);


    //create action "UpdateObject"
    Action updateObject = new Action("UpdateObject");
    //add arguments to action "UpdateObject"
    updateObject.addArgument(new Argument("ObjectID","in",s6));
    updateObject.addArgument(new Argument("CurrentTagValue","in",s8));
    updateObject.addArgument(new Argument("NewTagValue","in",s8));
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
}
