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
 * StorageFolder.java
 * Created on Jul 28, 2011, 1:32:40 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.io.File;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class StorageFolder extends CdsObject {

    private static final String UPNP_CLASS = "object.container.storageFolder";

    public StorageFolder(File directory) {
        super(UPNP_CLASS);
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalArgumentException(
                    "Can't create a StorageFolder without a directory.");
        }
        setTitle(directory.getName());
        File[] list = directory.listFiles();
        for (int i = 0; i < list.length; i++) {
            addChild(CdsObjectFactory.createObject(list[i]));
        }
    }

    public StorageFolder(String title) {
        super(UPNP_CLASS);
        setTitle(title);
    }
}
