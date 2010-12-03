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
 * HttpPacket.java
 * Created on Oct 31, 2010, 10:15:05 AM
 */
package domain.http;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpPacket implements HttpConstants {

    private String version;
    private String startLine;
    private Map<String /* fieldname */, String /* fieldvalue */> headers;
    private byte[] content;

    public HttpPacket() {
        setVersion(HTTP_VERSION);
        this.headers = new HashMap<String, String>();
        setContent(null);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String setHeader(String str) {
        if (str == null) {
            throw new NullPointerException("Can't parse null");
        }
        int index = str.indexOf(':');
        if (index < 0) {
            throw new IllegalArgumentException("Can't parse String: " + str);
        }
        return setHeader(
                str.substring(0, index).trim(),
                str.substring(index + 1).trim());
    }

    /**
     *
     * @param fieldName
     * @param fieldValue
     * @return the previous field-value, or null if there was no field-value
     */
    public String setHeader(String fieldName, String fieldValue) {
        return headers.put(fieldName, fieldValue);
    }

    /**
     *
     * @param fieldName
     * @return the field-value to which the specified fieldname is mapped,
     * or null if there is no mapping for the fieldname
     */
    public String getHeader(String fieldName) {
        return headers.get(fieldName);
    }

    /**
     * 
     * @param fieldName
     * @return the previous field-value, or null if there was no mapping for the fieldname
     */
    public String removeHeader(String fieldName) {
        return headers.remove(fieldName);
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        setContent(content, true);
    }

    public void setContent(byte[] content, boolean updateContentLength) {
        this.content = content;
        if (updateContentLength) {
            if (content != null) {
                setContentLength(content.length);
            } else {
                setContentLength(0);
            }
        }
    }

    /**
     *
     * @return the content length of the data or -1 if there is no valid value for Content-Length
     */
    public int getContentLength() {
        String val = getHeader(CONTENT_LENGTH);
        int contentLength = -1;
        try {
            contentLength = Integer.parseInt(val);
        } catch (NumberFormatException ex) {
            // Ignore
        }
        return contentLength;
    }

    public void setContentLength(int length) {
        if (length > 0) {
            setHeader(CONTENT_LENGTH, String.valueOf(length));
        } else {

        }
    }
}
