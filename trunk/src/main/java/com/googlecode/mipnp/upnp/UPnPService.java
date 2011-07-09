/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * UPnPService.java
 * Created on Jul 7, 2011, 12:18:34 PM
 */
package com.googlecode.mipnp.upnp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as implementing a UPnP service,
 * or an interface as defining a UPnP service.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UPnPService {

    /**
     * The vendor of the UPnP service.
     */
    String vendor() default "upnp-org";

    /**
     * The type of the UPnP service.
     */
    String type();

    /**
     * The version of the UPnP service.
     */
    String version();
}
