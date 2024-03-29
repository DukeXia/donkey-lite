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

import lombok.Getter;
import lombok.Setter;
import org.donkeycode.codegen.api.dom.xml.Attribute;
import org.donkeycode.codegen.api.dom.xml.XmlElement;

/**
 * This class is used to specify a renaming rule for columns in a table. This
 * renaming rule will be run against all column names before calculating the
 * corresponding property name. The most common use case is when columns in a
 * table are all prefixed by a certain value.
 *
 * <p>For example, if columns in a table are named:
 *
 * <ul>
 * <li>CUST_NAME</li>
 * <li>CUST_ADDRESS</li>
 * <li>CUST_CITY</li>
 * <li>CUST_STATE</li>
 * </ul>
 *
 * <p>it might be annoying to have the generated properties all containing the CUST
 * prefix. This class can be used to remove the prefix by specifying
 *
 * <ul>
 * <li>searchString = "^CUST"</li>
 * <li>replaceString=""</li>
 * </ul>
 *
 * <p>Note that internally, the generator uses the
 * <code>java.util.regex.Matcher.replaceAll</code> method for this function. See
 * the documentation of that method for example of the regular expression
 * language used in Java.
 *
 * @author Jeff Butler
 */
@Getter
@Setter
public class ColumnRenamingRule {
    private String searchString;
    private String replaceString;


    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(searchString)) {
            errors.add(getString("ValidationError.14", tableName));
        }
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("columnRenamingRule");
        xmlElement.addAttribute(new Attribute("searchString", searchString));

        if (replaceString != null) {
            xmlElement.addAttribute(new Attribute("replaceString", replaceString));
        }

        return xmlElement;
    }
}
