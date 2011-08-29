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
 * CdsConstants.java
 * Created on Aug 11, 2011, 3:45:45 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface CdsConstants {

    /*
     * Containers
     */
    public static final String UPNP_CLASS_CONTAINER = "object.container";
    public static final String UPNP_CLASS_STORAGE_FOLDER = UPNP_CLASS_CONTAINER + ".storageFolder";
    public static final String UPNP_CLASS_ALBUM = UPNP_CLASS_CONTAINER + ".album";
    public static final String UPNP_CLASS_MUSIC_ALBUM = UPNP_CLASS_ALBUM + ".musicAlbum";
    public static final String UPNP_CLASS_GENRE = UPNP_CLASS_CONTAINER + ".genre";
    public static final String UPNP_CLASS_MUSIC_GENRE = UPNP_CLASS_GENRE + ".musicGenre";
    public static final String UPNP_CLASS_PERSON = UPNP_CLASS_CONTAINER + ".person";
    public static final String UPNP_CLASS_MUSIC_ARTIST = UPNP_CLASS_PERSON + ".musicArtist";

    /*
     * Items
     */
    public static final String UPNP_CLASS_ITEM = "object.item";
    public static final String UPNP_CLASS_IMAGE_ITEM = UPNP_CLASS_ITEM + ".imageItem";
    public static final String UPNP_CLASS_PHOTO = UPNP_CLASS_IMAGE_ITEM + ".photo";
    public static final String UPNP_CLASS_AUDIO_ITEM = UPNP_CLASS_ITEM + ".audioItem";
    public static final String UPNP_CLASS_MUSIC_TRACK = UPNP_CLASS_AUDIO_ITEM + ".musicTrack";
    public static final String UPNP_CLASS_VIDEO_ITEM = UPNP_CLASS_ITEM + ".videoItem";
    public static final String UPNP_CLASS_MOVIE = UPNP_CLASS_VIDEO_ITEM + ".movie";

    /*
     * Base properties
     */
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_FOLDER = "folder";
    public static final String PROPERTY_TITLE = "dc:title";
    public static final String PROPERTY_CREATOR = "dc:creator";
    public static final String PROPERTY_RES = "res";
    public static final String PROPERTY_CLASS = "upnp:class";
    public static final String PROPERTY_SEARCHABLE = "searchable";
    public static final String PROPERTY_PARENT_ID = "parentID";
    public static final String PROPERTY_RESTRICTED = "restricted";
    public static final String PROPERTY_CHILD_COUNT = "childCount";

    public static final String PROPERTY_ARTIST = "upnp:artist";
    public static final String PROPERTY_GENRE = "upnp:genre";
    public static final String PROPERTY_ALBUM = "upnp:album";
    public static final String PROPERTY_ORIGINAL_TRACK_NUMBER = "upnp:originalTrackNumber";

    /*
     * Resource properties
     */
    public static final String PROPERTY_SIZE = "res@size"; // In bytes
    public static final String PROPERTY_DURATION = "res@duration"; // Format: H+:MM:SS[.F+] or H+:MM:SS[.F0/F1]
    public static final String PROPERTY_BITRATE = "res@bitrate"; // In bytes/second
    public static final String PROPERTY_SAMPLE_FREQUENCY = "res@sampleFrequency"; // In Hz

    public static final MimetypesFileTypeMap MIMETYPES = new MimetypesFileTypeMap() {
        {
            /*
             * Audio
             */
            addMimeTypes("audio/x-ms-wma wma WMA");
            addMimeTypes("audio/mpeg mp3 MP3");
            addMimeTypes("audio/wav wav WAV");
            addMimeTypes("audio/mp4 m4a M4A");
            /*
             * Video
             */
            addMimeTypes("video/x-ms-wmv wmv WMV");
            addMimeTypes("video/mpeg mpg MPG mpeg MPEG");
            addMimeTypes("video/avi avi AVI");
            addMimeTypes("video/mp4 mp4 MP4 m4v M4V mp4v MP4V");
            addMimeTypes("video/quicktime mov MOV");
            /*
             * Image
             */
            addMimeTypes("image/jpeg jpg JPG jpeg JPEG");
            addMimeTypes("image/png png PNG");
        }
    };
}
