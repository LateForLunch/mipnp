/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main.java
 * Created on Oct 17, 2010, 3:43:32 PM
 */
package mipnp;

import cli.MainController;
import domain.shutdown.ShutdownHook;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * TEST:
         * - Don't use the stop button in NetBeans.
         * - Use 'q' <Enter> instead.
         * - ShutdownHook should print "Shutdown" when the program is shutting down.
         * - ShutdownHook should work with System.exit(int), ^C, etc...
         */
        new ShutdownHook();
        new MainController();

        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
