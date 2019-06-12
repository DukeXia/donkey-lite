package org.donkeycode.codegen.internal.rules;

import org.donkeycode.codegen.api.IntrospectedTable;

/**
 * This class encapsulates all the code generation rules for a table using the
 * flat model.
 *
 * @author Jeff Butler
 *
 */
public class FlatModelRules extends BaseRules {

    /**
     * Instantiates a new flat model rules.
     *
     * @param introspectedTable
     *            the introspected table
     */
    public FlatModelRules(IntrospectedTable introspectedTable) {
        super(introspectedTable);
    }

    /**
     * We never generate a primary key in the flat model.
     *
     * @return true if the primary key should be generated
     */
    @Override
    public boolean generatePrimaryKeyClass() {
        return false;
    }

    /**
     * We always generate a base record in the flat model.
     *
     * @return true if the class should be generated
     */
    @Override
    public boolean generateBaseRecordClass() {
        return true;
    }

  
}
