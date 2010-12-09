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
 * This shutdown hook waits until the program is about to shut down.<br />
 * It then warns all {@link IShutdownListener}.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ShutdownHook {

    private List<IShutdownListener> listeners;
    private boolean shutdownStarted;

    /**
     * Creates a new ShutdownHook.
     */
    public ShutdownHook() {
        this.listeners = new ArrayList<IShutdownListener>();
        setShutdownStarted(false);
        Runtime.getRuntime().addShutdownHook(new Thread(new Hook()));
    }

    /**
     * Add a shutdown listener.
     * @param l the shutdown listener to add
     * @return true if successful, false otherwise
     */
    public synchronized boolean addShutdownListener(IShutdownListener l) {
        if (!isShutdownStarted()) {
            return listeners.add(l);
        }
        return false;
    }

    /**
     * Remoce a shutdown listener
     * @param l the shutdown listener to remove
     * @return true is successful, false otherwise
     */
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
            for (IShutdownListener l : listeners) {
                l.shutdown();
            }
        }
    }
}
