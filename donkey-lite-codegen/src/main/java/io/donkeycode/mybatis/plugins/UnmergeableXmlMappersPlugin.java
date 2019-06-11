/**
 * Copyright 2006-2018 the original author or authors.
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
package io.donkeycode.mybatis.plugins;

import java.util.List;

import io.donkeycode.mybatis.api.GeneratedXmlFile;
import io.donkeycode.mybatis.api.IntrospectedTable;
import io.donkeycode.mybatis.api.PluginAdapter;

/**
 * This plugin marks generated XML mapper files as unmergeable.  This will cause the generator to either
 * overwrite the files, or save the files under a new name depending on how the overwrite setting is configured.
 *
 * This can be useful when comments are disabled so the normal XML merge won't work. 
 *
 * @author Jeff Butler
 *
 */
public class UnmergeableXmlMappersPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        sqlMap.setMergeable(false);
        return true;
    }
}
