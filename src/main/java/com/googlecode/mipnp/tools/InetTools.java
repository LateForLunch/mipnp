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
            InetAddress[] addrs = getAllAddresses();
            for (int i = 0; i < addrs.length; i++) {
                if (!addrs[i].isLoopbackAddress()) {
                    return addrs[i];
                }
            }
        }
        return localHost;
    }

    /**
     * Returns all available IPv4 addresses.
     * @return all available IPv4 addresses
     */
    public static InetAddress[] getAllAddresses() {
        List<InetAddress> addresses = new ArrayList<InetAddress>();
        Enumeration<NetworkInterface> nis;

        try {
            nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // TODO: temp fix
                if (!ni.getDisplayName().startsWith("eth")) {
                    continue;
                }
                // END temp fix
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress ia = ias.nextElement();
                    if (isIPv4(ia.getAddress())) {
                        addresses.add(ia);
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
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

    /*
     * Get the IP address of a network interface.
     * @param interf the network interface
     * @return the IP address of the given network interface or null if nothing was found
     * @throws SocketException if there was an error while retrieving the network interface
     */
//    public static InetAddress interfaceToIp(NetworkInterface nic) {
//        Enumeration<InetAddress> ias = nic.getInetAddresses();
//        while (ias.hasMoreElements()) {
//            InetAddress ia = ias.nextElement();
//            // Only support for IPv4 for now
//            if (isIPv4(ia.getAddress())) {
//                return ia;
//            }
//        }
//        return null;
//    }
}
