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
 * HttpResponse.java
 * Created on Dec 4, 2010, 1:55:16 PM
 */
package domain.http;

import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class HttpResponse extends HttpPacket {

    private int statusCode;
    private String reasonPhrase;

    public HttpResponse() {
        super();
    }

    public HttpResponse(HttpRequest request) {
        super(request.getInputStream(), request.getOutputStream());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode, boolean updateReason) {
        this.statusCode = statusCode;
        setReasonPhrase(HTTP_STATUS.get(statusCode));
    }

    public void setStatusCode(int statusCode) {
        setStatusCode(statusCode, true);
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public void writeToOutputStream() throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(getOutputStream());
        HttpOutputStream hos = new HttpOutputStream(bos);
        String statusLine = getVersion() + " " +
                getStatusCode() + " " + getReasonPhrase();
        hos.writeLine(statusLine);
        String[] headers = getHeaders();
        for (int i = 0; i < headers.length; i++) {
            hos.writeLine(headers[i]);
        }
        hos.writeLine(); // Write empty line
        byte[] content = getContent();
        if (content != null) {
            hos.write(getContent());
        }
        hos.flush();
    }
}
