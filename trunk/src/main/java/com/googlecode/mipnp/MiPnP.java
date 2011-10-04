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
 * MiPnP.java
 * Created on Oct 4, 2011, 5:20:15 PM
 */
package com.googlecode.mipnp;

import com.googlecode.mipnp.controller.MainController;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class MiPnP {

    public static void main(String[] args) {
//        String busFactory =
//                System.getProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME);
//        System.setProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME,
//                "org.apache.cxf.bus.CXFBusFactory");

        MainController controller = new MainController(args);

//            File mediaDir = new File("/home/jochem/ushare");
//            FileSystemSource fss = new FileSystemSource(mediaDir);
//            library.addMusic(fss);
//            library.addVideos(fss);
//            library.addPictures(fss);
//            File bansheeDb = new File("src/main/resources/banshee/banshee.db");
//            BansheePlugin bansheePlugin = new BansheePlugin(bansheeDb);
//            library.addMusic(bansheePlugin);
//            library.addVideos(bansheePlugin);

//            System.out.printf(
//                    " - HTTP server listening on: %s:%d\n",
//                    server.getHttpAddress().getHostAddress(), server.getHttpPort());
//            System.out.println();

//            if (busFactory != null) {
//                System.setProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME,
//                        busFactory);
//            } else {
//                System.clearProperty(BusFactory.BUS_FACTORY_PROPERTY_NAME);
//            }
//        }
    }
}
