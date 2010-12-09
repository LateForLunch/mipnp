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
 * HttpServerMainThread.java
 * Created on Dec 2, 2010, 1:08:53 PM
 */
package domain.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * See {@link HttpServer}
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class HttpServerMainThread implements Runnable {

    private static final int DEFAULT_POOL_SIZE = 5;
    private static final int DEFAULT_SOCKET_TIMEOUT = 5000; // 5 seconds

    private HttpServer server;
    private ServerSocket serverSocket;
    private ExecutorService pool;

    public HttpServerMainThread(HttpServer server, ServerSocket serverSocket) {
        this.server = server;
        this.serverSocket = serverSocket;
        this.pool = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(DEFAULT_SOCKET_TIMEOUT);
                pool.execute(new HttpWorkerThread(server, socket));
                Thread.yield();
            } catch (SocketException ex) {
                // ServerSocket closed
                return;
            } catch (RejectedExecutionException ex) {
                // Pool closed
                return;
            } catch (IOException ex) {
                // I/O error during accept(), try again
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            // ignore
        }
        pool.shutdown();
        try {
            pool.awaitTermination(DEFAULT_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            System.err.println("Forcing HttpServer Threads to shutdown."); // TEST
            pool.shutdownNow();
            try {
                pool.awaitTermination(DEFAULT_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ex1) {
                // Nothing we can do
            }
        }
        this.serverSocket = null;
        this.pool = null;
    }
}
