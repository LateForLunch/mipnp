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
 * SingleInstanceNotifier.java
 * Created on Nov 6, 2011, 10:37:31 AM
 */
package com.googlecode.mipnp.instance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class SingleInstanceNotifier implements Runnable {

    private static final int SOCKET_TIMEOUT = 250;

    private Socket socket;
    private List<SingleInstanceListener> listeners;

    public SingleInstanceNotifier(
            Socket socket,
            List<SingleInstanceListener> listeners) {

        this.socket = socket;
        this.listeners = listeners;
    }

    public void run() {
        String[] args = null;
        ObjectInputStream ois = null;

        try {
            socket.setSoTimeout(SOCKET_TIMEOUT);
            ois = new ObjectInputStream(socket.getInputStream());
            args = (String[]) ois.readObject();
        } catch (ClassNotFoundException ex) {
            // Ignore
        } catch (IOException ex) {
            // Ignore
        } finally {
            if (ois != null) {
                try {
                    ois.close();
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

        if (args != null) {
            notifyListeners(args);
        }
    }

    private void notifyListeners(String[] args) {
        for (SingleInstanceListener l : listeners) {
            l.instanceStarted(args);
        }
    }
}
