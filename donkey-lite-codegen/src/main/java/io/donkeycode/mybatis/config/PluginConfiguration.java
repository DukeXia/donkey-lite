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
package io.donkeycode.mybatis.config;

import static io.donkeycode.mybatis.internal.util.StringUtility.stringHasValue;
import static io.donkeycode.mybatis.internal.util.messages.Messages.getString;

import java.util.List;

import io.donkeycode.mybatis.api.dom.xml.Attribute;
import io.donkeycode.mybatis.api.dom.xml.XmlElement;

/**
 *
 * @author Jeff Butler
 *
 */
public class PluginConfiguration extends TypedPropertyHolder {
    public PluginConfiguration() {
    }

    public XmlElement toXmlElement() {
        XmlElement answer = new XmlElement("plugin");
        if (getConfigurationType() != null) {
            answer.addAttribute(new Attribute("type", getConfigurationType()));
        }

        addPropertyXmlElements(answer);

        return answer;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(getConfigurationType())) {
            errors.add(getString("ValidationError.17",
                contextId));
        }
    }
}
