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
 * SearchResponder.java
 * Created on Jul 24, 2011, 1:35:24 PM
 */
package com.googlecode.mipnp.upnp.discovery;

import com.googlecode.mipnp.http.MalformedHttpPacketException;
import com.googlecode.mipnp.ssdp.SsdpRequest;
import com.googlecode.mipnp.ssdp.SsdpResponse;
import com.googlecode.mipnp.upnp.RootDevice;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SearchResponder implements Runnable {

    private static final int ADVERTISEMENT_DURATION = 1800;

    private RootDevice rootDevice;
    private DatagramSocket socket;
    private DatagramPacket searchPacket;

    public SearchResponder(
            RootDevice rootDevice,
            DatagramSocket socket,
            DatagramPacket searchPacket) {

        this.rootDevice = rootDevice;
        this.socket = socket;
        this.searchPacket = searchPacket;
    }

    public void run() {
        ByteArrayInputStream bais =
                new ByteArrayInputStream(searchPacket.getData(),
                searchPacket.getOffset(), searchPacket.getLength());
        try {
            SsdpRequest request = new SsdpRequest(bais);
            if (request.isMsearch()) {
                handleRequest(searchPacket, request);
            }
        } catch (MalformedHttpPacketException ex) {
            // Ignore packet
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
    }

    private void handleRequest(
            DatagramPacket requestPacket, SsdpRequest request)
            throws MalformedHttpPacketException, IOException {

        List<SsdpResponse> responses =
                SearchPacketFactory.createSearchResponseSet(
                rootDevice, ADVERTISEMENT_DURATION, request);
        if (responses.size() <= 0) {
            return;
        }

        InetAddress addr = requestPacket.getAddress();
        int port = requestPacket.getPort();

        // TODO: wait random time (MX header)

        for (SsdpResponse resp : responses) {
            byte[] data = resp.getBytes();
            DatagramPacket packet = new DatagramPacket(
                    data, data.length,
                    addr, port);
            socket.send(packet);
        }
    }
}
