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
 * Settings.java
 * Created on Dec 1, 2010, 8:09:18 PM
 */
package cli;

import cli.menu.CliMenu;
import domain.tools.NetworkInterfaceTools;
import java.net.SocketException;
import java.util.Properties;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Settings {

    public static final String INTERFACE = "interface";
    public static final String BIND_ADDRESS = "bindaddr";

    private static Properties settings;

    static {
        settings = new Properties();
        // TODO: Set default settings
    }

    public static void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i") || args[i].equalsIgnoreCase("--" + INTERFACE)) {
                setProperty(INTERFACE, args[++i]);
            }
        }
    }

    public static void checkSettings() {
        /*
         * Interface
         */
        if (Settings.getProperty(Settings.INTERFACE) == null) {
            try {
                String[] networkInerfaces = NetworkInterfaceTools.list();
                int option = askUser(
                        "Please select the network interface to use",
                        networkInerfaces, -1);
                String ifaip = networkInerfaces[option - 1];
                int index = ifaip.indexOf('(');
                String ifa = ifaip.substring(0, index).trim();
                String ip = ifaip.substring(index + 1, ifaip.length() - 1);
                Settings.setProperty(Settings.INTERFACE, ifa);
                Settings.setProperty(BIND_ADDRESS, ip);
            } catch (SocketException ex) {
                System.err.println("TODO (i/o error)");
                System.exit(1);
            }
        } else {
            try {
                String ip = NetworkInterfaceTools.interfaceToIp(getProperty(INTERFACE));
                if (ip == null) {
                    throw new SocketException();
                }
                setProperty(BIND_ADDRESS, ip);
            } catch (SocketException ex) {
                System.err.println("TODO (i/o error)");
                System.exit(1);
            }
        }
    }

    public static synchronized Object setProperty(String key, String value) {
        return settings.setProperty(key, value);
    }

    public static String getProperty(String key) {
        return settings.getProperty(key);
    }

    private static int askUser(String question, String[] options, int defaultValue) {
        CliMenu menu = new CliMenu(question, options, defaultValue);
        return menu.show();
    }
}
