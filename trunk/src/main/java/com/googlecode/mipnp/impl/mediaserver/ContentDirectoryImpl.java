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
 * ContentDirectoryImpl.java
 * Created on Jun 30, 2011, 3:53:50 PM
 */
package com.googlecode.mipnp.impl.mediaserver;

import com.googlecode.mipnp.upnp.AbstractService;
import java.net.URI;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ContentDirectoryImpl extends AbstractService {

    public ContentDirectoryImpl(
            URI descriptionUri, URI controlUri, URI eventUri) {
        super("upnp.org", "ContentDirectory", "ContentDirectory", "1",
                descriptionUri, controlUri, eventUri);
    }
}
