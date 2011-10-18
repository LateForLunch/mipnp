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
 * PluginEngine.java
 * Created on Oct 18, 2011, 2:29:13 PM
 */
package com.googlecode.mipnp.plugins;

import com.googlecode.mipnp.mediaserver.library.MediaSource;
import com.googlecode.mipnp.plugins.banshee.BansheePlugin;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class PluginEngine {

    /*
     * TODO: dynamic plugin lookup/loading
     */

    private static final String[] PLUGINS = {"Banshee Plugin"};

    public PluginEngine() {
    }

    public String[] getPluginNames() {
        return PLUGINS;
    }

    public MediaSource loadPluginByName(String name) throws Exception {
        return new BansheePlugin();
    }
}
