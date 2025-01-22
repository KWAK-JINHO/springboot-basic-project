package com.jinoprac.springboot_prac.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = com.jinoprac.springboot_prac.validator.ForbiddenWordsValidator.class)
public @interface ForbiddenWords {

    String message() default "허용되지 않는 단어가 포함되어 있습니다.";

    Class[] groups() default {};

    Class [] payload() default {};

    String[] words();
}
