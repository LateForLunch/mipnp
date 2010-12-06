/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche, Tijl Van Assche
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

package domain.upnp;

/**
 *
 * @author Jeroen De Wilde
 */
public class Argument {
    private String name;
    private String direction;
    private StateVariable relatedStateVariable;

    public Argument(String name, String direction, StateVariable relatedStateVariable){
        this.name = name;
        this.direction = direction;
        this.relatedStateVariable = relatedStateVariable;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the relatedStateVariable
     */
    public StateVariable getRelatedStateVariable() {
        return relatedStateVariable;
    }

    /**
     * @param relatedStateVariable the relatedStateVariable to set
     */
    public void setRelatedStateVariable(StateVariable relatedStateVariable) {
        this.relatedStateVariable = relatedStateVariable;
    }

}
