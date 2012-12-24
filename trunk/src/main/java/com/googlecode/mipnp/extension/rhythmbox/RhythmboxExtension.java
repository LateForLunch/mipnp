/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010-2012  Jochem Van denbussche
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
 * RhythmboxExtension.java
 * Created on Dec 24, 2012, 1:58:11 PM
 */
package com.googlecode.mipnp.extension.rhythmbox;

import com.googlecode.mipnp.extension.Extension;
import com.googlecode.mipnp.extension.ExtensionMethod;
import com.googlecode.mipnp.extension.ExtensionMethodType;
import com.googlecode.mipnp.mediaserver.library.MediaContainer;
import com.googlecode.mipnp.mediaserver.library.MediaSource;
import com.googlecode.mipnp.mediaserver.library.MusicAlbum;
import com.googlecode.mipnp.mediaserver.library.MusicTrack;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * This extension can retrieve the music information from Rhythmbox.<br/>
 * This extension has been written for Rhythmbox version 2.96 with database version 1.8.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@Extension(
        name="Rhythmbox",
        description="Import your Rhythmbox library")
public class RhythmboxExtension implements MediaSource {

    private static final File DB_FILE =
            new File(System.getProperty("user.home") +
            "/Downloads/rhythmdb.xml"); // TODO: reference to the real DB

    private File db;
    SAXBuilder builder;

    public RhythmboxExtension() {
    }

    @ExtensionMethod(ExtensionMethodType.LOAD)
    public void loadExtension() throws ClassNotFoundException {
        this.db = DB_FILE;
        this.builder = new SAXBuilder();
    }

    public MediaContainer getMediaContainer() {
        MediaContainer root = new MediaContainer("Rhythmbox");
        if (!db.exists()) {
            return root;
        }

        for (MusicTrack m : getMusicTracks()) {
            root.addMusic(m);
        }

        return root;
    }

    private List<MusicTrack> getMusicTracks() {
        List<MusicTrack> tracks = new ArrayList<MusicTrack>();
        Map<String, MusicAlbum> albums = new HashMap<String, MusicAlbum>();

        try {
            Document document = builder.build(db);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren("entry");

            for (Element entry : list) {
                if (entry.getAttribute("type").getValue().equals("song")) {
                    try {
                        MusicTrack track = new MusicTrack();
                        track.setTitle(entry.getChildText("title"));
                        track.setArtist(entry.getChildText("artist"));
                        track.setGenre(entry.getChildText("genre"));
                        track.setDuration(Integer.parseInt(entry.getChildText("duration")) * 1000);
                        track.setTrackNumber(Integer.parseInt(entry.getChildText("track-number")));
                        track.setBitRate(Integer.parseInt(entry.getChildText("bitrate")));
                        track.setFile(new File(new URI(entry.getChildText("location"))));

                        String strAlbum = entry.getChildText("album");
                        MusicAlbum album = albums.get(strAlbum);
                        if (album == null) {
                            album = new MusicAlbum(strAlbum, entry.getChildText("album-artist"));
                            albums.put(strAlbum, album);
                        }
                        track.setAlbum(album);

                        tracks.add(track);
                    } catch (NumberFormatException ex) {
                        System.err.println("ERROR: " + ex.getMessage());
                    } catch (URISyntaxException ex) {
                        System.err.println("ERROR: " + ex.getMessage());
                    }
                }
            }
        } catch (JDOMException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return tracks;
    }
}
