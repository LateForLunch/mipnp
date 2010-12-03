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
 * HttpRequest.java
 * Created on Dec 2, 2010, 9:21:20 PM
 */
package domain.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpRequest extends HttpPacket {

    private String method;
    private String requestUri;

    public HttpRequest() {
    }

    public void parse(InputStream input) throws IOException {
        /*BufferedInputStream bis = new BufferedInputStream(input);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1];
        int read = bis.read(buf);

        while (read > 0) {
            if (Arrays.equals(buf, CRb)) {
                break;
            } else if (Arrays.equals(buf, LFb)) {
                // TODO: line read
            } else {
                baos.write(buf);
            }
            read = bis.read(buf);
        }*/
    }

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        String testStr1 = "line1\r\nline2\r\n\r\n";
        byte[] b1 = testStr1.getBytes(HTTP_DEFAULT_CHARSET);
        byte[] b2 = "µ".getBytes("UTF-8");
        byte[] b = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, b, 0, b1.length);
        System.arraycopy(b2, 0, b, b1.length, b2.length);
        byte[] buf = new byte[b.length];
        System.out.println(Arrays.toString(b));
        System.out.println(new String(b));
        ByteArrayInputStream bais = new ByteArrayInputStream(b);

        Scanner scanner = new Scanner(bais, HTTP_DEFAULT_CHARSET_NAME);
        System.out.println(scanner.nextLine()); // "line1"
        System.out.println(scanner.nextLine()); // "line2"
        System.out.println(scanner.nextLine()); // ""
        System.out.println(bais.available()); // 0 ? -> is not right
        byte[] read = new byte[bais.available()];
        bais.read(read);
        System.out.println(new String(read, Charset.forName("UTF-8")));
    }
}
