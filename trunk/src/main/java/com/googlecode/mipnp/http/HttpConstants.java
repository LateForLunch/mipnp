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
package com.googlecode.mipnp.http;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * This interface provides constant useful for HTTP.<br/>
 * <br />
 * See RFC 2616: http://tools.ietf.org/html/rfc2616.html
 * <br />
 * <br />
 * Created on Oct 24, 2010, 10:54:07 AM
 * 
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 * @author Tijl Van Assche <tijlvanassche@gmail.com>
 */
public interface HttpConstants {

    public static final int HTTP_DEFAULT_PORT = 80;
    public static final String HTTP_DEFAULT_CHARSET_NAME = "US-ASCII";
    public static final Charset HTTP_DEFAULT_CHARSET = Charset.forName(HTTP_DEFAULT_CHARSET_NAME);
    public static final String HTTP_DATE_PATTERN = "EEE, dd MMM yyyyy HH:mm:ss z";

    public static final byte CRb = 10;
    public static final byte LFb = 13;
    public static final byte[] CRLFb = {CRb, LFb};
    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char HT = '\t';
    public static final String CRLF = "" + CR + LF;

    /*
     * Version
     */
    public static final String HTTP_VERSION_1_0 = "HTTP/1.0";
    public static final String HTTP_VERSION_1_1 = "HTTP/1.1";
    public static final String HTTP_VERSION = HTTP_VERSION_1_1;

    /*
     * Methods
     */
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";

    /*
     * Header field names
     */
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_HOST = "Host";

    /*
     * Response status codes
     */
    // Informational
    public static final int STATUS_CONTINUE = 100;
    public static final int STATUS_SWITCHING_PROTOCOLS = 101;

    // Successful
    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_ACCEPTED = 202;
    public static final int STATUS_NON_AUTHORITATIVE_INFORMATION = 203;
    public static final int STATUS_NO_CONTENT = 204;
    public static final int STATUS_RESET_CONTENT = 205;
    public static final int STATUS_PARTIAL_CONTENT = 206;

    // Redirection
    public static final int STATUS_MULTIPLE_CHOICES = 300;
    public static final int STATUS_MOVED_PERMANENTLY = 301;
    public static final int STATUS_FOUND = 302;
    public static final int STATUS_SEE_OTHER = 303;
    public static final int STATUS_NOT_MODIFIED = 304;
    public static final int STATUS_USE_PROXY = 305;
    public static final int STATUS_TEMPORARY_REDIRECT = 307;

    // Client Error
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_PAYMENT_REQUIRED = 402;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_METHOD_NOT_ALLOWED = 405;
    public static final int STATUS_NOT_ACCEPTABLE = 406;
    public static final int STATUS_PROXY_AUTHENTICATION_REQUIRED = 407;
    public static final int STATUS_REQUEST_TIME_OUT = 408;
    public static final int STATUS_CONFLICT = 409;
    public static final int STATUS_GONE = 410;
    public static final int STATUS_LENGTH_REQUIRED = 411;
    public static final int STATUS_PRECONDITION_FAILED = 412;
    public static final int STATUS_REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int STATUS_REQUEST_URI_TOO_LARGE = 414;
    public static final int STATUS_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int STATUS_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int STATUS_EXPECTATION_FAILED = 417;

    // Server Error
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;
    public static final int STATUS_NOT_IMPLEMENTED = 501;
    public static final int STATUS_BAD_GATEWAY = 502;
    public static final int STATUS_SERVICE_UNAVAILABLE = 503;
    public static final int STATUS_GATEWAY_TIME_OUT = 504;
    public static final int STATUS_HTTP_VERSION_NOT_SUPPORTED = 505;

    /*
     * Response status codes Map
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
