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

import com.googlecode.mipnp.upnp.ServiceImpl;
import java.io.File;
import javax.xml.ws.Holder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ConnectionManagerImpl extends ServiceImpl implements ConnectionManager {

    private static final String XML_SERVICE_DESCRIPTION =
            "src/main/resources/mediaserver/ConnectionManager-1.xml";

    public ConnectionManagerImpl() {
        super("upnp-org", "ConnectionManager", "ConnectionManager", 1);
        try {
            parseDescription(new File(XML_SERVICE_DESCRIPTION));
        } catch (Exception ex) {
            // This should not happen
            ex.printStackTrace(); // TODO: remove line if everything seems alright
        }
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
}
