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
 * HttpRequest.java
 * Created on Dec 2, 2010, 9:21:20 PM
 */
package com.googlecode.mipnp.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpRequest extends HttpPacket {

    private String method;
    private URI requestUri;
    private boolean handled;

    public HttpRequest() {
        super();
    }

    public HttpRequest(InputStream inputStream)
            throws MalformedHttpPacketException, IOException {

        super(inputStream);
    }

    public HttpRequest(OutputStream outputStream) {
        super(outputStream);
    }

    public boolean isMethod(String method) {
        if (method != null && method.equalsIgnoreCase(getMethod())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the method of this request is GET.
     * @return true if the method is GET, false otherwise
     */
    public boolean isGet() {
        return isMethod(METHOD_GET);
    }

    /**
     * Check if the method of this request is HEAD.
     * @return true if the method is HEAD, false otherwise
     */
    public boolean isHead() {
        return isMethod(METHOD_HEAD);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public URI getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(URI requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * Check if this request is handled or not.
     * @return true of this request is handled, false otherwise.
     */
    public boolean isHandled() {
        return handled;
    }

    /**
     * Sets the handled state.
     * @param handled
     */
    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    @Override
    public String getStartLine() {
        return getMethod() + " " + getRequestUri().toString() + " " + getVersion();
    }

    protected void parseFirstLine(String firstLine)
            throws MalformedHttpPacketException {

        String[] split = firstLine.split(" ");
        if (split.length != 3) {
            throw new MalformedHttpPacketException();
        }
        setMethod(split[0]);
        setVersion(split[2]);
        URI req = null;
        try {
            req = URI.create(split[1]);
//            String hostHeader = getHeader(HEADER_HOST);
//            if (!req.equals(URI.create("*")) && hostHeader != null) {
//                if (!hostHeader.startsWith("http://")) {
//                    hostHeader = "http://".concat(hostHeader);
//                }
//                req = URI.create(hostHeader).resolve(req);
//            }
        } catch (IllegalArgumentException ex) {
            throw new MalformedHttpPacketException();
        }
        setRequestUri(req);
    }
}
