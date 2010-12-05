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
import domain.http.HttpServer;
import domain.http.IHttpRequestHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpRequestHandler implements IHttpRequestHandler, HttpConstants {

    private Map<URI, HttpResource> resources;

    public HttpRequestHandler() {
        this.resources = new HashMap<URI, HttpResource>();
    }

    public void addHttpResource(URI relativeUri, HttpResource resource) {
        resources.put(relativeUri, resource);
    }

    public void handleHttpRequest(HttpRequest request) {
        if (request.isHandled()) {
            return; // Job already done
        }
        URI requestUri = request.getRequestUri();
        System.out.println("HTTP Request: " + request.getMethod() + " " + requestUri); // TEST
        HttpResponse response = null;
        if (request.isGet() || request.isHead()) {
            requestUri = URI.create(requestUri.getPath());
            HttpResource resource = resources.get(requestUri);
            if (resource != null) {
                response = createResponse(request, 200);
                response.setHeader(resource.getHttpContentTypeHeader());
                if (request.isGet()) {
                    response.setContent(resource.getAsByteArray());
                } else {
                    response.setContentLength(resource.getLength());
                }
            } else {
                response = createResponse(request, 404);
            }
        }
        try {
            response.writeToRequest();
            request.setHandled(true);
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }
    }

    private HttpResponse createResponse(HttpRequest request, int statusCode) {
        HttpResponse response = new HttpResponse(request);
        response.setStatusCode(statusCode);
        return response;
    }

    // TEST
    public static void main(String[] args) throws URISyntaxException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating HTTP server...");
        HttpServer httpServer = new HttpServer(8080, 0, null);
        HttpRequestHandler handler = new HttpRequestHandler();
        httpServer.addRequestHandler(handler);
        String content = "<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                + "<html><body>Java HttpServer works!µ</body></html>";
        HttpResource r1 = new HttpResource(content.getBytes("UTF-8"), "text/html", "utf-8");
        handler.addHttpResource(new URI("/"), r1);
        try {
            httpServer.start();
        } catch (IOException ex) {
            System.out.println("FAILED");
            ex.printStackTrace();
            System.exit(1);
        }
        System.out.println("OK");
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        httpServer.stop();
    }
}
