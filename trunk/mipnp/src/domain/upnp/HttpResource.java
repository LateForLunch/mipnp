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

import java.nio.charset.Charset;

/**
 * A HttpResource object contains the data as a byte array + the content type.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpResource {

    private byte[] data;
    private String contentType;
    private String charset;

    public HttpResource(byte[] data, String contentType) {
        this(data, contentType, null);
    }

    /**
     * Creates a new resource.<br/>
     * <br />
     * An example of a content type is: text/html<br />
     * See the HTTP RFC for all conent types.<br/>
     * <br/>
     * An example of a charset is: UTF-8<br />
     * See {@link Charset} for all charsets.
     * @param data the data
     * @param contentType the content type of the data
     * @param charset the charset of the data
     */
    public HttpResource(byte[] data, String contentType, String charset) {
        this.data = data;
        this.contentType = contentType;
        this.charset = charset;
    }

    public String getHttpContentTypeHeader() {
        String header = "Content-Type: " + contentType;
        if (charset != null) {
            header = header.concat("; charset=" + charset);
        }
        return header;
    }

    public byte[] getAsByteArray() {
        return data;
    }

    public int getLength() {
        return data.length;
    }
}
