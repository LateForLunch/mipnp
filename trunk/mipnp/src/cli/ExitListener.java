/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExitListener.java
 * Created on Oct 17, 2010, 3:52:34 PM
 */
package cli;

import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExitListener implements Runnable {

    private Scanner input;

    public ExitListener() {
        this.input = new Scanner(System.in);
    }

    public void run() {
System.out.println("Press 'q' to exit."); // TEST
        while (true) {
            String str = input.nextLine();
            if (str.charAt(0) == 'q') {
                System.exit(0);
            }
        }
    }
}
