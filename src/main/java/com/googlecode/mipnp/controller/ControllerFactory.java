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
 * ControllerFactory.java
 * Created on Nov 6, 2011, 2:27:10 PM
 */
package com.googlecode.mipnp.controller;

import com.googlecode.mipnp.instance.SingleInstance;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ControllerFactory {

    private static SingleInstance singleInstance = SingleInstance.getInstance();

    public static MainController createMainController(Preferences prefs) {
        boolean lock = singleInstance.lock();
        if (!lock) {
            singleInstance.notifyOtherInstance(prefs.getProgramArguments());
            System.out.println("MiPnP is already running."); // TODO: maybe replace
            System.exit(0);
        }

        AbstractMainController controller = null;
        if (GraphicsEnvironment.isHeadless()) {
            controller = new CliController(prefs);
        } else {
            controller = new GuiController(prefs);
        }
        singleInstance.addSingleInstanceListener(controller);

        return controller;
    }
}
