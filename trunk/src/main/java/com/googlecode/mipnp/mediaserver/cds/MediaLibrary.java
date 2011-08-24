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
 * MediaLibrary.java
 * Created on Jul 27, 2011, 1:42:13 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaLibrary {

    public static final String ID_ROOT = "0";

    public static final String ID_MUSIC = "1";
    public static final String ID_MUSIC_ALL = "4";
//    public static final String ID_MUSIC_GENRE = "5";
//    public static final String ID_MUSIC_ARTIST = "6";
//    public static final String ID_MUSIC_ALBUM = "7";
//    public static final String ID_MUSIC_PLAYLISTS = "F";
    public static final String ID_MUSIC_FOLDERS = "14";

    public static final String ID_VIDEO = "2";
    public static final String ID_VIDEO_ALL = "8";
//    public static final String ID_VIDEO_GENRE = "9";
//    public static final String ID_VIDEO_ACTOR = "A";
//    public static final String ID_VIDEO_SERIES = "E";
//    public static final String ID_VIDEO_PLAYLISTS = "10";
    public static final String ID_VIDEO_FOLDERS = "15";

    public static final String ID_PICTURES = "3";
    public static final String ID_PICTURES_ALL = "B";
//    public static final String ID_PICTURES_DATE_TAKEN = "C";
//    public static final String ID_PICTURES_ALBUMS = "D";
//    public static final String ID_PICTURES_KEYWORD = "D2";
//    public static final String ID_PICTURES_PLAYLISTS = "11";
    public static final String ID_PICTURES_FOLDERS = "16";

    private CdsObject root;
    private CdsObject music;
    private CdsObject video;
    private CdsObject pictures;

    public MediaLibrary() {
        init();
    }

    public void addMedia(File mediaFile) {
        CdsObject obj = CdsObjectFactory.createObject(mediaFile);

        if (obj == null) {
            return; // TODO: throw exception
        }

        if (obj.isContainer()) {
            for (CdsObject item : obj) {
                addItem(item);
            }
        } else {
            addItem(obj);
        }
    }

    public CdsObject getObjectById(String id) {
        return root.getObjectById(id);
    }

    public List<CdsObject> search(SearchCriteria sc) {
        return search(root, sc);
    }

    public List<CdsObject> search(CdsObject obj, SearchCriteria sc) {
        List<CdsObject> result = new ArrayList<CdsObject>();
        if (sc.meetsCriteria(obj)) {
            result.add(obj);
        }
        if (obj.isContainer()) {
            for (CdsObject child : obj.getChildren()) {
                result.addAll(search(child, sc));
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return toString(root, 0);
    }

    private String toString(CdsObject obj, int level) {
        String str = "";
        for (int i = 0; i < level; i++) {
            str += " ";
        }
        str += obj.getTitle() + "\n";
        if (obj.isContainer()) {
            for (CdsObject child : obj.getChildren()) {
                str += toString(child, level + 1);
            }
        }
        return str;
    }

    private void init() {
        this.root = CdsObjectFactory.createStorageFolder(ID_ROOT, "Root");

        this.music = CdsObjectFactory.createStorageFolder(ID_MUSIC, "Music");
        CdsObject musicFolders = new GroupStorageFolder(
                ID_MUSIC_FOLDERS, "Folders", CdsConstants.PROPERTY_FOLDER);
        music.addChild(musicFolders);

        this.video = CdsObjectFactory.createStorageFolder(ID_VIDEO, "Video");
        CdsObject videoFolders = new GroupStorageFolder(
                ID_VIDEO_FOLDERS, "Folders", CdsConstants.PROPERTY_FOLDER);
        video.addChild(videoFolders);

        this.pictures = CdsObjectFactory.createStorageFolder(ID_PICTURES, "Pictures");
        CdsObject pictureFolders = new GroupStorageFolder(
                ID_PICTURES_FOLDERS, "Folders", CdsConstants.PROPERTY_FOLDER);
        pictures.addChild(pictureFolders);

        root.addChild(music);
        root.addChild(video);
        root.addChild(pictures);
    }

    private void addItem(CdsObject item) {
        if (item.getUpnpClass().startsWith(CdsConstants.UPNP_CLASS_AUDIO_ITEM)) {
            for (CdsObject group : music.getChildren()) {
                group.addChild(item);
            }
        } else if (item.getUpnpClass().startsWith(CdsConstants.UPNP_CLASS_VIDEO_ITEM)) {
            for (CdsObject group : video.getChildren()) {
                group.addChild(item);
            }
        } else if (item.getUpnpClass().startsWith(CdsConstants.UPNP_CLASS_IMAGE_ITEM)) {
            for (CdsObject group : pictures.getChildren()) {
                group.addChild(item);
            }
        }
    }
}
