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
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class NetworkInterfaceTools {

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
                    list.add(ni.getDisplayName() + " (" + ia.getHostAddress() + ")");
                }
            }
        }
        String[] ret = new String[list.size()];
        list.toArray(ret);
        return ret;
    }

    public static boolean isIPv4(byte[] ip) {
        return (ip.length == 4 ? true : false);
    }

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
