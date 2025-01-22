package com.jinoprac.springboot_prac.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ForbiddenWordsValidator implements ConstraintValidator<ForbiddenWords, String> {

    private String[] forbiddenWords;

    @Override
    public void initialize(ForbiddenWords constraintAnnotation) {
        this.forbiddenWords = constraintAnnotation.words();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // 대소문자 구분 없이, 공백 제거 후 검사
        String normalizedValue = value.toLowerCase().replaceAll("\\s+", "");

        for (String word : forbiddenWords) {
            String normalizedWord = word.toLowerCase().replaceAll("\\s+", "");
            if (normalizedValue.contains(normalizedWord)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(word + "는 금지된 단어입니다.").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
