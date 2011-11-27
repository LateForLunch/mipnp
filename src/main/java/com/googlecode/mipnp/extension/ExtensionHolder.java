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
 * ExtensionHolder.java
 * Created on Nov 13, 2011, 11:39:23 AM
 */
package com.googlecode.mipnp.extension;

import java.lang.reflect.Method;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ExtensionHolder<T> {

    private String name;
    private String description;
    private T extensionObject;
    private boolean loaded;

    public ExtensionHolder(String name, String description, T extensionObject) {
        this.name = name;
        this.description = description;
        this.extensionObject = extensionObject;
        this.loaded = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public T getExtensionObject() {
        if (!loaded) {
            throw new IllegalStateException(
                    "The extension is not loaded.");
        }
        return extensionObject;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public synchronized void loadExtension() throws ExtensionException {
        if (!loaded) {
            try {
                invokeMethods(ExtensionMethodType.LOAD);
            } catch (Throwable ex) {
                throw new ExtensionException(
                        "An error occurred while loading extension: " +
                        getName(), ex);
            }
            this.loaded = true;
        }
    }

    public synchronized void unloadExtension() throws ExtensionException {
        if (loaded) {
            try {
                invokeMethods(ExtensionMethodType.UNLOAD);
            } catch (Throwable ex) {
                throw new ExtensionException(
                        "An error occurred while unloading extension: " +
                        getName(), ex);
            }
            this.loaded = false;
        }
    }

    private void invokeMethods(ExtensionMethodType type) throws Throwable {
        Class<?> objClass = extensionObject.getClass();
        for (Method m : objClass.getMethods()) {
            ExtensionMethod em = m.getAnnotation(ExtensionMethod.class);
            if (em != null && em.value() == type) {
                m.invoke(extensionObject);
            }
        }
    }
}
