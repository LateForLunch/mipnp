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
 * HttpInputStream.java
 * Created on Dec 3, 2010, 2:41:54 PM
 */
package domain.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpOutputWriter extends Writer {

    private OutputStream out;
    private Charset cs;
    private CharsetEncoder cse;

    public HttpOutputWriter(OutputStream out, Charset cs) {
        super(out);
        this.out = out;
        setCharset(cs);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        CharBuffer cb = CharBuffer.wrap(cbuf, off, len);
        byte[] buf = cse.encode(cb).array();
        out.write(buf, 0, buf.length);
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    public String getCharset() {
        return cs.displayName();
    }

    public void setCharset(Charset cs) {
        this.cs = cs;
        this.cse = cs.newEncoder();
    }
}
