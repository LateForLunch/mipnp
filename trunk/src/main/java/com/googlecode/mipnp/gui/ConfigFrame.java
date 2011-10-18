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
 * ConfigFrame.java
 * Created on Oct 5, 2011, 3:26:34 PM
 */
package com.googlecode.mipnp.gui;

import com.googlecode.mipnp.controller.MainController;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ConfigFrame extends JFrame {

    private MainController controller;

    public ConfigFrame(MainController controller) throws HeadlessException {
        super("MiPnP");
        this.controller = controller;
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.addTab("Basic Settings", new BasicSettingsPanel(controller));

//        getContentPane().add(new ConfigPanel(controller));
//        getContentPane().add(new MediaPanel(controller));
//        getContentPane().add(tabbedPane);
        getContentPane().add(new SettingsPanel(controller));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
