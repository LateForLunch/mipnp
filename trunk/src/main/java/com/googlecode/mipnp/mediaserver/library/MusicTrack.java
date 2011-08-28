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
import com.googlecode.mipnp.mediaserver.cds.Resource;
import java.io.File;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MusicTrack extends CdsObject {

    private MusicArtist artist;
    private MusicAlbum album;

    public MusicTrack(String title, File musicTrack) {
        super(UPNP_CLASS_MUSIC_TRACK);
        setTitle(title);
        setResource(new Resource(musicTrack));
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
}
