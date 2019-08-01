package com.internship.tmontica.cart.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import java.util.Collection;

@Component
public class CartValidator implements Validator {

    private SpringValidatorAdapter validator;

    public CartValidator() {
        this.validator = new SpringValidatorAdapter(
                Validation.buildDefaultValidatorFactory().getValidator()
        );
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return true; // 모든 타입에 대해 true 를 반환
    }

    @Override
    public void validate(Object o, Errors errors) {
        //Collection인 경우에만 Collection 내의 Object들에 대한 Validation을 진행하고,
        //그렇지 않은 일반 클래스인 경우엔 원래 Validation을 한다
        if(o instanceof Collection){
            Collection collection = (Collection) o;

            for (Object object : collection) {
                validator.validate(object, errors);
            }
        } else {
            validator.validate(o, errors);
        }
    }
}
