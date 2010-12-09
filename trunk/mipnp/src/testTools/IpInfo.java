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
 * IpInfo.java
 * Created on Nov 27, 2010, 1:15:00 PM
 */
package testTools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class IpInfo {

    /**
     * Starts the IpInfo test. This should print all the network interfaces.
     */
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
            System.out.println();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

        try {
            Enumeration<NetworkInterface> nis =
                    NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                System.out.println("Interface: " + ni.getDisplayName());

                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress ia = ias.nextElement();
                    System.out.println("  IP: " + ia.getHostAddress());
                    System.out.println("    Hostname: " + ia.getHostName());
                    System.out.println("    IPv4: " + (ia.getAddress().length == 4 ? "true" : "false"));
                    System.out.println("    isLoopbackAddress: " + ia.isLoopbackAddress());
                    System.out.println("    isLinkLocalAddress: " + ia.isLinkLocalAddress());
                }
                System.out.println();
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }
}
