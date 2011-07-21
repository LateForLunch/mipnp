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

import com.googlecode.mipnp.upnp.Action;
import com.googlecode.mipnp.upnp.Parameter;
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
            "<name>GetCurrentConnectionInfo</name>" +
            "<argumentList>" +
            "<argument>" +
            "<name>ConnectionID</name>" +
            "<direction>in</direction>" +
            "<relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>" +
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
            "<stateVariable sendEvents=\"yes\" multicast=\"yes\">" +
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
            "<name>A_ARG_TYPE_ConnectionID</name>" +
            "<dataType>i4</dataType>" +
            "<defaultValue>2</defaultValue>" +
            "<allowedValueRange>" +
            "<minimum>0</minimum>" +
            "<maximum>10</maximum>" +
            "<step>1</step>" +
            "</allowedValueRange>" +
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
        ServiceImpl service = new ServiceImpl("", "", "", "");
        ServiceDescriptionParser parser = new ServiceDescriptionParser(service);

        parser.parse(bais);

        /*
         * StateVariable: A_ARG_TYPE_ConnectionStatus
         */
        StateVariable stateVar1 =
                service.getStateVariable("A_ARG_TYPE_ConnectionStatus");
        assertNotNull(
                "'A_ARG_TYPE_ConnectionStatus' did not parse correctly.",
                stateVar1);
        assertEquals(
                "DataType of 'A_ARG_TYPE_ConnectionStatus' should be string.",
                StateVariable.DataType.STRING, stateVar1.getDataType());
        assertTrue(
                "SendEvents of 'A_ARG_TYPE_ConnectionStatus' should be true",
                stateVar1.hasToSendEvents());
        assertTrue(
                "Multicast of 'A_ARG_TYPE_ConnectionStatus' should be true",
                stateVar1.hasToMulticast());

        List<String> allowedVals = stateVar1.getAllowedValues();
        assertEquals(
                "Allowed values of 'A_ARG_TYPE_ConnectionStatus' should be 5.",
                5, allowedVals.size());
        assertTrue(
                "Allowed value 'OK' didn't get parsed.",
                allowedVals.contains("OK"));
        assertTrue(
                "Allowed value 'ContentFormatMismatch' didn't get parsed.",
                allowedVals.contains("ContentFormatMismatch"));
        assertTrue(
                "Allowed value 'InsufficientBandwidth' didn't get parsed.",
                allowedVals.contains("InsufficientBandwidth"));
        assertTrue(
                "Allowed value 'UnreliableChannel' didn't get parsed.",
                allowedVals.contains("UnreliableChannel"));
        assertTrue(
                "Allowed value 'Unknown' didn't get parsed.",
                allowedVals.contains("Unknown"));

        /*
         * StateVariable: A_ARG_TYPE_ConnectionID
         */
        StateVariable stateVar2 =
                service.getStateVariable("A_ARG_TYPE_ConnectionID");
        assertNotNull(
                "'A_ARG_TYPE_ConnectionID' did not parse correctly.",
                stateVar2);
        assertEquals(
                "DataType of 'A_ARG_TYPE_ConnectionID' should be i4.",
                StateVariable.DataType.I4, stateVar2.getDataType());
        assertFalse(
                "SendEvents of 'A_ARG_TYPE_ConnectionID' should be false",
                stateVar2.hasToSendEvents());
        assertFalse(
                "Multicast of 'A_ARG_TYPE_ConnectionID' should be false",
                stateVar2.hasToMulticast());

        assertEquals(
                "Allowed values of 'A_ARG_TYPE_ConnectionID' should be 0.",
                0, stateVar2.getAllowedValues().size());
        assertEquals(
                "Default value of 'A_ARG_TYPE_ConnectionID' should be 2.",
                "2", stateVar2.getDefaultValue());
        assertEquals(
                "Minimum value of 'A_ARG_TYPE_ConnectionID' should be 0.",
                "0", stateVar2.getAllowedRangeMin());
        assertEquals(
                "Maximum value of 'A_ARG_TYPE_ConnectionID' should be 10.",
                "10", stateVar2.getAllowedRangeMax());
        assertEquals(
                "Step value of 'A_ARG_TYPE_ConnectionID' should be 1.",
                "1", stateVar2.getAllowedRangeStep());

        /*
         * Action: GetCurrentConnectionInfo
         */
        Action action1 =
                service.getAction("GetCurrentConnectionInfo");
        assertNotNull(
                "'GetCurrentConnectionInfo' did not parse correctly.",
                action1);

        List<Parameter> parameters = action1.getParameters();
        assertEquals(
                "Number of parameters of 'GetCurrentConnectionInfo' should be 2.",
                2, parameters.size());

        for (Parameter param : parameters) {
            if (param.getName().equals("ConnectionID")) {
                assertEquals(
                        "Direction for 'ConnectionID' should be in.",
                        Parameter.Mode.IN, param.getDirection());
                assertEquals(
                        "Related state variable for 'ConnectionID' should be A_ARG_TYPE_ConnectionID.",
                        stateVar2, param.getRelatedStateVariable());
            } else if (param.getName().equals("Status")) {
                assertEquals(
                        "Direction for 'Status' should be out.",
                        Parameter.Mode.OUT, param.getDirection());
                assertEquals(
                        "Related state variable for 'ConnectionID' should be A_ARG_TYPE_ConnectionStatus.",
                        stateVar1, param.getRelatedStateVariable());
            } else {
                fail("Unknown parameter for 'GetCurrentConnectionInfo': " + param.getName());
            }
        }
    }
}
