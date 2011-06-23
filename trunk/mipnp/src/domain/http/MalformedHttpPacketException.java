/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MalformedHttpPacketException.java
 * Created on Jun 23, 2011, 6:27:56 PM
 */
package domain.http;

import java.io.IOException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MalformedHttpPacketException extends IOException {

    public MalformedHttpPacketException() {
        super("Illegal HTTP Packet");
    }

    public MalformedHttpPacketException(String message) {
        super(message);
    }
}
