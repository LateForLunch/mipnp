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
 * NetworkInterfaceTools.java
 * Created on Dec 2, 2010, 12:01:01 PM
 */
package domain.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * This class provides static methods to get more info about the network interfaces.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class NetworkInterfaceTools {

    /**
     * Makes a list of all network interfaces available.
     * @return a list that contains all available network interfaces
     * @throws SocketException if there is an error while retrieving all network interfaces
     */
    public static String[] list() throws SocketException {
        Enumeration<NetworkInterface> nis =
                NetworkInterface.getNetworkInterfaces();
        List<String> list = new ArrayList<String>();

        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                // Only support for IPv4 for now
                if (isIPv4(ia.getAddress())) {
                    list.add(ni.getName() + " (" + ia.getHostAddress() + ")");
                }
            }
        }
        String[] ret = new String[list.size()];
        list.toArray(ret);
        return ret;
    }

    /**
     * Check if an IP address is version 4.
     * @param ip the IP address to check
     * @return true if the given IP address is IPv4, false otherwise
     */
    public static boolean isIPv4(byte[] ip) {
        return (ip.length == 4 ? true : false);
    }

    /**
     * Get the IP address of a network interface.
     * @param interf the network interface
     * @return the IP address of the given network interface or null if nothing was found
     * @throws SocketException if there was an error while retrieving the network interface
     */
    public static String interfaceToIp(String interf) throws SocketException {
        NetworkInterface ni = NetworkInterface.getByName(interf);
        Enumeration<InetAddress> ias = ni.getInetAddresses();
        while (ias.hasMoreElements()) {
            InetAddress ia = ias.nextElement();
            // Only support for IPv4 for now
            if (isIPv4(ia.getAddress())) {
                return ia.getHostAddress();
            }
        }
        return null;
    }
}
