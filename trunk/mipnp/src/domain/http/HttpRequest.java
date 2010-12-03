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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
        BufferedInputStream bis = new BufferedInputStream(input);
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
        }
    }

//    public static void main(String[] args) {
//        String testStr = "line\r\nline";
//        Scanner scanner = new Scanner(testStr);
//        System.out.println(Arrays.toString(scanner.nextLine().getBytes()));
//        System.out.println(Arrays.toString(scanner.next().getBytes()));
//        System.out.println(Arrays.toString(scanner.nextLine().getBytes()));
//    }
}
