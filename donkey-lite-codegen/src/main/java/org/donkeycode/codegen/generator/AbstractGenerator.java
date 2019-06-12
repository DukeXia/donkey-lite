package org.donkeycode.codegen.generator;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.donkeycode.codegen.api.IntrospectedTable;
import org.donkeycode.codegen.api.ProgressCallback;
import org.donkeycode.codegen.config.Context;

@Getter
@Setter
public abstract class AbstractGenerator {
    protected Context context;
    protected IntrospectedTable introspectedTable;
    protected List<String> warnings;
    protected ProgressCallback progressCallback;

    public AbstractGenerator() {
    }
}
