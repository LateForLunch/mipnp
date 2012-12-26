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
 * MSContentDirectoryWrapper.java
 * Created on Jul 24, 2011, 11:12:23 AM
 */
package com.googlecode.mipnp.mediaserver;

import com.googlecode.mipnp.upnp.ServiceImpl;
import java.util.Arrays;
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
        portName="MSContentDirectory",
//        targetNamespace=MSContentDirectoryWrapper.TYPE_AS_URN)
        targetNamespace="urn:schemas-upnp-org:service:ContentDirectory:1")
public class MSContentDirectoryWrapper extends ServiceImpl {

//    public static final String TYPE_AS_URN =
//            "urn:schemas-microsoft-com:service:MSContentDirectory:1";
    public static final String TYPE_AS_URN =
            "urn:schemas-upnp-org:service:ContentDirectory:1";

    public static final String ID_AS_URN =
            "urn:upnp-org:serviceId:MSContentDirectory";

    private static final String XML_DESCRIPTION =
            "/mediaserver/MSContentDirectory-1.xml";

    private static final List<String> browseInsteadOfSearch =
            Arrays.asList(
            // Music
            "4", "5", "6", "7", "F", "14",
            "100", "101", "102", "103", "104", "105", "106", "107", "108",
            // Video
            "8", "9", "A", "E", "10", "15",
            "200", "201", "202", "203", "204", "205",
            // Pictures
            "B", "C", "D", "D2", "11", "16",
            "300", "301", "302", "303", "304", "305", "306",
            // Playlists
            "13", "17");

    private ContentDirectory cd;

    public MSContentDirectoryWrapper(ContentDirectory cd) {
        super("microsoft.com", "MSContentDirectory", "MSContentDirectory", 1);
//        super(TYPE_AS_URN, ID_AS_URN);
        try {
//            parseDescription(new File(XML_SERVICE_DESCRIPTION));
            parseDescription(
                    getClass().getResourceAsStream(XML_DESCRIPTION));
        } catch (Exception ex) {
            // This should not happen
            ex.printStackTrace(); // TODO: remove line if everything seems alright
        }
        this.cd = cd;
    }

    @WebMethod(
            operationName="Browse",
            action=TYPE_AS_URN + "#Browse")
    public void browse(
            @WebParam(name="ContainerID")
            String containerId,
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

        cd.browse(
                containerId, browseFlag, filter, startingIndex, requestedCount,
                sortCriteria, result, numberReturned, totalMatches, updateId);
    }

    @WebMethod(
            operationName="Search",
            action=TYPE_AS_URN + "#Search")
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

        if (browseInsteadOfSearch.contains(containerId)) {
            cd.browse(containerId, "BrowseDirectChildren", filter, startingIndex,
                    requestedCount, sortCriteria, result, numberReturned,
                    totalMatches, updateId);
        } else {
            cd.search(containerId, searchCriteria, filter, startingIndex,
                    requestedCount, sortCriteria, result, numberReturned,
                    totalMatches, updateId);
        }
    }

    @WebMethod(
            operationName="GetSystemUpdateID",
            action=TYPE_AS_URN + "#GetSystemUpdateID")
    public void getSystemUpdateId(
            @WebParam(name="Id", mode=WebParam.Mode.OUT)
            Holder<Integer> id) {

        cd.getSystemUpdateId(id);
    }

    @WebMethod(
            operationName="GetSearchCapabilities",
            action=TYPE_AS_URN + "#GetSearchCapabilities")
    public void getSearchCapabilities(
            @WebParam(name="SearchCaps", mode=WebParam.Mode.OUT)
            Holder<String> searchCaps) {

        cd.getSearchCapabilities(searchCaps);
    }

    @WebMethod(
            operationName="GetSortCapabilities",
            action=TYPE_AS_URN + "#GetSortCapabilities")
    public void getSortCapabilities(
            @WebParam(name="SortCaps", mode=WebParam.Mode.OUT)
            Holder<String> sortCaps) {

        cd.getSortCapabilities(sortCaps);
    }

    @Override
    public String getTypeAsUrn() {
        // urn:schemas-microsoft-com:service:MSContentDirectory:1
        return "urn:schemas-microsoft-com:service:" +
                getType() + ":" + getVersion();
    }

    @Override
    public String getIdAsUrn() {
        // urn:upnp-org:serviceId:MSContentDirectory
        return "urn:upnp-org:serviceId:" + getId();
    }
}
