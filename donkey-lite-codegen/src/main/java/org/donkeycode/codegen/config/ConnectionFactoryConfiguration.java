/**
 * Copyright 2006-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.donkeycode.codegen.config;

import static org.donkeycode.codegen.internal.util.StringUtility.stringHasValue;
import static org.donkeycode.codegen.internal.util.messages.Messages.getString;

import java.util.List;

import org.donkeycode.codegen.api.dom.xml.Attribute;
import org.donkeycode.codegen.api.dom.xml.XmlElement;
import org.donkeycode.codegen.internal.util.StringUtility;

public class ConnectionFactoryConfiguration extends TypedPropertyHolder {

    public ConnectionFactoryConfiguration() {
        super();
    }

    public void validate(List<String> errors) {
        if (getConfigurationType() == null || "DEFAULT".equals(getConfigurationType())) {
            if (!StringUtility.stringHasValue(getProperty("driverClass"))) {
                errors.add(getString("ValidationError.18", "connectionFactory", "driverClass"));   //$NON-NLS-3$
            }

            if (!StringUtility.stringHasValue(getProperty("connectionURL"))) {
                errors.add(getString("ValidationError.18", "connectionFactory", "connectionURL"));   //$NON-NLS-3$
            }
        }
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("connectionFactory");

        if (stringHasValue(getConfigurationType())) {
            xmlElement.addAttribute(new Attribute("type", getConfigurationType()));
        }

        addPropertyXmlElements(xmlElement);

        return xmlElement;
    }
}
