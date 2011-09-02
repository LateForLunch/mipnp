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
 * Video.java
 * Created on Aug 31, 2011, 11:41:35 AM
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
public class Video extends CdsObject {

    public Video(String title, File videoFile) {
        super(UPNP_CLASS_VIDEO_ITEM);
        setTitle(title);
        setResource(new FileResource(videoFile));
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
}
