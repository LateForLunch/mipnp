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
package com.googlecode.mipnp.mediaserver.library;

import com.googlecode.mipnp.mediaserver.cds.CdsObject;
import com.googlecode.mipnp.mediaserver.cds.SearchCriteria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaLibrary {

    public static final String ID_ROOT = "0";
    public static final String XBOX_ID_ROOT = "100000";
    
    public static final String ID_MUSIC = "1";
    public static final String ID_MUSIC_ALL = "4";
    public static final String ID_MUSIC_GENRE = "5";
    public static final String ID_MUSIC_ARTIST = "6";
    public static final String ID_MUSIC_ALBUM = "7";
//    public static final String ID_MUSIC_PLAYLISTS = "F";
//    public static final String ID_MUSIC_FOLDERS = "14";

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
    private CdsObject xbox_root;
    private Directory rootDir;

    private CdsObject music;
    private CdsObject musicAll;
    private CdsObject musicGenre;
    private CdsObject musicArtist;
    private CdsObject musicAlbum;

    private CdsObject video;
    private CdsObject videoAll;
    private CdsObject videoFolders;

    private CdsObject pictures;
    private CdsObject picturesAll;
    private CdsObject picturesFolders;

    public MediaLibrary() {
        init();
    }

    public void addMedia(MediaSource source) {
        addMusic(source);
        addVideos(source);
        addPictures(source);
        rootDir = source.getRootDirectory();
        createVideoTree(videoFolders, rootDir);
    }

    public CdsObject getObjectById(String id) {
        if (id.equals(XBOX_ID_ROOT)) {
            return xbox_root;
        }
        if (id.equals(ID_ROOT)) {
            return root;
        }
        for (CdsObject obj : root) {
            if (obj.getId().equals(id)) {
                return obj;
            }
        }
        return null;
    }

    public List<CdsObject> search(CdsObject start, SearchCriteria sc) {
        List<CdsObject> result = new ArrayList<CdsObject>();
        for (CdsObject obj : start) {
            if (sc.meetsCriteria(obj)) {
                result.add(obj);
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
        String parent = obj.getParentId();
        str += "(ID: " + obj.getId() + ", parentID: " + parent + ") " + obj.getTitle() + "\n";
//        str += obj.getTitle() + "\n";
        if (obj.isContainer()) {
            for (CdsObject child : obj.getChildren()) {
                str += toString(child, level + 1);
            }
        }
        return str;
    }

    private void init() {
        /*
         * Root
         */
        this.root = CdsObjectFactory.createStorageFolder(ID_ROOT, "Root");
        this.xbox_root = CdsObjectFactory.createStorageFolder(XBOX_ID_ROOT, "Root");
        
        /*
         * Music
         */
        this.music = CdsObjectFactory.createStorageFolder(
                ID_MUSIC, "Music");
        this.musicAll = CdsObjectFactory.createStorageFolder(
                ID_MUSIC_ALL, "All Music");
        this.musicGenre = CdsObjectFactory.createStorageFolder(
                ID_MUSIC_GENRE, "Genre");
        this.musicArtist = CdsObjectFactory.createStorageFolder(
                ID_MUSIC_ARTIST, "Artist");
        this.musicAlbum = CdsObjectFactory.createStorageFolder(
                ID_MUSIC_ALBUM, "Album");
        music.addChild(musicAll);
        music.addChild(musicGenre);
        music.addChild(musicArtist);
        music.addChild(musicAlbum);

        /*
         * Video
         */
        this.video = CdsObjectFactory.createStorageFolder(
                ID_VIDEO, "Video");
        this.videoAll = CdsObjectFactory.createStorageFolder(
                ID_VIDEO_ALL, "All Video");
//        // TODO: temp fix
//        CdsObject videoFoldersTemp = CdsObjectFactory.createStorageFolder(
//                ID_VIDEO_FOLDERS, "Folders");
//        this.videoFolders = CdsObjectFactory.createStorageFolder();
//        videoFolders.setTitle("All Videos");
//        videoFoldersTemp.addChild(videoFolders);
//        video.addChild(videoFoldersTemp);
//        // End temp fix
        // TODO: temp fix
        this.videoFolders = CdsObjectFactory.createStorageFolder(
                ID_VIDEO_FOLDERS, "Folders");
//        this.videoFolders = CdsObjectFactory.createStorageFolder();
//        videoFolders.setTitle("All Videos");
//        videoFoldersTemp.addChild(videoFolders);
        video.addChild(videoFolders);
        // End temp fix
        video.addChild(videoAll);

        /*
         * Pictures
         */
        this.pictures = CdsObjectFactory.createStorageFolder(
                ID_PICTURES, "Pictures");
        this.picturesAll = CdsObjectFactory.createStorageFolder(
                ID_PICTURES_ALL, "All Pictures");
        // TODO: temp fix
        CdsObject picturesFoldersTemp = CdsObjectFactory.createStorageFolder(
                ID_PICTURES_FOLDERS, "Folders");
        this.picturesFolders = CdsObjectFactory.createStorageFolder();
        picturesFolders.setTitle("All Pictures");
        picturesFoldersTemp.addChild(picturesFolders);
        pictures.addChild(picturesFoldersTemp);
        // End temp fix
        pictures.addChild(picturesAll);

        root.addChild(music);
        root.addChild(video);
        root.addChild(pictures);
        xbox_root.addChild(music);
        xbox_root.addChild(video);
        xbox_root.addChild(pictures);
    }

    private void addMusic(MediaSource source) {
        List<MusicTrack> musicTracks = source.getMusicTracks();
        if (musicTracks == null) {
            return;
        }
        for (MusicTrack track : musicTracks) {
            CdsObject item = CdsObjectFactory.createMusicItem(track);
            musicAll.addChild(item);

            CdsObject genre = getMusicGenre(track.getGenre());
            if (genre != null) {
                genre.addChild(item, false);
            }

            CdsObject artist = getMusicArtist(track.getArtist());
            if (artist != null) {
                artist.addChild(item, false);
            }

            CdsObject album = getMusicAlbum(track.getAlbum());
            if (album != null) {
                album.addChild(item, false);
            }
        }
    }

    private void addVideos(MediaSource source) {
        List<Video> videos = source.getVideos();
        if (videos == null) {
            return;
        }
        for (Video v : videos) {
            CdsObject item = CdsObjectFactory.createVideoItem(v);
            videoAll.addChild(item);
            //videoFolders.addChild(item, false); // TODO: fix folders
        }
    }

    private void addPictures(MediaSource source) {
        List<Picture> pics = source.getPictures();
        if (pics == null) {
            return;
        }
        for (Picture p : pics) {
            picturesAll.addChild(p);
            picturesFolders.addChild(p); // TODO: fix folders
        }
    }

    private CdsObject getMusicGenre(String genre) {
        if (genre == null || genre.equals("")) {
            return null;
        }
        for (CdsObject obj : musicGenre.getChildren()) {
            if (obj.getTitle().equals(genre)) {
                return obj;
            }
        }
        CdsObject obj = CdsObjectFactory.createMusicGenre(genre);
        musicGenre.addChild(obj);
        return obj;
    }

    private CdsObject getMusicArtist(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        for (CdsObject obj : musicArtist.getChildren()) {
            if (obj.getTitle().equals(name)) {
                return obj;
            }
        }
        CdsObject obj = CdsObjectFactory.createMusicArtist(name);
        musicArtist.addChild(obj);
        return obj;
    }

    private CdsObject getMusicAlbum(MusicAlbum album) {
        if (album == null) {
            return null;
        }
        String title = album.getTitle();
        if (title == null || title.equals("")) {
            return null;
        }
        for (CdsObject obj : musicAlbum.getChildren()) {
            if (obj.getTitle().equals(title)) {
                return obj;
            }
        }
        CdsObject obj = CdsObjectFactory.createMusicAlbum(album);
        musicAlbum.addChild(obj);
        return obj;
    }

    private boolean createVideoTree(CdsObject cdsDir, Directory dir) {
        List<Video> vidList = dir.getVideos();
        boolean hasAddedVid = false;
        
        if(!vidList.isEmpty()){
            hasAddedVid = true;
        }
        for(Video v : vidList){
            CdsObject item = CdsObjectFactory.createVideoItem(v);
            cdsDir.addChild(item, false);
        }
        
        for(Directory d : dir.getChildDirectories()){
            CdsObject cds = CdsObjectFactory.createStorageFolder();
            cds.setTitle(d.getTitle());
            
            if(createVideoTree(cds, d)){
                hasAddedVid = true;
                cdsDir.addChild(cds);
            }
        }
        return hasAddedVid;
        
    }
}
