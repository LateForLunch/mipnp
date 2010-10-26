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
package domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on Oct 24, 2010, 10:54:07 AM
 * 
 * See RFC 2616: http://www.ietf.org/rfc/rfc2616.txt
 * 
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 * @author Tijl Van Assche <tijlvanassche@gmail.com>
 */
public class Http {

    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char HT = '\t';
    public static final String CRLF = ""+CR+LF;

    /*
     * Version
     */
    public static final int VERSION_MAJOR = 1;
    public static final int VERSION_MINOR = 1;
    public static final String VERSION = "HTTP/" + VERSION_MAJOR + "." + VERSION_MINOR;

    /*
     * TODO: Request methods:
     * OPTIONS
     * GET
     * HEAD
     * POST
     * PUT
     * DELETE
     * TRACE
     * CONNECT
     */

    /*
     * Response status codes
     */
    public static final Map<Integer, String> STATUS = new HashMap<Integer, String>() {
        {
            put(100, "Continue");
            put(101, "Switching Protocols");

            put(200, "OK");
            put(201, "Created");
            put(202, "Accepted");
            put(203, "Non-Authoritative Information");
            put(204, "No Content");
            put(205, "Reset Content");
            put(206, "Partial Content");

            put(300, "Multiple Choices");
            put(301, "Moved Permanently");
            put(302, "Found");
            put(303, "See Other");
            put(304, "Not Modified");
            put(305, "Use Proxy");
            put(307, "Temporary Redirect");

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

            put(500, "Internal Server Error");
            put(501, "Not Implemented");
            put(502, "Bad Gateway");
            put(503, "Service Unavailable");
            put(504, "Gateway Time-out");
            put(505, "HTTP Version not supported");
        }
    };
}
