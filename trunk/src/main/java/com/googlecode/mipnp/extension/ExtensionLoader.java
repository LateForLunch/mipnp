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
 * ExtensionLoader.java
 * Created on Nov 12, 2011, 1:40:33 PM
 */
package com.googlecode.mipnp.extension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExtensionLoader {

    public static <T> List<ExtensionHolder<T>> load(Class<T> extensionClass) {
        List<ExtensionHolder<T>> extensions = new ArrayList<ExtensionHolder<T>>();

        ServiceLoader<T> sl = ServiceLoader.load(extensionClass);
        for (T obj : sl) {
            Class<?> objClass = obj.getClass();
            Extension ext = objClass.getAnnotation(Extension.class);
            if (ext != null && invokeLoadMethod(obj)) {
                extensions.add(new ExtensionHolder<T>(
                        obj, ext.name(), ext.description()));
            }
        }

        return extensions;
    }

    private static boolean invokeLoadMethod(Object obj) {
        Class<?> objClass = obj.getClass();

        for (Method m : objClass.getMethods()) {
            if (m.isAnnotationPresent(ExtensionLoadMethod.class)) {
                try {
                    m.invoke(obj);
                } catch (Throwable ex) {
                    return false;
                }
            }
        }

        return true;
    }
}
