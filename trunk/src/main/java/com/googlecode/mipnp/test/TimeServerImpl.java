/*
 * TimeServerImpl.java
 * Created on Jun 29, 2011, 11:58:19 AM
 */
package com.googlecode.mipnp.test;

import java.util.Date;
import javax.jws.WebService;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
@WebService(endpointInterface="com.googlecode.mipnp.test.TimeServer")
public class TimeServerImpl implements TimeServer {

    public long getTimeAsElapsed() {
        return new Date().getTime();
    }
}
