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

package domain.upnp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeroen De Wilde
 */
public class Action {

    private String name;
    private List<Argument> arguments;
    
    public Action(String name){
        this.name=name;
        arguments = new ArrayList<Argument>();
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
     * @return the arguments
     */
    public List<Argument> getArguments() {
        return arguments;
    }

    /**     
     * @param argument the argument to be added to argument list
     */

    public void addArgument(Argument argument){
     if(!arguments.contains(argument))
         arguments.add(argument);
     else{
         throw new IllegalArgumentException("Argument list already contains argument: "+argument.getName()+"!");
     }
    }
}
