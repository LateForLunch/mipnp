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
 * MusicTrack.java
 * Created on Aug 28, 2011, 3:51:18 PM
 */
package com.googlecode.mipnp.mediaserver.library;

import com.googlecode.mipnp.mediaserver.cds.CdsObject;
import com.googlecode.mipnp.mediaserver.cds.FileResource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MusicTrack extends CdsObject {

    private MusicArtist artist;
    private MusicAlbum album;
    private MusicGenre genre;

    public MusicTrack(String title, File musicTrack) {
        super(UPNP_CLASS_MUSIC_TRACK);
        setTitle(title);
        setResource(new FileResource(musicTrack));
    }

    public MusicArtist getArtist() {
        return artist;
    }

    public void setArtist(MusicArtist artist) {
        this.artist = artist;
        setProperty(PROPERTY_ARTIST, artist.getTitle());
    }

    public MusicAlbum getAlbum() {
        return album;
    }

    public void setAlbum(MusicAlbum album) {
        this.album = album;
        setProperty(PROPERTY_ALBUM, album.getTitle());
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
        setProperty(PROPERTY_GENRE, genre.getTitle());
    }

    public String getDuration() {
        return getProperty(PROPERTY_DURATION);
    }

    public void setDuration(int duration) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        setDuration(format.format(new Date(duration)));
    }

    public void setDuration(String duration) {
        setProperty(PROPERTY_DURATION, duration);
    }

    public int getTrackNumber() {
        return Integer.parseInt(getProperty(PROPERTY_ORIGINAL_TRACK_NUMBER));
    }

    public void setTrackNumber(int nr) {
        setProperty(PROPERTY_ORIGINAL_TRACK_NUMBER, String.valueOf(nr));
    }

    public int getBitRate() {
        return Integer.parseInt(getProperty(PROPERTY_BITRATE));
    }

    public void setBitRate(int bitrate) {
        setProperty(PROPERTY_BITRATE, String.valueOf(bitrate));
    }
}
