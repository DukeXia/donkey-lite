package com.donkeycode.boot;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
@Documented
public @interface LinkedHandler {

    String dependName();

    String getOperateType();
}
