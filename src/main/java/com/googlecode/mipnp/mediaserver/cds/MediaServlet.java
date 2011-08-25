/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * MediaServlet.java
 * Created on Jul 28, 2011, 1:18:52 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaServlet extends HttpServlet {

    private MediaLibrary library;

    public MediaServlet(MediaLibrary library) {
        this.library = library;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);
        if (id == null || id.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CdsObject obj = library.getObjectById(id);
        if (obj == null || obj.isContainer()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Resource res = obj.getResource();
        if (res == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        String range = request.getHeader("Range");
        long start = 0;
        long end = res.getContentLength() - 1;
        if (range != null) {
            range = range.substring(6);
            String strStart = range.substring(0, range.indexOf('-'));
            String strEnd = range.substring(range.indexOf('-') + 1);
            start = (strStart.length() > 0) ? Long.parseLong(strStart) : -1;
            end = (strEnd.length() > 0) ? Long.parseLong(strEnd) : -1;

            if (start < 0) {
                start = res.getContentLength() - end;
                end = res.getContentLength() - 1;
            } else if (end < 0 || end > res.getContentLength() - 1) {
                end = res.getContentLength() - 1;
            }
        }

        if (start == 0 && end == res.getContentLength() - 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setHeader("Content-Range",
                    "bytes " + start + "-" + end + "/" + res.getContentLength());
        }
        response.setContentType(res.getMimeType());
        response.setHeader("Content-Length", String.valueOf(res.getContentLength()));
        response.setDateHeader("Date", System.currentTimeMillis());
        response.setDateHeader("Last-Modified", res.getLastModified());

//        InputStream in = res.getInputStream();
        RandomAccessFile in = res.getDataInput();
        OutputStream out = response.getOutputStream();

//        for (int i = 0; i < start; i++) {
//            in.read();
//        }
        if (start > 0) {
            in.seek(start);
        }

        byte[] buf = new byte[1024];
        int count = 0;
        long todo = (end - start) + 1;
        while ((count = in.read(buf)) > 0) {
            todo -= count;
            if (todo > 0) {
                out.write(buf, 0, count);
            } else {
                out.write(buf, 0, (int) todo + count);
                break;
            }
        }

        in.close();
        out.close();
    }
}
