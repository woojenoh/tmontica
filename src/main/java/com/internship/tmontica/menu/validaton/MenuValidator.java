package com.internship.tmontica.menu.validaton;

import com.internship.tmontica.menu.model.request.MenuReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Date;

@Component
@Slf4j
public class MenuValidator {

    public void validate(MenuReq menuReq, Errors errors){
        // 1. sellPrice
        if(menuReq.getSellPrice() != menuReq.getProductPrice() * (100 - menuReq.getDiscountRate())/100 ){
            errors.rejectValue("sellPrice" , "wrongValue", "sellPrice is wrong");
        }

        if(menuReq.getStartDate()!= null && menuReq.getEndDate()!= null) {
            // 2. startDate - endDate
            if (menuReq.getStartDate().after(menuReq.getEndDate())) {
                errors.rejectValue("startDate", "wrongValue", "startDate is wrong");
                errors.rejectValue("endDate", "wrongValue", "endDate is wrong");
            }

            // 3. startDate , endDate 기간 내가 아닌데 usable = true 인 경우
            Date now = new Date();
            if (menuReq.isUsable() && (menuReq.getStartDate().before(now) || menuReq.getEndDate().after(now))) {
                errors.rejectValue("usable", "wrongValue", "usable is wrong");
            }
        }

        // 4. 이미지 파일
        if(menuReq.getImgFile().isEmpty()){
            errors.rejectValue("imgFile", "wrongValue", "imgFile is wrong");
        }

        if(!menuReq.getImgFile().getContentType().startsWith("image")){
            errors.rejectValue("imgFile", "wrongValue", "imgFile is wrong");
        }

    }
}
