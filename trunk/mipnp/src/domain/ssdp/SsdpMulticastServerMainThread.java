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
 * SsdpMulticastServerMainThread.java
 * Created on Dec 4, 2010, 4:51:10 PM
 */
package domain.ssdp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 * see {@link SsdpMulticastServer}
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SsdpMulticastServerMainThread implements Runnable, SsdpConstants {

    private SsdpMulticastServer server;
    private MulticastSocket multicastSocket;

    public SsdpMulticastServerMainThread(
            SsdpMulticastServer server, MulticastSocket multicastSocket) {

        this.server = server;
        this.multicastSocket = multicastSocket;
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                byte[] buf = new byte[SSDP_DEFAULT_BUF_SIZE];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                multicastSocket.receive(dp);
                SsdpRequest request = new SsdpRequest(dp);
                request.parse();
                server.notifyHandlers(request);
                Thread.yield();
            } catch (SocketException ex) {
                // MulticastSocket closed
                return;
            } catch (IOException ex) {
                // I/O error during receive(), try again
            }
        }
    }

    public void stop() {
        // TODO
    }
}
