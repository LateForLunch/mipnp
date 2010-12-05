/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.upnp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicholaihel
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

    public void addArgument(Argument argument){
     if(!arguments.contains(argument))
         arguments.add(argument);
     else{
         throw new IllegalArgumentException("Argument list already contains argument: "+argument.getName()+"!");
     }
    }
}
