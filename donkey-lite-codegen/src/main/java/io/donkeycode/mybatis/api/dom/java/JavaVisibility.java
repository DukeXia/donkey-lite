package io.donkeycode.mybatis.api.dom.java;

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
    PUBLIC("public "),
    PRIVATE("private "),
    PROTECTED("protected "),
    DEFAULT("");

    private String value;
}
