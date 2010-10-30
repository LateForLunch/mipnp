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
 * DiscoveryCli.java
 * Created on Oct 30, 2010, 5:18:00 PM
 */
package cli;

import java.io.PrintStream;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class DiscoveryCli {

    public DiscoveryCli() {
    }

    public void printStartMessage(PrintStream out) {
        out.println("Starting discovery thread...");
    }
}
