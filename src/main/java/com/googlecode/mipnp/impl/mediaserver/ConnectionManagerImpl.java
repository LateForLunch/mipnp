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
 * ConnectionManagerImpl.java
 * Created on Jun 30, 2011, 3:36:22 PM
 */
package com.googlecode.mipnp.impl.mediaserver;

import com.googlecode.mipnp.upnp.ActionImpl;
import com.googlecode.mipnp.upnp.Parameter.Mode;
import com.googlecode.mipnp.upnp.ParameterImpl;
import com.googlecode.mipnp.upnp.ServiceImpl;
import com.googlecode.mipnp.upnp.StateVariable;
import com.googlecode.mipnp.upnp.StateVariableImpl;
import java.net.URI;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ConnectionManagerImpl extends ServiceImpl implements ConnectionManager {

    private StateVariableImpl sourceProtocolInfo;
    private StateVariableImpl sinkProtocolInfo;
    private StateVariableImpl currentConnectionIds;
    private StateVariableImpl connectionStatus;
    private StateVariableImpl connectionManager;
    private StateVariableImpl direction;
    private StateVariableImpl protocolInfo;
    private StateVariableImpl connectionId;
    private StateVariableImpl avTransportId;
    private StateVariableImpl rcsId;

    public ConnectionManagerImpl(
            URI descriptionUri, URI controlUri, URI eventUri) {

        super("upnp.org", "ConnectionManager", "ConnectionManager", "1",
                descriptionUri, controlUri, eventUri);

        initStateVars();
        initActions();
    }

    public void getProtocolInfo(
            Holder<String> source,
            Holder<String> sink) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getCurrentConnectionIDs(Holder<String> connectionIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getCurrentConnectionInfo(
            int connectionId,
            Holder<Integer> rcsId,
            Holder<Integer> avTransportId,
            Holder<String> protocolInfo,
            Holder<String> peerConnectionManager,
            Holder<Integer> peerConnectionId,
            Holder<String> direction,
            Holder<String> status) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void initStateVars() {
        this.sourceProtocolInfo = new StateVariableImpl(
                "SourceProtocolInfo", StateVariable.DataType.STRING, true, false);
        this.sinkProtocolInfo = new StateVariableImpl(
                "SinkProtocolInfo", StateVariable.DataType.STRING, true, false);
        this.currentConnectionIds = new StateVariableImpl(
                "CurrentConnectionIDs", StateVariable.DataType.STRING, true, false);
        this.connectionStatus = new StateVariableImpl(
                "A_ARG_TYPE_ConnectionStatus", StateVariable.DataType.STRING, false, false);
        this.connectionManager = new StateVariableImpl(
                "A_ARG_TYPE_ConnectionManager", StateVariable.DataType.STRING, false, false);
        this.direction = new StateVariableImpl(
                "A_ARG_TYPE_Direction", StateVariable.DataType.STRING, false, false);
        this.protocolInfo = new StateVariableImpl(
                "A_ARG_TYPE_ProtocolInfo", StateVariable.DataType.STRING, false, false);
        this.connectionId = new StateVariableImpl(
                "A_ARG_TYPE_ConnectionID", StateVariable.DataType.I4, false, false);
        this.avTransportId = new StateVariableImpl(
                "A_ARG_TYPE_AVTransportID", StateVariable.DataType.I4, false, false);
        this.rcsId = new StateVariableImpl(
                "A_ARG_TYPE_RcsID", StateVariable.DataType.I4, false, false);
    }

    private void initActions() {
        // GetProtocolInfo
        ActionImpl action = new ActionImpl("GetProtocolInfo");
        action.addParameter(
                new ParameterImpl("Source", Mode.OUT, sourceProtocolInfo));
        action.addParameter(
                new ParameterImpl("Sink", Mode.OUT, sinkProtocolInfo));
        addAction(action);

        // GetCurrentConnectionIDs
        action = new ActionImpl("GetCurrentConnectionIDs");
        action.addParameter(
                new ParameterImpl("ConnectionIDs", Mode.OUT, currentConnectionIds));
        addAction(action);

        // GetCurrentConnectionInfo
        action = new ActionImpl("GetCurrentConnectionInfo");
        action.addParameter(
                new ParameterImpl("ConnectionID", Mode.IN, connectionId));
        action.addParameter(
                new ParameterImpl("RcsID", Mode.OUT, rcsId));
        action.addParameter(
                new ParameterImpl("AVTransportID", Mode.OUT, avTransportId));
        action.addParameter(
                new ParameterImpl("ProtocolInfo", Mode.OUT, protocolInfo));
        action.addParameter(
                new ParameterImpl("PeerConnectionManager", Mode.OUT, connectionManager));
        action.addParameter(
                new ParameterImpl("PeerConnectionID", Mode.OUT, connectionId));
        action.addParameter(
                new ParameterImpl("Direction", Mode.OUT, direction));
        action.addParameter(
                new ParameterImpl("Status", Mode.OUT, connectionStatus));
        addAction(action);
    }
}
