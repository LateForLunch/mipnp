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

import com.googlecode.mipnp.extension.ExtensionHolder;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import com.googlecode.mipnp.model.ExtensionsModel;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExtensionsController {

    private ExtensionsModel model;

    public ExtensionsController() {
        this.model = new ExtensionsModel();
    }

    public int getNumberOfExtensions() {
        return model.getNumberOfExtensions();
    }

    public List<ExtensionHolder<?>> getExtensions() {
        return model.getExtensions();
    }

    public ExtensionHolder<?> getExtension(int index) {
        return model.getExtension(index);
    }

    public List<ExtensionHolder<MediaSource>> getMediaSourceExtensions() {
        return model.getMediaSourceExtensions();
    }
}
