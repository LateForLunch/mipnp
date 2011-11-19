/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Pierre-Luc Plourde, Jochem Van denbussche
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
 * MediaContainer.java
 * Created on Nov 19, 2011, 2:13:01 PM
 */
package com.googlecode.mipnp.mediaserver.library;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pierre-Luc Plourde <pierrelucplourde2@gmail.com>
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MediaContainer {

    private String title;
    private List<MusicTrack> musicTracks;
    private List<Video> videos;
    private List<Picture> pictures;
    private List<MediaContainer> mediaContainers;

    public MediaContainer(String title) {
        this.title = title;
        this.musicTracks = new ArrayList<MusicTrack>();
        this.videos = new ArrayList<Video>();
        this.pictures = new ArrayList<Picture>();
        this.mediaContainers = new ArrayList<MediaContainer>();
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
     * Adds a music track to this media container.
     * @param m the music track to add.
     */
    public void addMusic(MusicTrack m) {
        musicTracks.add(m);
    }

    /**
     * Tests whether this media container contains a music track.
     * @return true if this media container contains a music track, false otherwise.
     */
    public boolean containsMusicTrack() {
        return (!musicTracks.isEmpty());
    }

    /**
     * Tests whether this media container or any child media container
     * contains a music track.
     * @return true if a music track is found, false otherwise.
     */
    public boolean containsMusicTrackDeepSearch() {
        if (containsMusicTrack()) {
            return true;
        }
        for (MediaContainer mc : mediaContainers) {
            if (mc.containsMusicTrackDeepSearch()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return all music tracks in this media container.
     */
    public Iterable<MusicTrack> getMusicTracks() {
        return musicTracks;
    }

    /**
     * Adds a video to this media container.
     * @param v the video to add.
     */
    public void addVideo(Video v) {
        videos.add(v);
    }

    /**
     * Tests whether this media container contains a video.
     * @return true if this media container contains a video, false otherwise.
     */
    public boolean containsVideo() {
        return (!videos.isEmpty());
    }

    /**
     * Tests whether this media container or any child media container
     * contains a video.
     * @return true if a video is found, false otherwise.
     */
    public boolean containsVideoDeepSearch() {
        if (containsVideo()) {
            return true;
        }
        for (MediaContainer mc : mediaContainers) {
            if (mc.containsVideoDeepSearch()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return all videos in this media container.
     */
    public Iterable<Video> getVideos() {
        return videos;
    }

    /**
     * Adds a picture to this media container.
     * @param p the picture to add.
     */
    public void addPicture(Picture p) {
        pictures.add(p);
    }

    /**
     * Tests whether this media container contains a picture.
     * @return true if this media container contains a picture, false otherwise.
     */
    public boolean containsPicture() {
        return (!pictures.isEmpty());
    }

    /**
     * Tests whether this media container or any child media container
     * contains a picture.
     * @return true if a picture is found, false otherwise.
     */
    public boolean containsPictureDeepSearch() {
        if (containsPicture()) {
            return true;
        }
        for (MediaContainer mc : mediaContainers) {
            if (mc.containsPictureDeepSearch()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return all pictures in this media container.
     */
    public Iterable<Picture> getPictures() {
        return pictures;
    }

    /**
     * Adds a media container to this media container.
     * @param mediaContainer the media container to add.
     */
    public void addMediaContainer(MediaContainer mediaContainer) {
        mediaContainers.add(mediaContainer);
    }

    /**
     * Tests whether this media container contains a media container.
     * @return true if this media container contains a media container, false otherwise.
     */
    public boolean containsMediaContainer() {
        return (!mediaContainers.isEmpty());
    }

    /**
     * @return all media containers in this media container.
     */
    public Iterable<MediaContainer> getMediaContainers() {
        return mediaContainers;
    }
}
