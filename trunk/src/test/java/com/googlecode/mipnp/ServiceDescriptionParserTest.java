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
 * ServiceDescriptionParserTest.java
 * Created on Jul 18, 2011, 10:16:03 AM
 */
package com.googlecode.mipnp;

import com.googlecode.mipnp.upnp.ServiceImpl;
import com.googlecode.mipnp.upnp.StateVariable;
import com.googlecode.mipnp.upnp.xml.ServiceDescriptionParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ServiceDescriptionParserTest extends TestCase {

    private static final String SERVICE_DESC_1 = "<?xml version=\"1.0\"?>" +
            "<scpd xmlns=\"urn:schemas-upnp-org:service-1-0\">" +
            "<specVersion>" +
            "<major>1</major>" +
            "<minor>0</minor>" +
            "</specVersion>" +
            "<actionList>" +
            "<action>" +
            "<name>GetProtocolInfo</name>" +
            "<argumentList>" +
            "<argument>" +
            "<name>Source</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>SourceProtocolInfo</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>Sink</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>SinkProtocolInfo</relatedStateVariable>" +
            "</argument>" +
            "</argumentList>" +
            "</action>" +
            "<action>" +
            "<name>PrepareForConnection</name>" +
            "<argumentList>" +
            "<argument>" +
            "<name>RemoteProtocolInfo</name>" +
            "<direction>in</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ProtocolInfo</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>PeerConnectionManager</name>" +
            "<direction>in</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionManager</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>PeerConnectionID</name>" +
            "<direction>in</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>Direction</name>" +
            "<direction>in</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_Direction</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>ConnectionID</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>AVTransportID</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_AVTransportID</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>RcsID</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_RcsID</relatedStateVariable>" +
            "</argument>" +
            "</argumentList>" +
            "</action>" +
            "<action>" +
            "<name>ConnectionComplete</name>" +
            "<argumentList>" +
            "<argument>" +
            "<name>ConnectionID</name>" +
            "<direction>in</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>" +
            "</argument>" +
            "</argumentList>" +
            "</action>" +
            "<action>" +
            "<name>GetCurrentConnectionIDs</name>" +
            "<argumentList>" +
            "<argument>" +
            "<name>ConnectionIDs</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>CurrentConnectionIDs</relatedStateVariable>" +
            "</argument>" +
            "</argumentList>" +
            "</action>" +
            "<action>" +
            "<name>GetCurrentConnectionInfo</name>" +
            "<argumentList>" +
            "<argument>" +
            "<name>ConnectionID</name>" +
            "<direction>in</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>RcsID</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_RcsID</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>AVTransportID</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_AVTransportID</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>ProtocolInfo</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ProtocolInfo</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>PeerConnectionManager</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionManager</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>PeerConnectionID</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>Direction</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_Direction</relatedStateVariable>" +
            "</argument>" +
            "<argument>" +
            "<name>Status</name>" +
            "<direction>out</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionStatus</relatedStateVariable>" +
            "</argument>" +
            "</argumentList>" +
            "</action>" +
            "</actionList>" +
            "<serviceStateTable>" +
            "<stateVariable sendEvents=\"yes\">" +
            "<name>SourceProtocolInfo</name>" +
            "<dataType>string</dataType>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"yes\">" +
            "<name>SinkProtocolInfo</name>" +
            "<dataType>string</dataType>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"yes\">" +
            "<name>CurrentConnectionIDs</name>" +
            "<dataType>string</dataType>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"no\">" +
            "<name>A_ARG_TYPE_ConnectionStatus</name>" +
            "<dataType>string</dataType>" +
            "<allowedValueList>" +
            "<allowedValue>OK</allowedValue>" +
            "<allowedValue>ContentFormatMismatch</allowedValue>" +
            "<allowedValue>InsufficientBandwidth</allowedValue>" +
            "<allowedValue>UnreliableChannel</allowedValue>" +
            "<allowedValue>Unknown</allowedValue>" +
            "</allowedValueList>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"no\">" +
            "<name>A_ARG_TYPE_ConnectionManager</name>" +
            "<dataType>string</dataType>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"no\">" +
            "<name>A_ARG_TYPE_Direction</name>" +
            "<dataType>string</dataType>" +
            "<allowedValueList>" +
            "<allowedValue>Input</allowedValue>" +
            "<allowedValue>Output</allowedValue>" +
            "</allowedValueList>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"no\">" +
            "<name>A_ARG_TYPE_ProtocolInfo</name>" +
            "<dataType>string</dataType>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"no\">" +
            "<name>A_ARG_TYPE_ConnectionID</name>" +
            "<dataType>i4</dataType>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"no\">" +
            "<name>A_ARG_TYPE_AVTransportID</name>" +
            "<dataType>i4</dataType>" +
            "</stateVariable>" +
            "<stateVariable sendEvents=\"no\">" +
            "<name>A_ARG_TYPE_RcsID</name>" +
            "<dataType>i4</dataType>" +
            "</stateVariable>" +
            "</serviceStateTable>" +
            "</scpd>";

    public ServiceDescriptionParserTest(String testName) {
        super(testName);
    }

    public void testParse1()
            throws ParserConfigurationException, SAXException, IOException {

        byte[] bytes = SERVICE_DESC_1.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ServiceImpl service = new ServiceImpl("", "", "", "", null, null, null);
        ServiceDescriptionParser parser = new ServiceDescriptionParser(service);

        parser.parse(bais);

        StateVariable stateVar =
                service.getStateVariable("A_ARG_TYPE_ConnectionStatus");
        assertNotNull("'A_ARG_TYPE_ConnectionStatus' did not parse correctly.", stateVar);

        List<String> allowedVals = stateVar.getAllowedValues();
        assertEquals("Allowed values of 'A_ARG_TYPE_ConnectionStatus' should be 5.",
                5, allowedVals.size());
        assertTrue("Allowed value 'OK' didn't get parsed.",
                allowedVals.contains("OK"));
        assertTrue("Allowed value 'ContentFormatMismatch' didn't get parsed.",
                allowedVals.contains("ContentFormatMismatch"));
        assertTrue("Allowed value 'InsufficientBandwidth' didn't get parsed.",
                allowedVals.contains("InsufficientBandwidth"));
        assertTrue("Allowed value 'UnreliableChannel' didn't get parsed.",
                allowedVals.contains("UnreliableChannel"));
        assertTrue("Allowed value 'Unknown' didn't get parsed.",
                allowedVals.contains("Unknown"));
    }
}
