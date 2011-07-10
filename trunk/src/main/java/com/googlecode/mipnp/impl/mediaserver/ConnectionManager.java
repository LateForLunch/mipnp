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
 * ConnectionManager.java
 * Created on Jul 1, 2011, 1:36:31 PM
 */
package com.googlecode.mipnp.impl.mediaserver;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@WebService(name="ConnectionManager")
public interface ConnectionManager {

    @WebMethod(operationName="GetCurrentConnectionInfo")
    void getCurrentConnectionInfo(
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
            Holder<String> status);

    @WebMethod(operationName="GetProtocolInfo")
    void getProtocolInfo(
            @WebParam(name="Source", mode=WebParam.Mode.OUT)
            Holder<String> source,
            @WebParam(name="Sink", mode=WebParam.Mode.OUT)
            Holder<String> sink);

    @WebMethod(operationName="GetCurrentConnectionIDs")
    void getCurrentConnectionIDs(
            @WebParam(name="ConnectionIDs", mode=WebParam.Mode.OUT)
            Holder<String> connectionIds);
}
