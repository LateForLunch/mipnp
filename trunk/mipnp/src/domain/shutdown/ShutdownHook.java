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
 * ShutdownHook.java
 * Created on Oct 17, 2010, 3:49:46 PM
 */
package domain.shutdown;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ShutdownHook {

    private List<IShutdownListener> listeners;
    private boolean shutdownStarted;

    public ShutdownHook() {
        this.listeners = new ArrayList<IShutdownListener>();
        setShutdownStarted(false);
        Runtime.getRuntime().addShutdownHook(new Thread(new Hook()));
    }

    public synchronized boolean addShutdownListener(IShutdownListener l) {
        if (!isShutdownStarted()) {
            return listeners.add(l);
        }
        return false;
    }

    public synchronized boolean removeShutdownListener(IShutdownListener l) {
        if (!isShutdownStarted()) {
            return listeners.remove(l);
        }
        return false;
    }

    private synchronized boolean isShutdownStarted() {
        return shutdownStarted;
    }

    private synchronized void setShutdownStarted(boolean shutdownStarted) {
        this.shutdownStarted = shutdownStarted;
    }

    private class Hook implements Runnable {
        public void run() {
            // Don't allow add/remove ShutdownListener anymore
            setShutdownStarted(true);
System.out.println("Shutdown."); // TEST
            for (IShutdownListener l : listeners) {
                l.shutdown();
            }
        }
    }
}
