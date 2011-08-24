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
public class CdsObjectFactory implements CdsConstants {

    private static final MimetypesFileTypeMap MIMETYPES = new MimetypesFileTypeMap();

    static {
        MIMETYPES.addMimeTypes("audio/x-ms-wma wma WMA");
        MIMETYPES.addMimeTypes("audio/mpeg mp3 MP3");
        MIMETYPES.addMimeTypes("audio/wav wav WAV");
        MIMETYPES.addMimeTypes("image/jpeg jpg JPG jpeg JPEG");
        MIMETYPES.addMimeTypes("image/png png PNG");
        MIMETYPES.addMimeTypes("video/x-ms-wmv wmv WMV");
        MIMETYPES.addMimeTypes("video/mpeg mpg MPG mpeg MPEG");
        MIMETYPES.addMimeTypes("video/avi avi AVI");
    }

    public static CdsObject createObject(File file) {
        if (file == null) {
            return null;
        }
        if (file.isDirectory()) {
            CdsObject obj = createStorageFolder();
            obj.setTitle(file.getName());
            obj.setProperty(PROPERTY_FOLDER, file.getParentFile().getName());
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                obj.addChild(createObject(files[i]));
            }
            return obj;
        } else {
            String mimeType = MIMETYPES.getContentType(file);
            if (mimeType.startsWith("audio")) {
                return createMusicTrack(file, mimeType);
            } else if (mimeType.startsWith("video")) {
                return createMovie(file, mimeType);
            } else if (mimeType.startsWith("image")) {
                return createPhoto(file, mimeType);
            } else {
                return null;
            }
        }
    }

    public static CdsObject createStorageFolder() {
        return new CdsObject(UPNP_CLASS_STORAGE_FOLDER);
    }

    public static CdsObject createStorageFolder(String id) {
        return new CdsObject(UPNP_CLASS_STORAGE_FOLDER, id);
    }

    public static CdsObject createStorageFolder(String id, String title) {
        return new CdsObject(UPNP_CLASS_STORAGE_FOLDER, id, title);
    }

    public static CdsObject createMusicTrack(File file, String mimeType) {
        CdsObject obj = new CdsObject(UPNP_CLASS_MUSIC_TRACK);
        String title = file.getName();
        title = title.substring(0, title.lastIndexOf('.'));
        obj.setTitle(title);
        obj.setProperty(PROPERTY_FOLDER, file.getParentFile().getName());
        FileResource res = new FileResource(file, mimeType);
        obj.setResource(res);
        return obj;
    }

    public static CdsObject createPhoto(File file, String mimeType) {
        CdsObject obj = new CdsObject(UPNP_CLASS_PHOTO);
        String title = file.getName();
        title = title.substring(0, title.lastIndexOf('.'));
        obj.setTitle(title);
        obj.setProperty(PROPERTY_FOLDER, file.getParentFile().getName());
        FileResource res = new FileResource(file, mimeType);
        obj.setResource(res);
        return obj;
    }

    public static CdsObject createMovie(File file, String mimeType) {
        CdsObject obj = new CdsObject(UPNP_CLASS_MOVIE);
        String title = file.getName();
        title = title.substring(0, title.lastIndexOf('.'));
        obj.setTitle(title);
        obj.setProperty(PROPERTY_FOLDER, file.getParentFile().getName());
        FileResource res = new FileResource(file, mimeType);
        obj.setResource(res);
        return obj;
    }
}
