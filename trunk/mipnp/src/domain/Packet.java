/*
 * Packet.java
 * Created on Nov 27, 2010, 2:29:41 PM
 */
package domain;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Packet {

    private static final Charset CHARSET = Charset.forName("US-ASCII");

    private ByteArrayOutputStream baos;
    private OutputStreamWriter osw;

    public Packet() {
        this.baos = new ByteArrayOutputStream();
        this.osw = new OutputStreamWriter(baos, CHARSET);
    }
}
