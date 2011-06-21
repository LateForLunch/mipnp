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
 * MulticastAdvertiser.java
 * Created on Jun 21, 2011, 2:11:05 PM
 */
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import domain.upnp.IDevice;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class MulticastAdvertiser implements SsdpConstants, Runnable {

    private IDevice rootDevice;
    private InetAddress multicastAddr;
    private int port;
    private MulticastSocket socket;

    public MulticastAdvertiser(IDevice rootDevice)
            throws UnknownHostException, IOException {

        this(rootDevice,
                InetAddress.getByName(SSDP_DEFAULT_MULTICAST_ADDRESS),
                SSDP_DEFAULT_PORT);
    }

    public MulticastAdvertiser(IDevice rootDevice,
            InetAddress multicastAddr, int port) throws IOException {

        this.rootDevice = rootDevice;
        this.multicastAddr = multicastAddr;
        this.port = port;
        this.socket = new MulticastSocket(port);
        socket.joinGroup(multicastAddr);
    }

    public void run() {
        while (true) {
            byte[] buf = new byte[SSDP_DEFAULT_BUF_SIZE];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(recv);
                System.out.println(new String(recv.getData())); // TODO
            } catch (SocketException ex) { // Socket closed
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        socket.leaveGroup(multicastAddr);
        socket.close();
    }
}
