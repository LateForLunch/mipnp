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
import domain.shutdown.IShutdownListener;
import java.io.IOException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServerCli implements IHttpRequestHandler, IShutdownListener {

    private HttpServerController controller;

    public HttpServerCli(HttpServerController controller) {
        this.controller = controller;
        startMessage();
        try {
            controller.start();
        } catch (IOException ex) {
            System.err.println("Could not start HTTP server: \n" + ex.getMessage());
            System.exit(2);
        }
        okMessage();
    }

    /**
     * Prints the method and request URI of the HttpRequest.
     * @param request
     */
    public void handleHttpRequest(HttpRequest request) {
        if (request.isGet() || request.isHead()) {
            System.out.println(
                    "HTTP request: " + request.getMethod() +
                    " " + request.getRequestUri());
        }
    }

    public void shutdown() {
        stopMessage();
        controller.stop();
        okMessage();
    }

    private void startMessage() {
        System.out.print("Starting HTTP server on address " +
                controller.getBindAddr() + " port " + controller.getPort() + "...");
    }

    private void stopMessage() {
        System.out.print("Stopping HTTP server...");
    }

    private void okMessage() {
        System.out.println("OK");
    }
}
