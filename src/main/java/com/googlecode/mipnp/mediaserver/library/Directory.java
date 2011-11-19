
package com.googlecode.mipnp.mediaserver.library;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pierre-Luc Plourde <pierrelucplourde2@gmail.com>
 */
public class Directory {
    private String title;
    private File file;
    
    private List<Video> videos;
    private List<Picture> pictures;
    private List<MusicTrack> musics;
    private List<Directory> childDirectories;
    
    public Directory() {
        this(null,null);
    }

    public Directory(String title, File file) {
        this.title = title;
        this.file = file;
        this.videos = new ArrayList<Video>();
        this.pictures = new ArrayList<Picture>();
        this.musics = new ArrayList<MusicTrack>();
        this.childDirectories = new ArrayList<Directory>();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }
    
    public void addVideo(Video v){
        getVideos().add(v);
    }
    
    public void addPicture(Picture p){
        getPictures().add(p);
    }
    
    public void addMusic(MusicTrack m){
        getMusics().add(m);
    }

    /**
     * @return the videos
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * @return the pictures
     */
    public List<Picture> getPictures() {
        return pictures;
    }

    /**
     * @return the musics
     */
    public List<MusicTrack> getMusics() {
        return musics;
    }

    public void addChildDirectory(Directory d){
        childDirectories.add(d);
    }
    
    /**
     * @return the childDirectories
     */
    public List<Directory> getChildDirectories() {
        return childDirectories;
    }
    
    
    
}
