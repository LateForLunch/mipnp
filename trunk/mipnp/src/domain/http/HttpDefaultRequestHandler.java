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
 * HttpDefaultRequestHandler.java
 * Created on Dec 5, 2010, 2:37:41 PM
 */
package domain.http;

import java.io.IOException;

/**
 * The default HttpRequestHandler.<br />
 * This class will send 501 Not Implemented.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class HttpDefaultRequestHandler implements IHttpRequestHandler {

    public HttpDefaultRequestHandler() {
    }

    /**
     * Send 501 Not Implemented.
     * @param request the request to be handled
     */
    public void handleHttpRequest(HttpRequest request) {
        HttpResponse response = new HttpResponse(request);
        response.setStatusCode(501); // 501 Not Implemented
        try {
            response.writeToRequest();
            request.setHandled(true);
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
    }
}
