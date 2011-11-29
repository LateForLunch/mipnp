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
 * ExtensionsModel.java
 * Created on Nov 29, 2011, 8:07:57 PM
 */
package com.googlecode.mipnp.model;

import com.googlecode.mipnp.extension.ExtensionHolder;
import com.googlecode.mipnp.extension.ExtensionLoader;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExtensionsModel {

    private List<ExtensionHolder<MediaSource>> mediaSources;

    public ExtensionsModel() {
        this.mediaSources = ExtensionLoader.load(MediaSource.class);
    }

    public List<ExtensionHolder<?>> getExtensions() {
        List<ExtensionHolder<?>> list = new ArrayList<ExtensionHolder<?>>();
        list.addAll(mediaSources);
        return list;
    }

    public int getNumberOfExtensions() {
        return getExtensions().size();
    }

    public ExtensionHolder<?> getExtension(int index) {
        return getExtensions().get(index);
    }

    public List<ExtensionHolder<MediaSource>> getMediaSourceExtensions() {
        return mediaSources;
    }
}
