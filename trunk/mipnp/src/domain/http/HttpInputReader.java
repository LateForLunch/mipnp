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
 * HttpInputReader.java
 * Created on Dec 3, 2010, 3:01:40 PM
 */
package domain.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpInputReader extends Reader {

    private InputStream in;
    private BufferedInputStream bis;
    private ByteArrayOutputStream baos;
    private Charset cs;
    private CharsetDecoder csd;

    public HttpInputReader(InputStream in, Charset cs) {
        super(in);
        this.in = in;
        this.bis = new BufferedInputStream(in);
        this.baos = new ByteArrayOutputStream();
        setCharset(cs);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        CharBuffer cb = CharBuffer.wrap(cbuf, off, len);
        ByteBuffer bb = ByteBuffer.allocate(10);
        return -1;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    public String getCharset() {
        return cs.displayName();
    }

    public void setCharset(Charset cs) {
        this.cs = cs;
        this.csd = cs.newDecoder();
    }
}
