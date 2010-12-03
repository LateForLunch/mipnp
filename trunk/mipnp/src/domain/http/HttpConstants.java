/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche, Tijl Van Assche
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
package domain.http;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on Oct 24, 2010, 10:54:07 AM
 * 
 * See RFC 2616: http://tools.ietf.org/html/rfc2616.html
 * 
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 * @author Tijl Van Assche <tijlvanassche@gmail.com>
 */
public interface HttpConstants {

    public static final int HTTP_DEFAULT_PORT = 80;
    public static final String HTTP_DEFAULT_CHARSET_NAME = "US-ASCII";
    public static final Charset HTTP_DEFAULT_CHARSET =
            Charset.forName(HTTP_DEFAULT_CHARSET_NAME);

    public static final byte[] CRb = "\r".getBytes(HTTP_DEFAULT_CHARSET);
    public static final byte[] LFb = "\n".getBytes(HTTP_DEFAULT_CHARSET);
    public static final byte[] CRLFb = "\r\n".getBytes(HTTP_DEFAULT_CHARSET);
    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char HT = '\t';
    public static final String CRLF = ""+CR+LF;

    /*
     * Version
     */
    public static final String HTTP_VERSION_1_0 = "HTTP/1.0";
    public static final String HTTP_VERSION_1_1 = "HTTP/1.1";
    public static final String HTTP_VERSION = HTTP_VERSION_1_1;

    /*
     * Methods
     */
    public static final String GET = "GET";
    public static final String HEAD = "HEAD";

    /*
     * Header field names
     */
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DATE = "Date";
    public static final String HOST = "Host";

    /*
     * Response status codes
     */
    public static final Map<Integer, String> HTTP_STATUS = new HashMap<Integer, String>() {
        {
            // Informational
            put(100, "Continue");
            put(101, "Switching Protocols");

            // Successful
            put(200, "OK");
            put(201, "Created");
            put(202, "Accepted");
            put(203, "Non-Authoritative Information");
            put(204, "No Content");
            put(205, "Reset Content");
            put(206, "Partial Content");

            // Redirection
            put(300, "Multiple Choices");
            put(301, "Moved Permanently");
            put(302, "Found");
            put(303, "See Other");
            put(304, "Not Modified");
            put(305, "Use Proxy");
            put(307, "Temporary Redirect");

            // Client Error
            put(400, "Bad Request");
            put(401, "Unauthorized");
            put(402, "Payment Required");
            put(403, "Forbidden");
            put(404, "Not Found");
            put(405, "Method Not Allowed");
            put(406, "Not Acceptable");
            put(407, "Proxy Authentication Required");
            put(408, "Request Time-out");
            put(409, "Conflict");
            put(410, "Gone");
            put(411, "Length Required");
            put(412, "Precondition Failed");
            put(413, "Request Entity Too Large");
            put(414, "Request-URI Too Large");
            put(415, "Unsupported Media Type");
            put(416, "Requested range not satisfiable");
            put(417, "Expectation Failed");

            // Server Error
            put(500, "Internal Server Error");
            put(501, "Not Implemented");
            put(502, "Bad Gateway");
            put(503, "Service Unavailable");
            put(504, "Gateway Time-out");
            put(505, "HTTP Version not supported");
        }
    };
}
