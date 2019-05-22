package com.donkeycode.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.donkeycode.core.utils.StringJsonUtils;
import com.donkeycode.core.utils.StringSuperUtils;

public class CheckJsonClass implements ConstraintValidator<CheckJson, String> {

    public void initialize(CheckJson constraintAnnotation) {
        constraintAnnotation.forbidden();
    }

    public boolean isValid(String json, ConstraintValidatorContext constraintValidatorContext) {
        if (StringSuperUtils.isNoneBlank(json) && StringJsonUtils.isGoodJson(json)) {
            return true;
        }
        return false;
    }
}
 