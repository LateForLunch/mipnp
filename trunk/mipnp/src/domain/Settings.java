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
 * Settings.java
 * Created on Dec 1, 2010, 8:09:18 PM
 */
package domain;

import java.util.Properties;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Settings {

    private static Properties settings;

    static {
        settings = new Properties();
        // TODO: Set default settings
    }

    public static void parseArguments(String[] args) {
        // TODO: parse args
    }

    public static synchronized Object setProperty(String key, String value) {
        return settings.setProperty(key, value);
    }

    public static String getProperty(String key) {
        return settings.getProperty(key);
    }
}
