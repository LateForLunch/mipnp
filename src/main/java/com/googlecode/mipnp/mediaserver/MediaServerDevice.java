/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * MediaServerDevice.java
 * Created on Jun 26, 2011, 4:39:23 PM
 */
package com.googlecode.mipnp.mediaserver;

import com.googlecode.mipnp.mediaserver.library.MediaLibrary;
import com.googlecode.mipnp.upnp.AbstractRootDevice;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaServerDevice extends AbstractRootDevice {

    private ContentDirectory cd;

    public MediaServerDevice(UUID uuid, MediaLibrary library) {
        try {
            setMajorUpnpVersion(1);
            setMinorUpnpVersion(1);
            setVendorDomainName("upnp.org");
            setType("MediaServer");
            setVersion(1);
            setFriendlyName("MiPnP :1");
            setManufacturer("MiPnP");
            setManufacturerUrl(new URL("http://code.google.com/p/mipnp/"));
            setModelDescription("MiPnP: UPnP Media Server");
            setModelName("Windows Media Connect Compatible (MiPnP)");
            setModelNumber("1.0");
            setModelUrl(new URL("http://code.google.com/p/mipnp/"));
            setSerialNumber("1");
            setUuid(uuid);
            setUniversalProductCode(null);
            setPresentationUrl(null);

            this.cd = new ContentDirectory(library);
            addService(cd);
            addService(new ConnectionManager());
            addService(new MediaReceiverRegistrar());
            addService(new MSContentDirectoryWrapper(cd));
        } catch (MalformedURLException ex) {
            Logger.getLogger(MediaServerDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMediaServletPath(URL path) {
        cd.setMediaServletPath(path);
    }
}
