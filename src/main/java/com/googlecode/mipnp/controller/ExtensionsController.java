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
 * ExtensionsController.java
 * Created on Nov 29, 2011, 8:27:55 PM
 */
package com.googlecode.mipnp.controller;

import com.googlecode.mipnp.extension.ExtensionException;
import com.googlecode.mipnp.extension.ExtensionHolder;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import com.googlecode.mipnp.model.MediaSourceExtensionsModel;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExtensionsController {

    private MediaSourceExtensionsModel mediaSourceExtensionsModel;

    public ExtensionsController() {
        this.mediaSourceExtensionsModel = new MediaSourceExtensionsModel();
    }

    public int getNumberOfMediaSourceExtensions() {
        return mediaSourceExtensionsModel.getNumberOfExtensions();
    }

    public List<ExtensionHolder<MediaSource>> getMediaSourceExtensions() {
        return mediaSourceExtensionsModel.getExtensions();
    }

    public ExtensionHolder<MediaSource> getMediaSourceExtension(int index) {
        return mediaSourceExtensionsModel.getExtension(index);
    }

    public void loadMediaSourceExtension(int index)
            throws ExtensionException {

        ExtensionHolder<MediaSource> extHolder =
                mediaSourceExtensionsModel.getExtension(index);
        extHolder.loadExtension();
    }

    public void unloadMediaSourceExtension(int index)
            throws ExtensionException {

        ExtensionHolder<MediaSource> extHolder =
                mediaSourceExtensionsModel.getExtension(index);
        extHolder.unloadExtension();
    }
}
