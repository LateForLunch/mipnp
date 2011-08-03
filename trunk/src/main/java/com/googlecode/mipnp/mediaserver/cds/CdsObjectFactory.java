/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche
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

/*
 * CdsObjectFactory.java
 * Created on Jul 28, 2011, 5:12:15 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CdsObjectFactory {

    private static final MimetypesFileTypeMap MIMETYPES = new MimetypesFileTypeMap();

    static {
        MIMETYPES.addMimeTypes("audio/mpeg mp3 MP3");
    }

    public static CdsObject createObject(File file) {
        if (file == null) {
            return null;
        }
        if (file.isDirectory()) {
            return new StorageFolder(file);
        } else {
            String mimeType = MIMETYPES.getContentType(file);
            if (mimeType.startsWith("audio")) {
                return new MusicTrack(file, mimeType);
            } else {
                return null;
            }
        }
    }
}
