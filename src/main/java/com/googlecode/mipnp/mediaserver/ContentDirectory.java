/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
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

import com.googlecode.mipnp.mediaserver.cds.CdsObject;
import com.googlecode.mipnp.mediaserver.cds.CdsObjectComparator;
import com.googlecode.mipnp.mediaserver.cds.DidlLiteDocument;
import com.googlecode.mipnp.mediaserver.library.MediaLibrary;
import com.googlecode.mipnp.mediaserver.cds.SearchCriteria;
import com.googlecode.mipnp.upnp.ServiceImpl;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
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

    private MediaLibrary library;
    private URL mediaLocation;

    public ContentDirectory(MediaLibrary library) {
        super("ContentDirectory", "ContentDirectory", 1);
        try {
            parseDescription(new File(XML_SERVICE_DESCRIPTION));
        } catch (Exception ex) {
            // This should not happen
            ex.printStackTrace(); // TODO: remove line if everything seems alright
        }
        this.library = library;
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

        DidlLiteDocument doc = new DidlLiteDocument(mediaLocation, filter);

        CdsObject obj = library.getObjectById(objectId);
        if (obj == null) {
            // TODO: Someone is asking an object we don't have
            // This should throw a SOAP fault
            result.value = doc.toString();
            numberReturned.value = 0;
            totalMatches.value = 0;
            updateId.value = 0;
            return;
        }

//        if (!obj.isContainer()) {
//            return; // TODO: SOAP fault
//        }

        if (browseFlag.equals("BrowseMetadata")) {
            doc.addCdsObject(obj);
            numberReturned.value = 1;
            totalMatches.value = 1;
        } else if (browseFlag.equals("BrowseDirectChildren") && obj.isContainer()) {
            List<CdsObject> children = obj.getChildren();
            Collections.sort(children, new CdsObjectComparator(sortCriteria));
            CdsObject child = null;
            int responseCount = children.size() - startingIndex;
            if (requestedCount > 0 && responseCount > requestedCount) {
                responseCount = requestedCount;
            }
            for (int i = startingIndex; i < startingIndex + responseCount; i++) {
                child = children.get(i);
                doc.addCdsObject(child);
            }
            numberReturned.value = responseCount;
            totalMatches.value = children.size();
        } else {
            // TODO: SOAP fault
            return;
        }

        result.value = doc.toString();
        updateId.value = 0;
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

        DidlLiteDocument doc = new DidlLiteDocument(mediaLocation, filter);

        CdsObject start = library.getObjectById(containerId);
        if (start == null || start.isItem()) {
            // TODO: SOAP fault
            result.value = doc.toString();
            numberReturned.value = 0;
            totalMatches.value = 0;
            updateId.value = 0;
            return;
        }

        SearchCriteria sc = new SearchCriteria(searchCriteria);
        List<CdsObject> searchResult = library.search(start, sc);
        Collections.sort(searchResult, new CdsObjectComparator(sortCriteria));
//        result.value = "<DIDL-Lite ";
//        result.value += "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" ";
//        result.value += "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" ";
//        result.value += "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\">";

        CdsObject obj = null;
        int responseCount = searchResult.size() - startingIndex;
        if (requestedCount > 0 && responseCount > requestedCount) {
            responseCount = requestedCount;
        }
        for (int i = startingIndex; i < startingIndex + responseCount; i++) {
            obj = searchResult.get(i);
            doc.addCdsObject(obj);
        }
//        for (int i = startingIndex; i < startingIndex + responseCount; i++) {
//            obj = searchResult.get(i);
//            result.value += "<item id=\"" + obj.getId();
//            CdsObject parent = obj.getParent();
//            String parentId = "-1";
//            if (parent != null) {
//                parentId = parent.getId();
//            }
//            result.value += "\" parentID=\"" + parentId + "\" restricted=\"true\">";
//            result.value += "<upnp:class>" + obj.getUpnpClass() + "</upnp:class>";
//            result.value += "<dc:title>" + obj.getTitle() + "</dc:title>";
//            if (filter.contains("res")) {
//                FileResource res = obj.getResource();
//                if (res != null) {
//                    result.value += "<res protocolInfo=\"http-get:*:" + res.getMimeType() + ":*\">";
//                    result.value += mediaLocation.toString() + "/" + obj.getId();
//                    result.value += "</res>";
//                }
//            }
//            result.value += "</item>";
//        }

//        result.value += "</DIDL-Lite>";
        result.value = doc.toString();
        numberReturned.value = responseCount;
        totalMatches.value = searchResult.size();
        updateId.value = 0;
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

    public URL getMediaLocation() {
        return mediaLocation;
    }

    public void setMediaLocation(URL location) {
        this.mediaLocation = location;
    }
}
