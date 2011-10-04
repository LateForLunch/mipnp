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
 * ServerHeaderFilter.java
 * Created on Jul 12, 2011, 10:46:50 AM
 */
package com.googlecode.mipnp.upnp;

import com.googlecode.mipnp.tools.OsTools;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class ServerHeaderFilter implements Filter {

    private String server;

    public ServerHeaderFilter(RootDevice rootDevice) {
        this.server = OsTools.getOsName() + "/" + OsTools.getOsVersion();
        server += " UPnP/" + rootDevice.getMajorUpnpVersion() + ".";
        server += rootDevice.getMinorUpnpVersion();
        server += " MiPnP/1.0"; // TODO: MiPnP version
    }

    public void init(FilterConfig fc) throws ServletException {
    }

    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) sr1;
        response.setHeader("Server", server);
        fc.doFilter(sr, sr1);
    }

    public void destroy() {
    }
}
