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
 * MediaSourceExtensionsModel.java
 * Created on Nov 29, 2011, 8:07:57 PM
 */
package com.googlecode.mipnp.model;

import com.googlecode.mipnp.extension.ExtensionHolder;
import com.googlecode.mipnp.extension.ExtensionLoader;
import com.googlecode.mipnp.extension.banshee.BansheeExtension;
import com.googlecode.mipnp.extension.shotwell.ShotwellExtension;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaSourceExtensionsModel {

    private List<ExtensionHolder<MediaSource>> extensionHolders;

    public MediaSourceExtensionsModel() {
        // TODO: load instead of manual add
//        this.mediaSources = ExtensionLoader.load(MediaSource.class);

        this.extensionHolders = new ArrayList<ExtensionHolder<MediaSource>>();
        extensionHolders.add(new ExtensionHolder<MediaSource>(
                "Banshee",
                "Import your Banshee library",
                new BansheeExtension()));
        extensionHolders.add(new ExtensionHolder<MediaSource>(
                "Shotwell",
                "Import your Shotwell library",
                new ShotwellExtension()));
    }

    public List<ExtensionHolder<MediaSource>> getExtensions() {
        return extensionHolders;
    }

    public int getNumberOfExtensions() {
        return extensionHolders.size();
    }

    public ExtensionHolder<MediaSource> getExtension(int index) {
        return extensionHolders.get(index);
    }
}
