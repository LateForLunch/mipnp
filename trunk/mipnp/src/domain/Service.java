/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jeroen De Wilde
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
 * Service.java
 * Created on Oct 23, 2010, 3:37:50 PM
 */
package domain;

import java.net.URL;

public class Service {

    private String serviceType;
    private String serviceId;
    private URL scpdURL;
    private URL controlURL;
    private URL eventSubURL;

    public Service(String serviceId, String serviceType){
        this.serviceId=serviceId;this.serviceType=serviceType;
    }

    /**
     * @return the scpdURL
     */
    public URL getScpdURL() {
        return scpdURL;
    }

    /**
     * @param scpdURL the scpdURL to set
     */
    public void setScpdURL(URL scpdURL) {
        this.scpdURL = scpdURL;
    }

    /**
     * @return the controlURL
     */
    public URL getControlURL() {
        return controlURL;
    }

    /**
     * @param controlURL the controlURL to set
     */
    public void setControlURL(URL controlURL) {
        this.controlURL = controlURL;
    }

    /**
     * @return the eventSubURL
     */
    public URL getEventSubURL() {
        return eventSubURL;
    }

    /**
     * @param eventSubURL the eventSubURL to set
     */
    public void setEventSubURL(URL eventSubURL) {
        this.eventSubURL = eventSubURL;
    }
}
