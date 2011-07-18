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
 * ServiceDescriptionParser.java
 * Created on Jul 13, 2011, 8:46:30 PM
 */
package com.googlecode.mipnp.upnp.xml;

import com.googlecode.mipnp.upnp.ActionImpl;
import com.googlecode.mipnp.upnp.Parameter;
import com.googlecode.mipnp.upnp.ParameterImpl;
import com.googlecode.mipnp.upnp.ServiceImpl;
import com.googlecode.mipnp.upnp.StateVariable;
import com.googlecode.mipnp.upnp.StateVariableImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class ServiceDescriptionParser {

    private static final String ACTION = "action";
    private static final String STATE_VAR = "stateVariable";
    private static final String NAME = "name";
    private static final String DATA_TYPE = "dataType";
    private static final String SEND_EVENTS = "sendEvents";
    private static final String MULTICAST = "multicast";
    private static final String ARGUMENT = "argument";
    private static final String DIRECTION = "direction";
    private static final String RELATED_STATE_VAR = "relatedStateVariable";
    private static final String DEFAULT_VALUE = "defaultValue";
    private static final String ALLOWED_VALUE = "allowedValue";
    private static final String MINIMUM = "minimum";
    private static final String MAXIMUM = "maximum";
    private static final String STEP = "step";

    private ServiceImpl target;

    public ServiceDescriptionParser(ServiceImpl target) {
        this.target = target;
    }

    public void parse(File xmlFile)
            throws FileNotFoundException, ParserConfigurationException,
            SAXException, IOException {

        FileInputStream fis = new FileInputStream(xmlFile);
        parse(fis);
    }

    public void parse(InputStream is)
            throws ParserConfigurationException, SAXException, IOException {

        Document doc = parseXml(is);
        parseStateVars(doc);
        parseActions(doc);
    }

    private Document parseXml(InputStream is)
            throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        return document;
    }

    private void parseStateVars(Document doc) {
        NodeList varlist = doc.getElementsByTagName(STATE_VAR);
        for (int i = 0; i < varlist.getLength(); i++) {
            Element stateVarElement = (Element) varlist.item(i);

            boolean sendEvents = true;
            if (stateVarElement.getAttribute(SEND_EVENTS).equals("no")) {
                sendEvents = false;
            }
            boolean multicast = false;
            if (stateVarElement.getAttribute(MULTICAST).equals("yes")) {
                multicast = true;
            }

            String name = getTextValue(stateVarElement, NAME);
            String dataTypeStr = getTextValue(stateVarElement, DATA_TYPE);
            dataTypeStr = dataTypeStr.toUpperCase().replace('.', '_');
            if (dataTypeStr.equals("DATETIME")) {
                dataTypeStr = "DATE_TIME";
            } else if (dataTypeStr.equals("DATETIME_TZ")) {
                dataTypeStr = "DATE_TIME_TZ";
            }
            StateVariable.DataType dataType =
                    StateVariable.DataType.valueOf(dataTypeStr);

            StateVariableImpl stateVar = new StateVariableImpl(
                    name, dataType, sendEvents, multicast);

            stateVar.setDefaultValue(getTextValue(stateVarElement, DEFAULT_VALUE));

            NodeList allowedVals =
                    stateVarElement.getElementsByTagName(ALLOWED_VALUE);
            for (int j = 0; j < allowedVals.getLength(); j++) {
                Node allowedVal = allowedVals.item(j).getFirstChild();
                stateVar.addAllowedValue(allowedVal.getNodeValue());
            }

            stateVar.setAllowedRangeMin(getTextValue(stateVarElement, MINIMUM));
            stateVar.setAllowedRangeMax(getTextValue(stateVarElement, MAXIMUM));
            stateVar.setAllowedRangeStep(getTextValue(stateVarElement, STEP));

            target.addStateVariable(stateVar);
        }
    }

    private void parseActions(Document doc) {
        NodeList actionlist = doc.getElementsByTagName(ACTION);
        for (int i = 0; i < actionlist.getLength(); i++) {
            Element actionElement = (Element) actionlist.item(i);

            String name = getTextValue(actionElement, NAME);
            ActionImpl action = new ActionImpl(name);

            for (Parameter param : parseParams(actionElement)) {
                action.addParameter(param);
            }

            target.addAction(action);
        }
    }

    private List<Parameter> parseParams(Element action) {
        List<Parameter> params = new ArrayList<Parameter>();
        NodeList argList = action.getElementsByTagName(ARGUMENT);

        for (int i = 0; i < argList.getLength(); i++) {
            Element paramElement = (Element) argList.item(i);

            String name = getTextValue(paramElement, NAME);
            String dir = getTextValue(paramElement, DIRECTION);
            Parameter.Mode mode = Parameter.Mode.valueOf(dir.toUpperCase());
            String stateVarStr = getTextValue(paramElement, RELATED_STATE_VAR);
            StateVariable stateVar = target.getStateVariable(stateVarStr);

            Parameter param = new ParameterImpl(name, mode, stateVar);
            params.add(param);
        }
        return params;
    }

    private String getTextValue(Element el, String tagName) {
        String value = null;
        NodeList list = el.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            Node node = list.item(0);
            value = node.getFirstChild().getNodeValue();
        }
        return value;
    }
}
