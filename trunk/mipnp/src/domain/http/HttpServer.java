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
 * A HttpServer can listen to HTTP requests and call HttpRequestHandlers.<br />
 * This class will create a new Thread.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpServer implements HttpConstants {

    private int port;
    private int backlog;
    private InetAddress bindAddr;
    private ServerSocket serverSocket;
    private HttpServerMainThread serverMain;
    private Thread serverThread;
    private List<IHttpRequestHandler> handlers;
    private IHttpRequestHandler defaultHandler;

    public HttpServer() {
        this(HTTP_DEFAULT_PORT, 0, null);
    }

    /**
     * Creates a new HTTP server.<br />
     * see {@link ServerSocket#ServerSocket(int, int, java.net.InetAddress)} for more info.
     * @param port the port to listen to
     * @param backlog
     * @param bindAddr the address to bind to
     */
    public HttpServer(int port, int backlog, InetAddress bindAddr) {
        setPort(port);
        setBacklog(backlog);
        setBindAddr(bindAddr);
        this.handlers = new ArrayList<IHttpRequestHandler>();
        this.defaultHandler = new HttpDefaultRequestHandler();
    }

    /**
     * Starts the HTTP server.<br />
     * When a new HTTP request comes in, the HttpRequestHandlers will be called.
     * @throws IOException when an I/O error occurs while starting the server
     */
    public void start() throws IOException {
        this.serverSocket = new ServerSocket(port, backlog, bindAddr);
        this.serverMain = new HttpServerMainThread(this, serverSocket);
        this.serverThread = new Thread(serverMain);
        serverThread.setName("HtppServerMain");
        serverThread.start();
    }

    /**
     * Stop the HTTP server.
     */
    public void stop() {
        serverMain.stop();
        this.serverThread = null;
        this.serverMain = null;
        this.serverSocket = null;
    }

    /**
     * Add a HttpRequestHandler.<br />
     * The request handler will be called when a new HTTP request comes in.
     * @param handler the handler to add
     */
    public void addRequestHandler(IHttpRequestHandler handler) {
        handlers.add(handler);
    }

    /**
     * Remove a handler.
     * @param handler the handler to remove
     */
    public void removeRequestHandler(IHttpRequestHandler handler) {
        handlers.remove(handler);
    }

    /**
     * Notify all the handler.
     * @param request the HTTP request
     */
    protected void notifyHandlers(HttpRequest request, HttpResponse response) {
        for (IHttpRequestHandler handler : handlers) {
            handler.handleHttpRequest(request, response);
        }
        if (!request.isHandled()) {
            defaultHandler.handleHttpRequest(request, response);
        }
    }

    public int getPort() {
        if (serverSocket != null) {
            return serverSocket.getLocalPort();
        }
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
