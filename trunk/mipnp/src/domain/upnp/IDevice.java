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
 * IDevice.java
 * Created on Jun 21, 2011, 2:00:15 PM
 */
package domain.upnp;

import java.net.URL;

/**
 *
 * @author jochem
 */
public interface IDevice {

    /**
     * Returns the type of the device.
     * @return the type of the device
     */
    String getDeviceType();

    /**
     * Returns the Uniform Resource Name (URN) of the device.
     * <br/>
     * See http://www.ietf.org/rfc/rfc2141.txt for more information about URNs.
     * @return the Uniform Resource Name of the device
     */
    String getURN();

    URL getDescriptionUrl();
    String getUuid();
    Iterable<IDevice> getEmbeddedDevices();
    Iterable<IService> getServices();
    int getBootId();
    int getConfigId();
}
