package com.sxxxxx.message.center.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public final class ValidateUtil {

    private ValidateUtil() {

    }

    /**
     * 校验实体类
     */
    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> constraintViolations = SingletonClassInstance.instance.validate(t);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getMessage()).append(";");
            }

            throw new ValidationException(validateError.toString());
        }
    }

    /**
     * 通过组来校验实体类
     */
    public static <T> void validate(T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations =  SingletonClassInstance.instance.validate(t, groups);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getMessage()).append(";");
            }

            throw new ValidationException(validateError.toString());
        }
    }


    /**
     * 静态内部类
     **/
    private static class SingletonClassInstance {
        private static final Validator instance = Validation.buildDefaultValidatorFactory().getValidator();
    }

}
