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
 * HttpInputStream.java
 * Created on Dec 4, 2010, 12:01:52 PM
 */
package com.googlecode.mipnp.http;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class can be used to read Strings as well as byte arrays from an {@link InputStream}.<br />
 * The {@link HttpConstants#HTTP_DEFAULT_CHARSET} will be used to convert the bytes into a String.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpInputStream extends FilterInputStream implements HttpConstants {

    public HttpInputStream(InputStream in) {
        super(in);
    }

    /**
     * Read a line from the InputStream.
     * @return a line
     * @throws IOException if an I/O error occurs while reading
     */
    public String readLine() throws IOException {
        byte[] buf = new byte[1];
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        boolean crFound = false;

        int read = read(buf);
        while (read > 0) {
            if (crFound) {
                if (buf[0] == LFb) {
                    // CRLF found
                    break;
                } else {
                    // CR followed by some other char
                    line.write(CRb);
                    line.write(buf);
                    crFound = false;
                }
            }
            if (buf[0] == CRb) {
                crFound = true;
            } else {
                line.write(buf);
            }
            read = read(buf);
        }
        return new String(line.toByteArray(), HTTP_DEFAULT_CHARSET);
    }
}
