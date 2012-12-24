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
 * ConnectionManagerImpl.java
 * Created on Jun 30, 2011, 3:36:22 PM
 */
package com.googlecode.mipnp.mediaserver;

import com.googlecode.mipnp.upnp.ServiceImpl;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@WebService(
        portName="ConnectionManager",
        targetNamespace=ConnectionManager.TYPE_AS_URN)
public class ConnectionManager extends ServiceImpl {

    public static final String TYPE_AS_URN =
            "urn:schemas-upnp-org:service:ConnectionManager:1";

    public static final String ID_AS_URN =
            "urn:upnp-org:serviceId:ConnectionManager";

    private static final String XML_DESCRIPTION =
            "/mediaserver/ConnectionManager-1.xml";

    public ConnectionManager() {
//        super("ConnectionManager", "ConnectionManager", 1);
        super(TYPE_AS_URN, ID_AS_URN);
        try {
//            parseDescription(new File(XML_SERVICE_DESCRIPTION));
            parseDescription(
                    getClass().getResourceAsStream(XML_DESCRIPTION));
        } catch (Exception ex) {
            // This should not happen
            ex.printStackTrace(); // TODO: remove line if everything seems alright
        }
    }

    @WebMethod(
            operationName="GetProtocolInfo",
            action=TYPE_AS_URN + "#GetProtocolInfo")
    public void getProtocolInfo(
            @WebParam(name="Source", mode=WebParam.Mode.OUT)
            Holder<String> source,
            @WebParam(name="Sink", mode=WebParam.Mode.OUT)
            Holder<String> sink) {

        System.out.println("TODO: implement ConnectionManager.getProtocolInfo"); // TODO
    }

    @WebMethod(
            operationName="GetCurrentConnectionIDs",
            action=TYPE_AS_URN + "#GetCurrentConnectionIDs")
    public void getCurrentConnectionIDs(
            @WebParam(name="ConnectionIDs", mode=WebParam.Mode.OUT)
            Holder<String> connectionIds) {

        System.out.println("TODO: implement ConnectionManager.getCurrentConnectionIDs"); // TODO
    }

    @WebMethod(
            operationName="GetCurrentConnectionInfo",
            action=TYPE_AS_URN + "#GetCurrentConnectionInfo")
    public void getCurrentConnectionInfo(
            @WebParam(name="ConnectionID")
            int connectionId,
            @WebParam(name="RcsID", mode=WebParam.Mode.OUT)
            Holder<Integer> rcsId,
            @WebParam(name="AVTransportID", mode=WebParam.Mode.OUT)
            Holder<Integer> avTransportId,
            @WebParam(name="ProtocolInfo", mode=WebParam.Mode.OUT)
            Holder<String> protocolInfo,
            @WebParam(name="PeerConnectionManager", mode=WebParam.Mode.OUT)
            Holder<String> peerConnectionManager,
            @WebParam(name="PeerConnectionID", mode=WebParam.Mode.OUT)
            Holder<Integer> peerConnectionId,
            @WebParam(name="Direction", mode=WebParam.Mode.OUT)
            Holder<String> direction,
            @WebParam(name="Status", mode=WebParam.Mode.OUT)
            Holder<String> status) {

        System.out.println("TODO: implement ConnectionManager.getCurrentConnectionInfo"); // TODO
    }
}
