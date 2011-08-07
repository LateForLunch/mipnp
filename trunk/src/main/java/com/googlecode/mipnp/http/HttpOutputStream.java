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
 * HttpOutputStream.java
 * Created on Dec 4, 2010, 2:12:18 PM
 */
package com.googlecode.mipnp.http;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class can be used to write lines as well as byte arrays to an OutputStream.<br />
 * The {@link HttpConstants#HTTP_DEFAULT_CHARSET} will be used to convert the Strings into a bytes.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpOutputStream extends FilterOutputStream implements HttpConstants {

    public HttpOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Write a String to the OutputStream.<br />
     * This method will append {@link HttpConstants#CRLF} to the end of the String.
     * @param line the String to send to the OutputStream
     * @throws IOException if an I/O error occurs while writing the data
     */
    public void writeLine(String line) throws IOException {
        line = line + CRLF;
        write(line.getBytes(HTTP_DEFAULT_CHARSET));
    }

    public void writeLine() throws IOException {
        writeLine("");
    }
}
