package com.donkeycode.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.donkeycode.core.utils.JSONUtils;
import com.donkeycode.core.utils.StringUtils;

public class CheckJsonClass implements ConstraintValidator<CheckJson, String> {

    @Override
    public void initialize(CheckJson constraintAnnotation) {
        constraintAnnotation.forbidden();
    }

    @Override
    public boolean isValid(String json, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNoneBlank(json) && JSONUtils.isJson(json)) {
            return true;
        }
        return false;
    }
}
 