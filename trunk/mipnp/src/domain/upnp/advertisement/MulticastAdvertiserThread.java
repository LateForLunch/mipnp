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
 * MulticastAdvertiserThread.java
 * Created on Jun 22, 2011, 5:25:10 PM
 */
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import domain.upnp.IDevice;
import java.net.DatagramSocket;
import java.util.Random;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class MulticastAdvertiserThread implements Runnable, SsdpConstants {

    private static final int ADVERTISEMENT_DURATION = 1800;
    private static final int ADVERTISEMENT_REPEATS = 2;
    private static final int INITIAL_SLEEP_MILLIS = 100;
    private static final int SET_SLEEP_MILLIS = 300;

    private IDevice rootDevice;
    private DatagramSocket socket;
    private Random random;

    public MulticastAdvertiserThread(IDevice rootDevice, DatagramSocket socket) {
        this.rootDevice = rootDevice;
        this.socket = socket;
        this.random = new Random();
    }

    public void run() {
        try {
            Thread.sleep(random.nextInt(INITIAL_SLEEP_MILLIS));
        } catch (InterruptedException ex) {
            return;
        }
        // TODO: Send ad
        while (!Thread.interrupted()) {
            int sleep = ADVERTISEMENT_DURATION / 2;
            sleep -= random.nextInt(ADVERTISEMENT_DURATION / 10);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                return;
            }
            for (int i = 1; i <= ADVERTISEMENT_REPEATS; i++) {
                try {
                    // TODO: Send ad
                    Thread.sleep(random.nextInt(SET_SLEEP_MILLIS));
                } catch (InterruptedException ex) {
                    return;
                }
            }
        }
    }
}
