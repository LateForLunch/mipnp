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
 * AbstractRootDevice.java
 * Created on Jun 24, 2011, 5:48:31 PM
 */
package domain.upnp;

import java.net.URL;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public abstract class AbstractRootDevice extends AbstractDevice implements IRootDevice {

    private int bootId;
    private int configId;
    private URL descriptionUrl;

    public AbstractRootDevice() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getBootId() {
        return bootId;
    }

    public int getConfigId() {
        return configId;
    }

    public URL getDescriptionUrl() {
        return descriptionUrl;
    }
}
