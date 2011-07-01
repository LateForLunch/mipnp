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
 * ContentDirectory.java
 * Created on Jun 30, 2011, 3:53:50 PM
 */
package com.googlecode.mipnp.upnp.mediaserver;

import com.googlecode.mipnp.upnp.AbstractService;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ContentDirectory extends AbstractService {

    public ContentDirectory() {
        setUniformResourceName("urn:schemas-upnp-org:service:ContentDirectory:1");
        setIdentifier("urn:upnp-org:serviceId:ContentDirectory");
        try {
            setDescriptionUri(new URI("/contentdirectory.xml"));
            setControlUri(new URI("/contentdirectory/control"));
            setEventUri(new URI("/contentdirectory/event"));
        } catch (URISyntaxException ex) {
            // This should not happen
        }
    }
}
