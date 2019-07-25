package com.internship.tmontica.menu.validaton;

import com.internship.tmontica.menu.model.request.MenuReq;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MenuReqValidator implements ConstraintValidator<ValidMenuReq, MenuReq> {

    @Override
    public boolean isValid(MenuReq menuReq, ConstraintValidatorContext constraintValidatorContext) {
        return (menuReq.getSellPrice() == menuReq.getProductPrice() * (100 - menuReq.getDiscountRate())/100) ? true : false;
    }

    @Override
    public void initialize(ValidMenuReq constraintAnnotation) {

    }
}
