/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
