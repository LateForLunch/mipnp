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
 * HttpServerCli.java
 * Created on Dec 8, 2010, 9:06:31 PM
 */
package cli;

import domain.http.HttpRequest;
import domain.http.IHttpRequestHandler;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServerCli implements IHttpRequestHandler {

    public HttpServerCli() {
    }

    public void handleHttpRequest(HttpRequest request) {
        if (request.isGet() || request.isHead()) {
            System.out.println(
                    "HTTP request: " + request.getMethod() +
                    " " + request.getRequestUri());
        }
    }
}
