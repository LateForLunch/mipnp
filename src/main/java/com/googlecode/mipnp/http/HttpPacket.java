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
 * HttpPacket.java
 * Created on Oct 31, 2010, 10:15:05 AM
 */
package com.googlecode.mipnp.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public abstract class HttpPacket implements HttpConstants {

    private String version;
    private Map<String /* fieldname */, String /* fieldvalue */> headers;
    private byte[] content;
    private InputStream inputStream;
    private OutputStream outputStream;

    public HttpPacket() {
        this.version = HTTP_VERSION;
        this.headers = new HashMap<String, String>();
    }

    public HttpPacket(InputStream inputStream)
            throws MalformedHttpPacketException, IOException {

        this();
        this.inputStream = inputStream;
        parse();
    }

    public HttpPacket(OutputStream outputStream) {
        this();
        this.outputStream = outputStream;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isVersion(String version) {
        if (version == null || !(version.equalsIgnoreCase(getVersion()))) {
            return false;
        }
        return true;
    }

    public String setHeader(String str) throws IllegalArgumentException {
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
        fieldName = fieldName.toLowerCase();
        return headers.put(fieldName, fieldValue);
    }

    /**
     *
     * @param fieldName
     * @return the field-value to which the specified field name is mapped,
     * or null if there is no mapping for the field name
     */
    public String getHeader(String fieldName) {
        fieldName = fieldName.toLowerCase();
        return headers.get(fieldName);
    }

    public List<String> getHeaders() {
        List<String> result = new ArrayList<String>(headers.size());
        Iterator<Map.Entry<String, String>> it = headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            result.add(entry.getKey() + ":" + entry.getValue());
        }
        return result;
    }

    /**
     * 
     * @param fieldName
     * @return the previous field-value, or null if there was no mapping for the fieldname
     */
    public String removeHeader(String fieldName) {
        fieldName = fieldName.toLowerCase();
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
        String val = getHeader(HEADER_CONTENT_LENGTH);
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
            setHeader(HEADER_CONTENT_LENGTH, String.valueOf(length));
        } else {
            removeHeader(HEADER_CONTENT_LENGTH);
        }
    }

    public byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Write start line
        byte[] ba = getStartLine().concat(CRLF).getBytes(HTTP_DEFAULT_CHARSET);
        baos.write(ba, 0, ba.length);

        // Write headers
        for (String header : getHeaders()) {
            ba = header.concat(CRLF).getBytes(HTTP_DEFAULT_CHARSET);
            baos.write(ba, 0, ba.length);
        }

        // Write empty line
        ba = CRLF.getBytes(HTTP_DEFAULT_CHARSET);
        baos.write(ba, 0, ba.length);

        // Write content
        ba = getContent();
        if (ba != null) {
            baos.write(ba, 0, ba.length);
        }

        return baos.toByteArray();
    }

    public abstract String getStartLine();

    protected abstract void parseFirstLine(String firstLine)
            throws MalformedHttpPacketException;

    private void parse() throws MalformedHttpPacketException, IOException {
        HttpInputStream his = new HttpInputStream(
                new BufferedInputStream(inputStream));
        String firstLine = his.readLine();
        parseFirstLine(firstLine);
        String line = his.readLine();
        while (!line.trim().isEmpty()) {
            try {
                setHeader(line);
            } catch (IllegalArgumentException ex) {
                throw new MalformedHttpPacketException(ex.getMessage());
            }
            line = his.readLine();
        }
        // The remaining bytes in the InputStream is the content
        byte[] con = new byte[his.available()];
        his.read(con);
        setContent(con, false);
    }

    public void writeTo(OutputStream out) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] data = getBytes();
        bos.write(data, 0, data.length);
        bos.flush();
    }

    public void writeToOutputStream() throws IOException {
        if (outputStream == null) {
            throw new NullPointerException("OutputStream is null");
        }
        writeTo(outputStream);
    }
}
