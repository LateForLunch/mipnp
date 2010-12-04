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
import java.io.InputStream;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpRequest extends HttpPacket {

    private String method;
    private String requestUri;

    public HttpRequest() {
    }

    public boolean parse(InputStream in) throws IOException {
        HttpInputStream his = new HttpInputStream(new BufferedInputStream(in));
        String firstLine = his.readLine();
        String[] split = firstLine.split(" ");
        if (split.length != 3) {
            return false;
        }
        setMethod(split[0]);
        setRequestUri(split[1]);
        setVersion(split[2]);

        return super.parse(his);
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

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }
}
