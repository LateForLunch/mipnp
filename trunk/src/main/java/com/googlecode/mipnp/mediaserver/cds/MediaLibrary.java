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
import java.io.FileNotFoundException;
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

    private CdsObjectFactory factory;
    private CdsObject root;
    private CdsObject musicFolders;
    private CdsObject videoFolders;
    private CdsObject pictureFolders;

    public MediaLibrary() {
        this.factory = new CdsObjectFactory();
        init();
    }

    public void addMedia(File mediaFile) throws FileNotFoundException {
        CdsObject obj = null;
        if (mediaFile.isDirectory()) {
            obj = factory.createObject(mediaFile);
        } else {
            File parentFile = mediaFile.getParentFile();
            obj = factory.createStorageFolder();
            obj.setTitle(parentFile.getName());
            obj.addChild(factory.createObject(mediaFile));
        }

        if (obj == null) {
            return; // TODO: exception?
        }

        if (obj.contains(CdsConstants.UPNP_CLASS_AUDIO_ITEM)) {
            musicFolders.addChild(obj);
        }
        if (obj.contains(CdsConstants.UPNP_CLASS_VIDEO_ITEM)) {
            videoFolders.addChild(obj);
        }
        if (obj.contains(CdsConstants.UPNP_CLASS_IMAGE_ITEM)) {
            pictureFolders.addChild(obj);
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

    private void init() {
        this.root = factory.createStorageFolder(ID_ROOT, "Root");

        CdsObject music = factory.createStorageFolder(ID_MUSIC, "Music");
        this.musicFolders = factory.createStorageFolder(ID_MUSIC_FOLDERS, "Folders");
        music.addChild(musicFolders);

        CdsObject video = factory.createStorageFolder(ID_VIDEO, "Video");
        this.videoFolders = factory.createStorageFolder(ID_VIDEO_FOLDERS, "Folders");
        video.addChild(videoFolders);

        CdsObject pictures = factory.createStorageFolder(ID_PICTURES, "Pictures");
        this.pictureFolders = factory.createStorageFolder(ID_PICTURES_FOLDERS, "Folders");
        pictures.addChild(pictureFolders);

        root.addChild(music);
        root.addChild(video);
        root.addChild(pictures);
    }

    @Override
    public String toString() {
        return toString(root, 0);
    }

    public String toString(CdsObject obj, int level) {
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
}
