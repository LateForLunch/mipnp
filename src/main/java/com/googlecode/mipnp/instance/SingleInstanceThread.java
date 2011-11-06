/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * SingleInstanceThread.java
 * Created on Nov 6, 2011, 10:18:01 AM
 */
package com.googlecode.mipnp.instance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SingleInstanceThread implements Runnable {

    private static final int MAX_THREADS = 10;

    private ServerSocket serverSocket;
    private List<SingleInstanceListener> listeners;
    private ExecutorService threadPool;

    public SingleInstanceThread(
            ServerSocket serverSocket,
            List<SingleInstanceListener> listeners) {

        this.serverSocket = serverSocket;
        this.listeners = listeners;
        this.threadPool = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public void run() {
        try {
            while (!Thread.interrupted() && !serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    threadPool.execute(
                            new SingleInstanceNotifier(socket, listeners));
                } catch (IOException ex) {
                }
            }
        } finally {
            threadPool.shutdown();
            threadPool.shutdownNow();
        }
    }
}
