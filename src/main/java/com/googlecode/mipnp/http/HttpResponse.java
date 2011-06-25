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
 * HttpResponse.java
 * Created on Dec 4, 2010, 1:55:16 PM
 */
package com.googlecode.mipnp.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpResponse extends HttpPacket {

    private int statusCode;
    private String reasonPhrase;

    public HttpResponse() {
        super();
    }

    public HttpResponse(InputStream inputStream)
            throws MalformedHttpPacketException, IOException {

        super(inputStream);
    }

    public HttpResponse(OutputStream outputStream) {
        super(outputStream);
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     * @param statusCode the status code, see {@link HttpConstants#HTTP_STATUS}
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        setReasonPhrase(HTTP_STATUS.get(statusCode));
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String getStartLine() {
        return getVersion() + " " + getStatusCode() + " " + getReasonPhrase();
    }

    @Override
    protected void parseFirstLine(String firstLine)
            throws MalformedHttpPacketException {

        String[] split = firstLine.split(" ");
        if (split.length < 3) {
            throw new MalformedHttpPacketException();
        }
        setVersion(split[0]);
        try {
            setStatusCode(Integer.parseInt(split[1]));
        } catch (NumberFormatException ex) {
            throw new MalformedHttpPacketException(ex.getMessage());
        }
        String reason = split[2];
        for (int i = 3; i < split.length; i++) {
            reason = reason + " " + split[i];
        }
        setReasonPhrase(reason);
    }
}
