package com.internship.tmontica.menu.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class BindingResultHelper {
    public static void throwCustomInvalidParameterException(BindingResult bindingResult) {

        // 개별 필드 오류 처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            String message = fieldErrors.stream()
                    .map(e -> String.format("Property value [%s] of '%s' is invalid.", e.getRejectedValue(), e.getField()))
                    .collect(Collectors.joining(" && "));
            throw new CustomInvalidParameterException(message);
        }

        // 개별 필드 외의 오류 처리
        List<ObjectError> objectErrors = bindingResult.getAllErrors();
        if (!objectErrors.isEmpty()) {
            String message = objectErrors.stream()
                    .map(e -> String.format("Error in object '%s': %s", e.getObjectName(), e.getDefaultMessage()))
                    .collect(Collectors.joining(" && "));
            throw new CustomInvalidParameterException(message);
        }

        throw new CustomInvalidParameterException("입력값을 확인해 주십시오.");
    }
}
