package com.internship.tmontica.banner.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerReq {

    @NotEmpty(message = "usePage는 필수입니다.")
    private String usePage;

    private boolean usable;

    @NotNull
    private MultipartFile imgFile;

    @NotEmpty(message = "link는 필수입니다.")
    private String link;

    @NotEmpty(message = "시작일은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Date start_date;

    @NotEmpty(message = "종료일은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Date end_date;

    @NotEmpty(message = "순서는 필수입니다.")
    @Min(1)
    private int number;

}
