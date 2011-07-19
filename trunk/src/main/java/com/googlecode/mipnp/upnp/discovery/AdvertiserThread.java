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
 * AdvertiserThread.java
 * Created on Jun 22, 2011, 5:25:10 PM
 */
package com.googlecode.mipnp.upnp.discovery;

import com.googlecode.mipnp.ssdp.SsdpConstants;
import com.googlecode.mipnp.ssdp.SsdpRequest;
import com.googlecode.mipnp.upnp.RootDevice;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class AdvertiserThread implements Runnable, SsdpConstants {

    private static final int ADVERTISEMENT_DURATION = 1800;
    private static final int ADVERTISEMENT_REPEATS = 2;
    private static final int SET_SLEEP_MILLIS = 200;

    private RootDevice rootDevice;
    private MulticastSocket socket;
    private InetAddress mcastAddr;
    private int mcastPort;
    private Random random;

    public AdvertiserThread(RootDevice rootDevice, MulticastSocket socket) {
        this.rootDevice = rootDevice;
        this.socket = socket;
        try {
            this.mcastAddr = InetAddress.getByName(SSDP_DEFAULT_MULTICAST_ADDRESS);
        } catch (UnknownHostException ex) {
            // This should not happen
        }
        this.mcastPort = SSDP_DEFAULT_PORT;
        this.random = new Random();
    }

    public void run() {
        int sleep;
        while (!Thread.interrupted()) {
            try {
                sendAliveSet(ADVERTISEMENT_DURATION);
                sleep = ADVERTISEMENT_DURATION / 2;
                sleep -= random.nextInt(ADVERTISEMENT_DURATION / 10);
                Thread.sleep(sleep * 1000);
            } catch (SocketException ex) {
                ex.printStackTrace(); // TODO
            } catch (IOException ex) {
                ex.printStackTrace(); // TODO
            } catch (InterruptedException ex) {
                return;
            }
        }
    }

    private void sendAliveSet(int adDuration)
            throws InterruptedException, SocketException, IOException {

        List<SsdpRequest> requests =
                AdvertisementPacketFactory.createAliveSet(rootDevice, adDuration);
        for (int i = 1; i <= ADVERTISEMENT_REPEATS; i++) {
            for (SsdpRequest request : requests) {
                byte[] data = request.getBytes();
                DatagramPacket packet = new DatagramPacket(
                        data, data.length,
                        mcastAddr, mcastPort);
                socket.send(packet);
            }
            Thread.sleep(random.nextInt(SET_SLEEP_MILLIS));
        }
    }
}
