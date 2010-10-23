/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche, Jeroen De Wilde
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
 * DiscoveryHandler.java
 * Created on Oct 23, 2010, 3:41:04 PM
 */
package domain;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>, Jeroen De Wilde
 */
public class DiscoveryHandler {

    private static final String ADDRESS = "239.255.255.250";
    private static final int PORT = 1900;

    private Device rootDevice;
    private InetAddress group;
    private MulticastSocket socket;
    private Thread aliveThread;

    /**
     *
     * @param rootDevice the root Device.
     * @throws UnknownHostException if no IP address for the host could be found.
     * @throws IOException if an I/O exception occurs while creating the MulticastSocket.
     */
    public DiscoveryHandler(Device rootDevice)
            throws UnknownHostException, IOException {

        setRootDevice(rootDevice);
        this.group = InetAddress.getByName(ADDRESS);
        this.socket = new MulticastSocket(PORT);
    }

    /**
     *
     * @throws IOException if there is an error joining the multicast group.
     */
    public void start() throws IOException {
        socket.joinGroup(group);
        DiscoveryAliveThread dat = new DiscoveryAliveThread(rootDevice, socket);
        this.aliveThread = new Thread(dat);
        aliveThread.start();
    }

    /**
     *
     * @throws IOException if there is an error leaving the multicast group.
     */
    public void stop() throws IOException {
        if (aliveThread.isAlive()) {
            aliveThread.interrupt();
            try {
                aliveThread.join();
            } catch (InterruptedException ex) {
                // This should not happen.
            }
        }
        socket.leaveGroup(group);
    }

    public Device getRootDevice() {
        return rootDevice;
    }

    public void setRootDevice(Device rootDevice) {
        this.rootDevice = rootDevice;
    }
}
