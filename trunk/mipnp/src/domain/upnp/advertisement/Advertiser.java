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
 * Advertiser.java
 * Created on Dec 5, 2010, 11:29:37 AM
 */
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import domain.ssdp.SsdpRequest;
import domain.upnp.Device;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Advertiser implements SsdpConstants {

    private Device rootDevice;
    private String groupAddress;
    private InetAddress group;
    private int port;
    private MulticastSocket multicastSocket;
    private int ttl;
    private AdvertiserMainThread advertiserMain;
    private Thread advertiserThread;

    public Advertiser(Device rootDevice) {
        this(rootDevice, SSDP_DEFAULT_ADDRESS, SSDP_DEFAULT_PORT, SSDP_DEFAULT_TTL);
    }

    public Advertiser(Device rootDevice, String groupAddress, int port, int ttl) {
        this.rootDevice = rootDevice;
        setGroupAddress(groupAddress);
        setPort(port);
    }

    public void start() throws IOException {
        this.group = InetAddress.getByName(groupAddress);
        this.multicastSocket = new MulticastSocket(port);
        multicastSocket.setTimeToLive(ttl);
        multicastSocket.joinGroup(group);
        this.advertiserMain = new AdvertiserMainThread(this);
        this.advertiserThread = new Thread(advertiserMain);
        advertiserThread.setName("AdvertiserMain");
        advertiserThread.start();
    }

    public void stop() {
        try {
            multicastSocket.leaveGroup(group);
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
        multicastSocket.close();
        this.multicastSocket = null;
        this.group = null;
        this.advertiserThread = null;
        this.advertiserMain = null;
    }

    public synchronized void requestAlive() {
        SsdpRequest[] list =
                AdvertisePacketFactory.createMulticastAdvertiseSet(
                rootDevice, SSDP_DEFAULT_MAX_AGE);
        for (int i = 0; i < list.length; i++) {
            // TODO: send list[i]
        }
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public int getPort() {
        if (multicastSocket != null) {
            return multicastSocket.getLocalPort();
        }
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeToLive() {
        if (multicastSocket != null) {
            try {
                return multicastSocket.getTimeToLive();
            } catch (IOException ex) {
                return ttl;
            }
        }
        return ttl;
    }

    public void setTimeToLive(int ttl) throws IOException {
        if (multicastSocket != null) {
            multicastSocket.setTimeToLive(ttl);
        }
        this.ttl = ttl;
    }
}
