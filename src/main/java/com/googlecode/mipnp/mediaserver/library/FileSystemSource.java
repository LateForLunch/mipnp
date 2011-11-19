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
 * FileSystemSource.java
 * Created on Sep 4, 2011, 12:02:56 PM
 */
package com.googlecode.mipnp.mediaserver.library;

import com.googlecode.mipnp.mediaserver.cds.CdsConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class FileSystemSource implements MediaSource {

    private List<MusicTrack> allMusicTracks;
    private List<Video> allVideos;
    private List<Picture> allPictures;
    private Directory rootDirectory;

    public FileSystemSource(File directory) {
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalArgumentException(
                    "given directory is null or not a directory at all.");
        }
        this.allMusicTracks = new ArrayList<MusicTrack>();
        this.allVideos = new ArrayList<Video>();
        this.allPictures = new ArrayList<Picture>();
        this.rootDirectory = addMedia(directory);
    }

    public List<MusicTrack> getMusicTracks() {
        return allMusicTracks;
    }

    public List<Video> getVideos() {
        return allVideos;
    }

    public List<Picture> getPictures() {
        return allPictures;
    }
    
    public Directory getRootDirectory(){
        return rootDirectory;
    }

    private Directory addMedia(File directory) {
        
        Directory currentDir = new Directory(directory.getName(), directory);
        
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                currentDir.addChildDirectory(addMedia(files[i])); ;
            } else {
                String type = CdsConstants.MIMETYPES.getContentType(files[i]);
                String title = files[i].getName();
                int lastPoint = title.lastIndexOf('.');
                if (lastPoint > 0) {
                    title = title.substring(0, lastPoint);
                }
                if (type.startsWith("audio")) {
                    allMusicTracks.add(new MusicTrack(title, files[i]));
                    currentDir.addMusic(new MusicTrack(title, files[i]));
                } else if (type.startsWith("video")) {
                    allVideos.add(new Video(title, files[i]));
                    currentDir.addVideo(new Video(title, files[i]));
                } else if (type.startsWith("image")) {
                    allPictures.add(new Picture(title, files[i]));
                    currentDir.addPicture(new Picture(title, files[i]));
                }
            }
        }
        return currentDir;
    }
}
