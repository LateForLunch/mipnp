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
 * Settings.java
 * Created on Oct 3, 2011, 3:18:29 PM
 */
package com.googlecode.mipnp.cli;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class Settings {

    private Properties defaultProps;
    private Properties fileProps;
    private Properties commandLineProps;

    public Settings() {
    }

    private void init() {
    }
}
