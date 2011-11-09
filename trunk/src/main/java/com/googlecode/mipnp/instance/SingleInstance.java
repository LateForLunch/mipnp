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
 * SingleInstance.java
 * Created on Nov 6, 2011, 9:45:40 AM
 */
package com.googlecode.mipnp.instance;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SingleInstance {

    private static final byte[] LOCALHOST = new byte[] {127, 0, 0, 1};
    private static final int DEFAULT_PORT = 57429;

    private static SingleInstance instance;

    public static synchronized SingleInstance getInstance() {
        if (instance == null) {
            instance = new SingleInstance();
        }
        return instance;
    }

    private ServerSocket serverSocket;
    private Thread singleInstanceThread;
    private List<SingleInstanceListener> listeners;

    private SingleInstance() {
        this.listeners = new ArrayList<SingleInstanceListener>();
    }

    public boolean lock() {
        if (serverSocket != null) {
            return true;
        }

        try {
            InetAddress localHost = InetAddress.getByAddress(LOCALHOST);
            this.serverSocket = new ServerSocket(DEFAULT_PORT, 0, localHost);
            this.singleInstanceThread = new Thread(
                    new SingleInstanceThread(serverSocket, listeners));
            singleInstanceThread.start();
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    public boolean unlock() {
        if (serverSocket == null) {
            return true;
        }

        try {
            serverSocket.close();
        } catch (IOException ex) {
            return false;
        }

        singleInstanceThread.interrupt();
        try {
            singleInstanceThread.join();
        } catch (InterruptedException ex) {
            // This should not happen
        }
        singleInstanceThread = null;
        serverSocket = null;

        return true;
    }

    public boolean addSingleInstanceListener(SingleInstanceListener l) {
        return listeners.add(l);
    }

    public boolean removeSingleInstanceListener(SingleInstanceListener l) {
        return listeners.remove(l);
    }

    public void notifyOtherInstance() {
        notifyOtherInstance(null);
    }

    public void notifyOtherInstance(String[] args) {
        if (serverSocket != null) {
            return;
        }

        if (args == null) {
            args = new String[0];
        }

        ObjectOutputStream oos = null;
        Socket socket = null;
        try {
            InetAddress localHost = InetAddress.getByAddress(LOCALHOST);
            socket = new Socket(localHost, DEFAULT_PORT);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(args);
            oos.flush();
        } catch (IOException ex) {
            // Ignore - the other instance will not be notified
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
