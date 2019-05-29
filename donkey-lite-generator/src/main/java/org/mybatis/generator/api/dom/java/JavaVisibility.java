package org.mybatis.generator.api.dom.java;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Typesafe enum of possible Java visibility settings.
 *
 * @author Jeff Butler
 */
@Getter
@AllArgsConstructor
public enum JavaVisibility {
    PUBLIC("public "), //$NON-NLS-1$
    PRIVATE("private "), //$NON-NLS-1$
    PROTECTED("protected "), //$NON-NLS-1$
    DEFAULT(""); //$NON-NLS-1$

    private String value;
}
