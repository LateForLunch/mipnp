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
 * HttpServerController.java
 * Created on Dec 8, 2010, 9:10:12 PM
 */
package cli;

import domain.http.HttpServer;
import domain.shutdown.IShutdownListener;
import domain.shutdown.ShutdownHook;
import domain.upnp.HttpRequestHandler;
import domain.upnp.HttpResource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServerController {

    private ShutdownHook shutdownHook;
    private HttpServer httpServer;
    private HttpServerCli httpServerCli;
    private HttpRequestHandler httpRequestHandler;

    public HttpServerController(ShutdownHook shutdownHook, HttpServer server) {
        this.shutdownHook = shutdownHook;
        this.httpServer = server;
        this.httpRequestHandler = new HttpRequestHandler();
        test();
        httpServer.addRequestHandler(httpRequestHandler);
        this.httpServerCli = new HttpServerCli(this);
        shutdownHook.addShutdownListener(httpServerCli);
        httpServer.addRequestHandler(httpServerCli);
    }

    public void start() throws IOException {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop();
    }

    public int getPort() {
        return httpServer.getPort();
    }

    public String getBindAddr() {
        return httpServer.getBindAddr().getHostAddress();
    }

    public void addHttpResource(URI relativeUri, HttpResource resource) {
        httpRequestHandler.addHttpResource(relativeUri, resource);
    }

    public void shutdown() {
        stop();
    }

    private void test() {
        String content = "<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                + "<html><body>Java HttpServer works!µ</body></html>";
        HttpResource r1;
        try {
            r1 = new HttpResource(content.getBytes("UTF-8"), "text/html", "utf-8");
            addHttpResource(new URI("/"), r1);
        } catch (UnsupportedEncodingException ex) {
            // should not happen
        } catch (URISyntaxException ex) {
            // should not happen
        }
    }
}
