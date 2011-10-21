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
 * MainCli.java
 * Created on Oct 3, 2011, 3:15:27 PM
 */
package com.googlecode.mipnp.cli;

import com.googlecode.mipnp.controller.MainController;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MainCli {

    private MainController controller;

    public MainCli(MainController controller) {
        this.controller = controller;
        printCopyNotice();
        start();
        mainLoop();
        stop();
    }

    private void printCopyNotice() {
        System.out.println("MiPnP  Copyright (C) 2010, 2011  Jochem Van denbussche");
        System.out.println("This program comes with ABSOLUTELY NO WARRANTY.");
        System.out.println("This is free software, and you are welcome to redistribute it under certain conditions.");
        System.out.println("Read the LICENSE.txt file for more information.");
        System.out.println();
    }

    private void start() {
        try {
            controller.start();
            System.out.println();
            System.out.println("MiPnP started");
        } catch (IOException ex) {
            System.out.println("An I/O error occurred while starting MiPnP.");
            System.out.println("Details: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.out.println("There is probably an illegal character " +
                    "in one of the UPnP service identifiers.");
            System.out.println("Details: " + ex.getMessage());
        }
    }

    private void mainLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 'q' to stop.");
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
    }

    private void stop() {
        try {
            controller.stop();
        } catch (IOException ex) {
            System.out.println("An I/O error occurred while stopping MiPnP:");
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            // This should not happen
        }
        controller.exit();
    }
}
