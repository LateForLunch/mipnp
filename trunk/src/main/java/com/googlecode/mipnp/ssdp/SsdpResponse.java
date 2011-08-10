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
 * SsdpResponse.java
 * Created on Dec 4, 2010, 4:43:05 PM
 */
package com.googlecode.mipnp.ssdp;

import com.googlecode.mipnp.http.HttpResponse;
import com.googlecode.mipnp.http.MalformedHttpPacketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpResponse extends HttpResponse {

    public SsdpResponse() {
        super();
    }

    public SsdpResponse(InputStream inputStream)
            throws MalformedHttpPacketException, IOException {

        super(inputStream);
    }

    public SsdpResponse(OutputStream outputStream) {
        super(outputStream);
    }
}
