/**
 * Copyright 2006-2016 the original author or authors.
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

/**
 *	数据库链接配置
 *
 * @author Jeff Butler
 */
public class JDBCConnectionConfiguration extends PropertyHolder {

    private String driverClass;

    private String connectionURL;

    private String userId;

    private String password;

    public JDBCConnectionConfiguration() {
        super();
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("jdbcConnection");
        xmlElement.addAttribute(new Attribute("driverClass", driverClass));
        xmlElement.addAttribute(new Attribute("connectionURL", connectionURL));

        if (stringHasValue(userId)) {
            xmlElement.addAttribute(new Attribute("userId", userId));
        }

        if (stringHasValue(password)) {
            xmlElement.addAttribute(new Attribute("password", password));
        }

        addPropertyXmlElements(xmlElement);

        return xmlElement;
    }

    public void validate(List<String> errors) {
        if (!stringHasValue(driverClass)) {
            errors.add(getString("ValidationError.4"));
        }

        if (!stringHasValue(connectionURL)) {
            errors.add(getString("ValidationError.5"));
        }
    }
}
