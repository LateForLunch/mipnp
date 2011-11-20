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
 * Extensions.java
 * Created on Nov 12, 2011, 1:40:33 PM
 */
package com.googlecode.mipnp.extension;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Extensions {

    public static <T> List<Extension<T>> load(Class<T> service) {
        List<Extension<T>> extensions = new ArrayList<Extension<T>>();

        ServiceLoader<T> sl = ServiceLoader.load(service);
        for (T obj : sl) {
            Annotation a = obj.getClass().getAnnotation(ExtensionInfo.class);
            if (a != null) {
                ExtensionInfo e = (ExtensionInfo) a;
                extensions.add(
                        new Extension<T>(obj, e.name(), e.description()));
            }
        }

        return extensions;
    }
}
