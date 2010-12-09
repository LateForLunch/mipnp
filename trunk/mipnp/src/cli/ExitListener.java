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
 * ExitListener.java
 * Created on Oct 17, 2010, 3:52:34 PM
 */
package cli;

import domain.shutdown.IShutdownListener;
import java.util.Scanner;

/**
 * This class will print the "Press 'q' to exit." message.<br />
 * After that it will wait for the user to enter 'q'.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExitListener implements Runnable, IShutdownListener {

    private MainController mainController;
    private Scanner input;

    public ExitListener(MainController mainController) {
        this.mainController = mainController;
        this.input = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Press 'q' to exit.");
        while (true) {
            String str = input.nextLine();
            if (str.charAt(0) == 'q') {
                System.exit(0);
            }
        }
    }

    public void shutdown() {
        System.out.println("Shutdown");
    }
}
