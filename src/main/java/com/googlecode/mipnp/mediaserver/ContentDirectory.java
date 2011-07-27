/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche
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

/*
 * ContentDirectory.java
 * Created on Jun 30, 2011, 3:53:50 PM
 */
package com.googlecode.mipnp.mediaserver;

import com.googlecode.mipnp.upnp.ServiceImpl;
import java.io.File;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@WebService(
        portName="ContentDirectory",
        targetNamespace="urn:schemas-upnp-org:service:ContentDirectory:1")
public class ContentDirectory extends ServiceImpl {

    private static final String XML_SERVICE_DESCRIPTION =
            "src/main/resources/mediaserver/ContentDirectory-1.xml";

    public ContentDirectory() {
        super("ContentDirectory", "ContentDirectory", 1);
        try {
            parseDescription(new File(XML_SERVICE_DESCRIPTION));
        } catch (Exception ex) {
            // This should not happen
            ex.printStackTrace(); // TODO: remove line if everything seems alright
        }
    }

    @WebMethod(operationName="Browse")
    public void browse(
            @WebParam(name="ObjectID")
            String objectId,
            @WebParam(name="BrowseFlag")
            String browseFlag,
            @WebParam(name="Filter")
            String filter,
            @WebParam(name="StartingIndex")
            int startingIndex,
            @WebParam(name="RequestedCount")
            int requestedCount,
            @WebParam(name="SortCriteria")
            String sortCriteria,
            @WebParam(name="Result", mode=WebParam.Mode.OUT)
            Holder<String> result,
            @WebParam(name="NumberReturned", mode=WebParam.Mode.OUT)
            Holder<Integer> numberReturned,
            @WebParam(name="TotalMatches", mode=WebParam.Mode.OUT)
            Holder<Integer> totalMatches,
            @WebParam(name="UpdateID", mode=WebParam.Mode.OUT)
            Holder<Integer> updateId) {

        System.out.println("TODO: implement ContentDirectory.browse"); // TODO
    }

    @WebMethod(operationName="Search")
    public void search(
            @WebParam(name="ContainerID")
            String containerId,
            @WebParam(name="SearchCriteria")
            String searchCriteria,
            @WebParam(name="Filter")
            String filter,
            @WebParam(name="StartingIndex")
            int startingIndex,
            @WebParam(name="RequestedCount")
            int requestedCount,
            @WebParam(name="SortCriteria")
            String sortCriteria,
            @WebParam(name="Result", mode=WebParam.Mode.OUT)
            Holder<String> result,
            @WebParam(name="NumberReturned", mode=WebParam.Mode.OUT)
            Holder<Integer> numberReturned,
            @WebParam(name="TotalMatches", mode=WebParam.Mode.OUT)
            Holder<Integer> totalMatches,
            @WebParam(name="UpdateID", mode=WebParam.Mode.OUT)
            Holder<Integer> updateId) {

        System.out.println("TODO: implement ContentDirectory.search"); // TODO
    }

    @WebMethod(operationName="DestroyObject")
    public void destroyObject(
            @WebParam(name="ObjectID")
            String objectId) {

        System.out.println("TODO: implement ContentDirectory.destroyObject"); // TODO
    }

    @WebMethod(operationName="GetSystemUpdateID")
    public void getSystemUpdateId(
            @WebParam(name="Id", mode=WebParam.Mode.OUT)
            Holder<Integer> id) {

        System.out.println("TODO: implement ContentDirectory.getSystemUpdateId"); // TODO
    }

    @WebMethod(operationName="GetSearchCapabilities")
    public void getSearchCapabilities(
            @WebParam(name="SearchCaps", mode=WebParam.Mode.OUT)
            Holder<String> searchCaps) {

        System.out.println("TODO: implement ContentDirectory.getSearchCapabilities"); // TODO
    }

    @WebMethod(operationName="GetSortCapabilities")
    public void getSortCapabilities(
            @WebParam(name="SortCaps", mode=WebParam.Mode.OUT)
            Holder<String> sortCaps) {

        System.out.println("TODO: implement ContentDirectory.getSortCapabilities"); // TODO
    }

    @WebMethod(operationName="UpdateObject")
    public void updateObject(
            @WebParam(name="ObjectID")
            String objectId,
            @WebParam(name="CurrentTagValue")
            String currentTagValue,
            @WebParam(name="NewTagValue")
            String newTagValue) {

        System.out.println("TODO: implement ContentDirectory.updateObject"); // TODO
    }
}
