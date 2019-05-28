package com.donkeycode.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.donkeycode.core.utils.JsonUtils;
import com.donkeycode.core.utils.StringEncaseUtils;

public class CheckJsonClass implements ConstraintValidator<CheckJson, String> {

    @Override
    public void initialize(CheckJson constraintAnnotation) {
        constraintAnnotation.forbidden();
    }

    @Override
    public boolean isValid(String json, ConstraintValidatorContext constraintValidatorContext) {
        if (StringEncaseUtils.isNoneBlank(json) && JsonUtils.isJson(json)) {
            return true;
        }
        return false;
    }
}
 