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
 * MediaServer.java
 * Created on Jun 26, 2011, 4:39:23 PM
 */
package com.googlecode.mipnp.impl.mediaserver;

import com.googlecode.mipnp.upnp.AbstractRootDevice;
import java.util.UUID;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaServer extends AbstractRootDevice {

    public MediaServer() {
        setMajorUpnpVersion(1);
        setMinorUpnpVersion(1);
        setVendorDomainName("upnp.org");
        setType("MediaServer");
        setVersion(1);
        setFriendlyName("MiPnP: 1");
        setManufacturer(null);
        setManufacturerUrl(null);
        setModelDescription("MiPnP: UPnP Media Server");
        setModelName("Windows Media Connect Compatible (MiPnP)");
        setModelNumber("1.0");
        setModelUrl(null);
        setSerialNumber("1");
        setUuid(UUID.randomUUID());
        setUniversalProductCode(null);
        setPresentationUrl(null);

        addService(new ConnectionManager());
        addService(new ContentDirectory());
        addService(new MediaReceiverRegistrar());
    }
}
