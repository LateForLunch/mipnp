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
package com.googlecode.mipnp.mediaserver.library;

import com.googlecode.mipnp.mediaserver.cds.CdsConstants;
import com.googlecode.mipnp.mediaserver.cds.CdsObject;
import com.googlecode.mipnp.mediaserver.cds.FileResource;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CdsObjectFactory implements CdsConstants {

//    public static List<CdsObject> createItems(File directory) {
//        if (directory == null || directory.isFile()) {
//            throw new IllegalArgumentException(
//                    "directory may not be null or a file.");
//        }
//        List<CdsObject> items = new ArrayList<CdsObject>();
//        File[] files = directory.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            if (files[i].isDirectory()) {
//                items.addAll(createItems(files[i]));
//            } else {
//                items.add(createItem(files[i]));
//            }
//        }
//        return items;
//    }

//    public static CdsObject createItem(File file) {
//        if (file == null || file.isDirectory()) {
//            throw new IllegalArgumentException(
//                    "file may not be null or a directory.");
//        }
//        String mimeType = MIMETYPES.getContentType(file);
//        if (mimeType.startsWith("audio")) {
//            return createMusicTrack(file, mimeType);
//        } else if (mimeType.startsWith("video")) {
//            return createVideoItem(file, mimeType);
//        } else if (mimeType.startsWith("image")) {
//            return createPhoto(file, mimeType);
//        } else {
//            return null;
//        }
//    }

    public static CdsObject createStorageFolder() {
        return new CdsObject(UPNP_CLASS_STORAGE_FOLDER);
    }

    public static CdsObject createStorageFolder(String id) {
        return new CdsObject(UPNP_CLASS_STORAGE_FOLDER, id);
    }

    public static CdsObject createStorageFolder(String id, String title) {
        return new CdsObject(UPNP_CLASS_STORAGE_FOLDER, id, title);
    }

    public static CdsObject createMusicItem(MusicTrack track) {
        CdsObject item = new CdsObject(UPNP_CLASS_MUSIC_TRACK);
        item.setTitle(track.getTitle());
        item.setProperty(PROPERTY_ARTIST, track.getArtist());
        MusicAlbum album = track.getAlbum();
        if (album != null) {
            item.setProperty(PROPERTY_ALBUM, album.getTitle());
        }
        item.setProperty(PROPERTY_GENRE, track.getGenre());
        item.setProperty(PROPERTY_DURATION, track.getDuration());
        if (track.getTrackNumber() > 0) {
            item.setProperty(
                    PROPERTY_ORIGINAL_TRACK_NUMBER,
                    String.valueOf(track.getTrackNumber()));
        }
        if (track.getBitRate() > 0) {
            item.setProperty(
                    PROPERTY_BITRATE,
                    String.valueOf(track.getBitRate()));
        }
        item.setResource(new FileResource(track.getFile()));
        return item;
    }

    public static CdsObject createMusicGenre(String name) {
        CdsObject obj = new CdsObject(UPNP_CLASS_MUSIC_GENRE);
        obj.setTitle(name);
        return obj;
    }

    public static CdsObject createMusicArtist(String name) {
        CdsObject obj = new CdsObject(UPNP_CLASS_MUSIC_ARTIST);
        obj.setTitle(name);
        return obj;
    }

    public static CdsObject createMusicAlbum(MusicAlbum album) {
        CdsObject obj = new CdsObject(UPNP_CLASS_MUSIC_ALBUM);
        obj.setTitle(album.getTitle());
        obj.setProperty(PROPERTY_ARTIST, album.getArtist());
        return obj;
    }

    public static CdsObject createVideoItem(Video video) {
        CdsObject item = new CdsObject(UPNP_CLASS_VIDEO_ITEM);
        item.setTitle(video.getTitle());
        item.setProperty(PROPERTY_DURATION, video.getDuration());
        item.setResource(new FileResource(video.getFile()));
        return item;
    }
}
