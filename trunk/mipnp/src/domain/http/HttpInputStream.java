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
 * Created on Dec 4, 2010, 12:01:52 PM
 */
package domain.http;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpInputStream extends FilterInputStream implements HttpConstants {

    public HttpInputStream(InputStream in) {
        super(in);
    }

    public String readLine() throws IOException {
        byte[] buf = new byte[1];
        ByteArrayOutputStream line = new ByteArrayOutputStream();

        int read = read(buf);
        if (buf[0] == LFb || buf[0] == CRb) {
            read = read(buf);
        }
        while (read > 0) {
            if (buf[0] == LFb) {
                break;
            } else if (buf[0] == CRb) {
                break;
            } else {
                line.write(buf);
            }
            read = read(buf);
        }
        return new String(line.toByteArray(), HTTP_DEFAULT_CHARSET);
    }
}
