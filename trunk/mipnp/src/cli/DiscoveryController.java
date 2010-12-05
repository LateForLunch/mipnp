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
 * DiscoveryController.java
 * Created on Oct 30, 2010, 5:12:13 PM
 */
package cli;

import domain.upnp.Device;
import java.io.IOException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class DiscoveryController {

    private DiscoveryCli discoveryCli;

    public DiscoveryController(Device rootDevice) {
        this.discoveryCli = new DiscoveryCli();
    }

    public void startDiscovery() throws IOException {
        discoveryCli.printStartMessage(System.out);
    }

    public void stopDiscovery() throws IOException {
    }
}
