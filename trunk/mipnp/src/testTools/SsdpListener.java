/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SsdpListener.java
 * Created on Oct 17, 2010, 3:51:19 PM
 */
package testTools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SsdpListener implements Runnable {

    private static final String ADDRESS = "239.255.255.250";
    private static final int PORT = 1900;

    private InetAddress group;
    private MulticastSocket socket;

    private SsdpListener() throws IOException {
        this.group = InetAddress.getByName(ADDRESS);
        this.socket = new MulticastSocket(PORT);
    }

    public void run() {
        try {
            socket.joinGroup(group);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        while (true) {
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(recv);
                System.out.println(new String(recv.getData()));
            } catch (SocketException ex) { // Socket closed
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            socket.leaveGroup(group);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        socket.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 'q' to stop.\nCreating SSDP listener...");
        SsdpListener listener = null;
        try {
            listener = new SsdpListener();
        } catch (IOException ex) {
            System.out.println(" FAILED\n");
            ex.printStackTrace();
            System.exit(1);
        }
        Thread thread = new Thread(listener);
        System.out.println(" OK\n");
        thread.start();
        while (!(scanner.nextLine().equalsIgnoreCase("q"))) {
            System.out.println("Unknown command.\nPress 'q' to stop.\n");
        }
        listener.stop();
    }
}
