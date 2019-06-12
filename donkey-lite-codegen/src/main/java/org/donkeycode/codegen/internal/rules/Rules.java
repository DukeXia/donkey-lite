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
package org.donkeycode.codegen.internal.rules;

import org.donkeycode.codegen.api.IntrospectedTable;
import org.donkeycode.codegen.api.dom.java.FullyQualifiedJavaType;

/**
 * This interface centralizes all the rules related to code generation -
 * including the methods and objects to create, and certain attributes related
 * to those objects.
 *
 * @author Jeff Butler
 */
public interface Rules {

    /**
     * Calculates the class that contains all fields. This class is used as the
     * insert statement parameter, as well as the returned value from the select
     * by primary key method. The actual class depends on how the domain model
     * is generated.
     *
     * @return the type of the class that holds all fields
     */
    FullyQualifiedJavaType calculateAllFieldsClass();

    /**
     * Implements the rule for generating the result map without BLOBs. If
     * either select method is allowed, then generate the result map.
     *
     * @return true if the result map should be generated
     */
    boolean generateBaseResultMap();

    /**
     * Implements the rule for generating the SQL base column list element.
     * Generate the element if any of the select methods are enabled.
     *
     * @return true if the SQL base column list element should be generated
     */
    boolean generateBaseColumnList();

    /**
     * Implements the rule for determining whether to generate a primary key
     * class. If you return false from this method, and the table has primary
     * key columns, then the primary key columns will be added to the base
     * class.
     *
     * @return true if a separate primary key class should be generated
     */
    boolean generatePrimaryKeyClass();

    /**
     * Implements the rule for generating a base record.
     *
     * @return true if the class should be generated
     */
    boolean generateBaseRecordClass();

    /**
     * Implements the rule for generating a Java client.  This rule is
     * only active when a javaClientGenerator configuration has been
     * specified, but the table is designated as "modelOnly".  Do not
     * generate the client if the table is designated as modelOnly.
     *
     * @return true if the Java client should be generated
     */
    boolean generateJavaClient();

    IntrospectedTable getIntrospectedTable();
}
