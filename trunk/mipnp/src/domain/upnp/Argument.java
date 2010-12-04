/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.upnp;

/**
 *
 * @author nicholaihel
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
