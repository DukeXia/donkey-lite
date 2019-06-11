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
package io.donkeycode.mybatis.config;

import static io.donkeycode.mybatis.internal.util.StringUtility.stringHasValue;
import static io.donkeycode.mybatis.internal.util.messages.Messages.getString;

import java.util.List;

import io.donkeycode.mybatis.api.dom.xml.Attribute;
import io.donkeycode.mybatis.api.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 */
public class SqlMapGeneratorConfiguration extends PropertyHolder {
    private String targetPackage;

    private String targetProject;

    /**
     *
     */
    public SqlMapGeneratorConfiguration() {
        super();
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public XmlElement toXmlElement() {
        XmlElement answer = new XmlElement("sqlMapGenerator");

        if (targetPackage != null) {
            answer.addAttribute(new Attribute("targetPackage", targetPackage));
        }

        if (targetProject != null) {
            answer.addAttribute(new Attribute("targetProject", targetProject));
        }

        addPropertyXmlElements(answer);

        return answer;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(targetProject)) {
            errors.add(getString("ValidationError.1", contextId));
        }

        if (!stringHasValue(targetPackage)) {
            errors.add(getString("ValidationError.12",
                "SQLMapGenerator", contextId));
        }
    }
}