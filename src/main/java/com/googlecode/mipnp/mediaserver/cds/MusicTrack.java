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
 * Created on Jul 28, 2011, 1:39:25 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.io.File;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MusicTrack extends CdsObject {

    private static final String UPNP_CLASS = "object.item.audioItem.musicTrack";

    public MusicTrack(File musicTrack, String mimeType) {
        super(UPNP_CLASS);
        if (musicTrack == null || !musicTrack.isFile()) {
            throw new IllegalArgumentException(
                    "Can't create a MusicTrack without a file.");
        }
        String title = musicTrack.getName();
        title = title.substring(0, title.lastIndexOf('.'));
        setTitle(title);
        setResource(new FileResource(musicTrack, mimeType));
    }
}
