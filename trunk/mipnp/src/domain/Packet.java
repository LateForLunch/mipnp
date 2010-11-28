/*
 * Packet.java
 * Created on Nov 27, 2010, 2:29:41 PM
 */
package domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/*
 * TODO:
 * - When using write(String str, Charset cs), the data comes before the data already in the buffer
 */

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Packet {

    public static final Charset DEFAULT_CHARSET = Charset.forName("US-ASCII");

    private ByteArrayOutputStream baos;
    private OutputStreamWriter osw;

    public Packet() {
        this(DEFAULT_CHARSET);
    }

    public Packet(Charset defaultCharset) {
        this.baos = new ByteArrayOutputStream();
        this.osw = new OutputStreamWriter(baos, defaultCharset);
    }

    /* Packet */

    /**
     * Write a String with the given Charset.
     * @param str the String to write
     * @param cs the Charset to use for the encoding
     * @throws IOException see {@link OutputStream#write(byte[]) OutputStream.write}
     */
    public void write(String str, Charset cs) throws IOException {
        ByteBuffer bb = cs.encode(str);
        write(bb.array());
    }

    /* OutputStreamWriter */

    /**
     * see {@link OutputStreamWriter#write(java.lang.String, int, int) OutputStreamWriter.write}
     */
    public void write(String str, int off, int len) throws IOException {
        osw.write(str, off, len);
    }

    /**
     * see {@link OutputStreamWriter#write(char[], int, int) OutputStreamWriter.write}
     */
    public void write(char[] cbuf, int off, int len) throws IOException {
        osw.write(cbuf, off, len);
    }

    /**
     * see {@link OutputStreamWriter#write(int) OutputStreamWriter.write}
     */
    public void write(int c) throws IOException {
        osw.write(c);
    }

    /**
     * see {@link OutputStreamWriter#getEncoding() OutputStreamWriter.getEncoding}
     */
    public String getEncoding() {
        return osw.getEncoding();
    }

    /**
     * see {@link OutputStreamWriter#flush() OutputStreamWriter.flush}
     */
    public void flush() throws IOException {
        osw.flush();
    }

    /**
     * see {@link OutputStreamWriter#close() OutputStreamWriter.close}
     */
    public void close() throws IOException {
        osw.close();
    }

    /* Writer */

    /**
     * see {@link Writer#write(java.lang.String) Writer.write}
     */
    public void write(String str) throws IOException {
        osw.write(str);
    }

    /**
     * see {@link Writer#write(char[]) Writer.write}
     */
    public void write(char[] cbuf) throws IOException {
        osw.write(cbuf);
    }

    /**
     * see {@link Writer#append(char) Writer.append}
     */
    public Writer append(char c) throws IOException {
        return osw.append(c);
    }

    /**
     * see {@link Writer#append(java.lang.CharSequence, int, int) Writer.append}
     */
    public Writer append(CharSequence csq, int start, int end) throws IOException {
        return osw.append(csq, start, end);
    }

    /**
     * see {@link Writer#append(java.lang.CharSequence) Writer.append}
     */
    public Writer append(CharSequence csq) throws IOException {
        return osw.append(csq);
    }

    /* ByteArrayOutputStream */

    /**
     * see {@link ByteArrayOutputStream#writeTo(java.io.OutputStream) ByteArrayOutputStream.writeTo}
     */
    public synchronized void writeTo(OutputStream out) throws IOException {
        baos.writeTo(out);
    }

    /**
     * see {@link ByteArrayOutputStream#write(byte[], int, int) ByteArrayOutputStream.write}
     */
    public synchronized void write(byte[] b, int off, int len) {
        baos.write(b, off, len);
    }

    /**
     * see {@link ByteArrayOutputStream#toString() ByteArrayOutputStream.toString}
     */
    @Override
    public synchronized String toString() {
        return baos.toString();
    }

    /**
     * see {@link ByteArrayOutputStream#toByteArray() ByteArrayOutputStream.toByteArray}
     */
    public synchronized byte[] toByteArray() {
        return baos.toByteArray();
    }

    /**
     * see {@link ByteArrayOutputStream#size() ByteArrayOutputStream.size}
     */
    public synchronized int size() {
        return baos.size();
    }

    /**
     * Flush the OutputStreamWriter and close the ByteArrayOutputStream.
     * <br />
     * see {@link OutputStreamWriter#flush() OutputStreamWriter.flush}
     * <br />
     * see {@link ByteArrayOutputStream#reset() ByteArrayOutputStream.reset}
     * @throws IOException see {@link OutputStreamWriter#flush() flush}
     */
    public synchronized void reset() throws IOException {
        flush();
        baos.reset();
    }

    /* OutputStream */

    /**
     * see {@link OutputStream#write(byte[]) OutputStream.write}
     */
    public void write(byte[] b) throws IOException {
        baos.write(b);
    }
}
