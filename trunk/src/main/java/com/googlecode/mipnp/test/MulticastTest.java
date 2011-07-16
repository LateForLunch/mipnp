/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MulticastTest.java
 * Created on Jul 15, 2011, 5:03:08 PM
 */
package com.googlecode.mipnp.test;

import com.googlecode.mipnp.ssdp.SsdpConstants;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MulticastTest implements SsdpConstants {

    public static void main(String[] args) {
        String msg = "Hello";
//        InetAddress group;
        try {
//            group = InetAddress.getByName("228.5.6.7");
            InetSocketAddress addr = new InetSocketAddress(
                    SSDP_DEFAULT_MULTICAST_ADDRESS, SSDP_DEFAULT_PORT);
            MulticastSocket s = new MulticastSocket(SSDP_DEFAULT_PORT);
//            s.joinGroup(group);
            s.joinGroup(addr, NetworkInterface.getByName("eth0"));
            DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), addr.getAddress(), addr.getPort());
            s.send(hi);
            // get their responses!
    //        byte[] buf = new byte[1000];
    //        DatagramPacket recv = new DatagramPacket(buf, buf.length);
    //        s.receive(recv);
    //        ...
            // OK, I'm done talking - leave the group...
            s.leaveGroup(addr, NetworkInterface.getByName("eth0"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
