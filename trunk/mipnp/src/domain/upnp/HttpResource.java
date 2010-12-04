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
 * Resource.java
 * Created on Dec 4, 2010, 10:12:22 PM
 */
package domain.upnp;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpResource {

    private byte[] data;
    private String contentType;
    private String charset;

    public HttpResource(byte[] data, String contentType, String charset) {
        this.data = data;
        this.contentType = contentType;
        this.charset = charset;
    }

    public String getHttpContentTypeHeader() {
        return "Content-Type: " + contentType + "; charset=" + charset;
    }

    public byte[] getAsByteArray() {
        return data;
    }
}
