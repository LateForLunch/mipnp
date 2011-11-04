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
 * CliController.java
 * Created on Oct 30, 2011, 11:29:04 AM
 */
package com.googlecode.mipnp.view.cli;

import com.googlecode.mipnp.controller.AbstractMainController;
import com.googlecode.mipnp.controller.Preferences;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CliController extends AbstractMainController {

    public CliController(Preferences prefs) {
        super(prefs);
        MainCli cli = new MainCli(this);
    }
}
