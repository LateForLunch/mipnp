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
 * AdvertiserMainThread.java
 * Created on Dec 5, 2010, 11:31:20 AM
 */
package domain.upnp.advertisement;

import domain.ssdp.SsdpConstants;
import java.util.Random;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class AdvertiserMainThread implements Runnable, SsdpConstants {

    private static final int REPEAT = 3;

    private Advertiser advertiser;
    private Random random;

    public AdvertiserMainThread(Advertiser advertiser) {
        this.advertiser = advertiser;
        this.random = new Random();
    }

    public void run() {
        try {
            Thread.sleep((long) (random.nextDouble() * 100));
            requestAlive(); // Initial alive
            int w8 = SSDP_DEFAULT_MAX_AGE * 250;
            while (!Thread.interrupted()) {
                Thread.sleep((long) ((random.nextDouble() * w8) + w8));
                requestAlive();
            }
        } catch (InterruptedException ex) {
            return;
        }
    }

    private void requestAlive() throws InterruptedException {
        for (int i = 1; i <= REPEAT; i++) {
            advertiser.requestAlive();
            Thread.sleep((long) (random.nextDouble() * 300));
        }
    }
}
