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
 * MediaReceiverRegistrar.java
 * Created on Jun 30, 2011, 4:00:45 PM
 */
package com.googlecode.mipnp.upnp.mediaserver;

import com.googlecode.mipnp.upnp.AbstractService;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class MediaReceiverRegistrar extends AbstractService {

    public MediaReceiverRegistrar() {
        setUniformResourceName("urn:microsoft.com:service:X_MS_MediaReceiverRegistrar:1");
        setIdentifier("urn:microsoft.com:serviceId:X_MS_MediaReceiverRegistrar");
        try {
            setDescriptionUri(new URI("/mediareceiverregistrar.xml"));
            setControlUri(new URI("/mediareceiverregistrar/control"));
            setEventUri(new URI("/mediareceiverregistrar/event"));
        } catch (URISyntaxException ex) {
            // This should not happen
        }
    }
}
