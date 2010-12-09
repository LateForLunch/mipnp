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
 * Main.java
 * Created on Oct 17, 2010, 3:43:32 PM
 */
package mipnp;

import cli.MainController;
import cli.Settings;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Main {

    /**
     * Starts the program.
     * @param args the command line arguments, see {@link Settings#help()}
     */
    public static void main(String[] args) {
        /*
         * TEST:
         * - Don't use the stop button in NetBeans.
         * - Use 'q' <Enter> instead.
         * - ShutdownHook should print "Shutdown" when the program is shutting down.
         * - ShutdownHook should work with System.exit(int), ^C, etc...
         */
        new MainController(args);
    }
}
