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
 * ExtensionFactory.java
 * Created on Nov 12, 2011, 1:40:33 PM
 */
package com.googlecode.mipnp.extension;

import com.googlecode.mipnp.extension.banshee.BansheeExtension;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExtensionFactory {

    /*
     * TODO: dynamic extension lookup/loading
     */

    private static final Class[] EXTENSIONS = {
        BansheeExtension.class
    };

    public static String[] getExtensionNames() {
        String[] names = new String[EXTENSIONS.length];

        for (int i = 0; i < EXTENSIONS.length; i++) {
            Annotation a = EXTENSIONS[i].getAnnotation(Extension.class);
            if (a != null) {
                Extension e = (Extension) a;
                names[i] = e.name();
            }
        }

        return names;
    }

    public static<T> List<T> loadExtensions(Class<T> extensionClass) {
        List<T> extensions = new ArrayList<T>();

        for (int i = 0; i < EXTENSIONS.length; i++) {
            Annotation a = EXTENSIONS[i].getAnnotation(Extension.class);
            if (a != null) {
                Extension e = (Extension) a;
                if (e.extensionClass().equals(extensionClass)) {
                    try {
                        T newInstance = (T) EXTENSIONS[i].newInstance();
                        extensions.add(newInstance);
                    } catch (Exception ex) {
                    }
                }
            }
        }

        return extensions;
    }
}
