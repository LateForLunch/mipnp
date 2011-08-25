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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(res.getMimeType());
        InputStream in = res.getInputStream();
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }

        in.close();
        out.close();
    }
}
