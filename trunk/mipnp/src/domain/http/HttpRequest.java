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
 * HttpRequest.java
 * Created on Dec 2, 2010, 9:21:20 PM
 */
package domain.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpRequest extends HttpPacket {

    private String method;
    private URI requestUri;

    public HttpRequest() {
        super();
    }

    public boolean parse() throws IOException {
        HttpInputStream his = new HttpInputStream(
                new BufferedInputStream(getInputStream()));
        String firstLine = his.readLine();
        String[] split = firstLine.split(" ");
        if (split.length != 3) {
            return false;
        }
        boolean parse = super.parse(his);
        setMethod(split[0]);
        setVersion(split[2]);
        URI req = null;
        try {
            req = URI.create(split[1]);
            String hostHeader = getHeader(HOST);
            if (hostHeader != null) {
                hostHeader = "http://".concat(hostHeader);
                req = URI.create(hostHeader).resolve(split[1]);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace(); // TODO: the response MUST be a 400 (Bad Request) error message.
        }
        setRequestUri(req);
        return parse;
    }

    public boolean isMethod(String method) {
        if (method != null && method.equalsIgnoreCase(getMethod())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGet() {
        return isMethod(GET);
    }

    public boolean isHead() {
        return isMethod(HEAD);
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
}
