/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AbstractService.java
 * Created on Jun 30, 2011, 3:54:43 PM
 */
package com.googlecode.mipnp.upnp;

import java.net.URI;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public abstract class AbstractService implements IService {

    private String uniformResourceName;
    private String identifier;
    private URI descriptionUri;
    private URI controlUri;
    private URI eventUri;

    public AbstractService() {
    }

    public String getUniformResourceName() {
        return uniformResourceName;
    }

    public void setUniformResourceName(String uniformResourceName) {
        this.uniformResourceName = uniformResourceName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public URI getDescriptionUri() {
        return descriptionUri;
    }

    public void setDescriptionUri(URI descriptionUri) {
        this.descriptionUri = descriptionUri;
    }

    public URI getControlUri() {
        return controlUri;
    }

    public void setControlUri(URI controlUri) {
        this.controlUri = controlUri;
    }

    public URI getEventUri() {
        return eventUri;
    }

    public void setEventUri(URI eventUri) {
        this.eventUri = eventUri;
    }
}
