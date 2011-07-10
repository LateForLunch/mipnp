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
 * AbstractService.java
 * Created on Jun 30, 2011, 3:54:43 PM
 */
package com.googlecode.mipnp.upnp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public abstract class AbstractService implements Service {

    private String vendorDomainName;
    private String type;
    private String id;
    private String version;
    private List<Action> actions;

    public AbstractService(
            String vendorDomainName, String type, String id, String version) {

        this.vendorDomainName = vendorDomainName;
        this.type = type;
        this.id = id;
        this.version = version;
        this.actions = new ArrayList<Action>();
    }

    public String getVendorDomainName() {
        return vendorDomainName;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public List<Action> getActions() {
        return actions;
    }

    public boolean addAction(Action action) {
        return actions.add(action);
    }

    public boolean removeAction(Action action) {
        return actions.remove(action);
    }
}
