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
 * FileResource.java
 * Created on Aug 2, 2011, 3:34:56 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class FileResource extends Resource {

    private File file;

    public FileResource(File file, String mimeType) {
        super(mimeType);
        this.file = file;
    }

    @Override
    public RandomAccessFile getDataInput() {
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (Exception ex) {
//        }
//        return fis;

        RandomAccessFile ras = null;
        try {
            ras = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException ex) {
        }
        return ras;
    }

    @Override
    public long getContentLength() {
        return file.length();
    }

    @Override
    public long getLastModified() {
        return file.lastModified();
    }
}
