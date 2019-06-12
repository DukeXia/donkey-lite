package org.donkeycode.codegen.generator.mybatis3;

import java.util.ArrayList;
import java.util.List;

import org.donkeycode.codegen.api.IntrospectedColumn;

/**
 * Couple of little utility methods to make dealing with generated always
 * columns easier.  If a column is GENERATED ALWAYS, it should not
 * be references on an insert or update method.
 *
 * <p>If a column is identity, it should not be referenced on an insert method.
 *
 * <p>TODO - Replace this with Lambdas when we get to Java 8
 *
 * @author Jeff Butler
 *
 */
public class ListUtilities {

    public static List<IntrospectedColumn> removeGeneratedAlwaysColumns(List<IntrospectedColumn> columns) {
        List<IntrospectedColumn> filteredList = new ArrayList<IntrospectedColumn>();
        for (IntrospectedColumn ic : columns) {
            if (!ic.isGeneratedAlways()) {
                filteredList.add(ic);
            }
        }
        return filteredList;
    }

    public static List<IntrospectedColumn> removeIdentityAndGeneratedAlwaysColumns(List<IntrospectedColumn> columns) {
        List<IntrospectedColumn> filteredList = new ArrayList<IntrospectedColumn>();
        for (IntrospectedColumn ic : columns) {
            if (!ic.isGeneratedAlways() && !ic.isIdentity()) {
                filteredList.add(ic);
            }
        }
        return filteredList;
    }
}
