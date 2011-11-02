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
 * InetTools.java
 * Created on Dec 2, 2010, 12:01:01 PM
 */
package com.googlecode.mipnp.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * This class provides static methods to get more out of an InetAddress object.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class InetTools {

    /**
     * Returns the address of the local host.<br/>
     * Every attempt is made to find an address other than the loopback address. 
     * If no address is found, the loopback address will be returned.
     * @return the local host address
     * @throws UnknownHostException if an error occurred while getting the local host address.
     */
    public static InetAddress getLocalHost() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();

        if (localHost.isLoopbackAddress()) {
            InetAddress[] addrs;
            try {
                addrs = getInetAddresses();
                for (int i = 0; i < addrs.length; i++) {
                    if (!addrs[i].isLoopbackAddress()) {
                        return addrs[i];
                    }
                }
            } catch (SocketException ex) {
            }
        }

        return localHost;
    }

    /**
     * Gets the first IPv4 address of a network interface that can be found.
     * @param ni the network interface
     * @return the fist IPv4 address of the given network interface or null
     */
    public static InetAddress getInetAddress(NetworkInterface ni) {
        Enumeration<InetAddress> addrs = ni.getInetAddresses();

        while (addrs.hasMoreElements()) {
            InetAddress addr = addrs.nextElement();
            if (isIPv4(addr.getAddress())) {
                return addr;
            }
        }

        return null;
    }

    /**
     * Returns all available IPv4 addresses.
     * @return all available IPv4 addresses
     */
    public static InetAddress[] getInetAddresses() throws SocketException {
        List<InetAddress> addresses = new ArrayList<InetAddress>();
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();

        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                if (isIPv4(ia.getAddress())) {
                    addresses.add(ia);
                }
            }
        }

        InetAddress[] ret = new InetAddress[addresses.size()];
        addresses.toArray(ret);
        return ret;
    }

    /**
     * Returns true if the given IP address is version 4.
     * @param ip the IP address to check
     * @return true if the given IP address is IPv4, false otherwise
     */
    public static boolean isIPv4(byte[] ip) {
        return (ip.length == 4 ? true : false);
    }

    /**
     * Returns the display names of the network interfaces.<br/>
     * This method filters out the loopback interfaces.
     * @return the display names of the network interfaces
     * @throws SocketException if an I/O error occurs
     */
    public static String[] getNetworkInterfaceNames() throws SocketException {
        List<String> result = new ArrayList<String>();
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();

        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            if (!ni.isLoopback()) {
                result.add(ni.getDisplayName());
            }
        }

        return result.toArray(new String[result.size()]);
    }
}
