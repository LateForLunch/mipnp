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

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class HttpServerMainThread implements Runnable {

    private static final int DEFAULT_POOL_SIZE = 5;

    private ServerSocket serverSocket;
    private ExecutorService pool;

    public HttpServerMainThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.pool = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
