/*
 * TimeServer.java
 * Created on Jun 29, 2011, 11:56:31 AM
 */
package com.googlecode.mipnp.test;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface TimeServer {

    @WebMethod long getTimeAsElapsed();
}
