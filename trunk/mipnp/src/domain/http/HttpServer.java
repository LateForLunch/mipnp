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
 * HttpServer.java
 * Created on Dec 2, 2010, 12:57:12 PM
 */
package domain.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServer implements HttpConstants {

    private int port;
    private int backlog;
    private InetAddress bindAddr;
    private ServerSocket serverSocket;
    private HttpServerMainThread server;
    private Thread serverThread;
    private List<HttpRequestHandler> handlers;

    public HttpServer() {
        this(HTTP_DEFAULT_PORT, 0, null);
    }

    public HttpServer(int port, int backlog,  InetAddress bindAddr) {
        setPort(port);
        setBacklog(backlog);
        setBindAddr(bindAddr);
        this.handlers = new ArrayList<HttpRequestHandler>();
    }

    public void start() throws IOException {
        this.serverSocket = new ServerSocket(port, backlog, bindAddr);
        this.server = new HttpServerMainThread(this, serverSocket);
        this.serverThread = new Thread(server);
        serverThread.setName("HtppServerMain");
        serverThread.start();
    }

    public void stop() {
        server.stop();
        this.serverThread = null;
        this.server = null;
    }

    public void addRequestHandler(HttpRequestHandler handler) {
        handlers.add(handler);
    }

    public void removeRequestHandler(HttpRequestHandler handler) {
        handlers.remove(handler);
    }

    protected void notifyHandlers(HttpRequest request) {
        for (HttpRequestHandler handler : handlers) {
            handler.handleRequest(request);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public InetAddress getBindAddr() {
        return bindAddr;
    }

    public void setBindAddr(InetAddress bindAddr) {
        this.bindAddr = bindAddr;
    }
}
