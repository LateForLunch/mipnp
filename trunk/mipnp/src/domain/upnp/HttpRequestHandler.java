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
 * HttpRequestHandler.java
 * Created on Dec 4, 2010, 9:27:41 PM
 */
package domain.upnp;

import domain.http.HttpConstants;
import domain.http.HttpRequest;
import domain.http.HttpResponse;
import domain.http.IHttpRequestHandler;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class can handle GET and HEAD HTTP methods.<br />
 * Use the {@link HttpResource} class to feed this handler with data.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpRequestHandler implements IHttpRequestHandler, HttpConstants {

    private Map<URI, HttpResource> resources;

    /**
     * Creates a new HttpRequestHandler, without any HTTP resources.
     */
    public HttpRequestHandler() {
        this.resources = new HashMap<URI, HttpResource>();
    }

    /**
     * Add a HttpResource.
     * @param relativeUri the URI the resource will be located at
     * @param resource the resource
     */
    public void addHttpResource(URI relativeUri, HttpResource resource) {
        resources.put(relativeUri, resource);
    }

    public Iterator<Map.Entry<URI, HttpResource>> getHttpResources() {
        return resources.entrySet().iterator();
    }

    /**
     * Method that will be called when there is a new HttpRequest.
     * @param request the new request
     */
    public void handleHttpRequest(HttpRequest request, HttpResponse response) {
        if (request.isHandled()) {
            return; // Job already done
        }
        URI requestUri = request.getRequestUri();
        if (request.isGet() || request.isHead()) {
            requestUri = URI.create(requestUri.getPath());
            HttpResource resource = resources.get(requestUri);
            if (resource != null) {
                response.setStatusCode(STATUS_OK);
                response.setHeader(resource.getHttpContentTypeHeader());
                if (request.isGet()) {
                    response.setContent(resource.getAsByteArray());
                } else {
                    response.setContentLength(resource.getLength());
                }
            } else {
                response.setStatusCode(STATUS_NOT_FOUND);
            }
        }
        try {
            response.writeToOutputStream();
            request.setHandled(true);
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
    }
}
