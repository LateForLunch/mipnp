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

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public interface CdsConstants {

    public static final String UPNP_CLASS_CONTAINER = "object.container";
    public static final String UPNP_CLASS_STORAGE_FOLDER = "object.container.storageFolder";

    public static final String UPNP_CLASS_ITEM = "object.item";
    public static final String UPNP_CLASS_IMAGE_ITEM = UPNP_CLASS_ITEM + ".imageItem";
    public static final String UPNP_CLASS_PHOTO = UPNP_CLASS_IMAGE_ITEM + ".photo";
    public static final String UPNP_CLASS_AUDIO_ITEM = UPNP_CLASS_ITEM + ".audioItem";
    public static final String UPNP_CLASS_MUSIC_TRACK = UPNP_CLASS_AUDIO_ITEM + ".musicTrack";
    public static final String UPNP_CLASS_VIDEO_ITEM = UPNP_CLASS_ITEM + ".videoItem";
    public static final String UPNP_CLASS_MOVIE = UPNP_CLASS_VIDEO_ITEM + ".movie";

    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_FOLDER = "folder";
    public static final String PROPERTY_TITLE = "title";
    public static final String PROPERTY_CREATOR = "creator";
    public static final String PROPERTY_CLASS = "class";
    public static final String PROPERTY_SEARCHABLE = "searchable";
    public static final String PROPERTY_PARENT_ID = "parentID";
    public static final String PROPERTY_RESTRICTED = "restricted";
    public static final String PROPERTY_CHILD_COUNT = "childCount";

    public static final String PROPERTY_GENRE = "genre";
    public static final String PROPERTY_ARTIST = "artist";
    public static final String PROPERTY_ALBUM = "album";
}
