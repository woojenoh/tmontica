package com.internship.tmontica.banner;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BannerDao {
    //    private int id;
    //    private String usePage;
    //    private boolean usable;
    //    private String img;
    //    private String link;
    //    private Date start_date;
    //    private Date end_date;
    //    private String creatorId;
    //    private int number;

    @Insert("INSERT INTO banners VALUES(#{usePage}, #{usable}, #{img}, #{link}, #{startDate}, #{endDate}, #{creatorId}, #{number})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    int addBanner(Banner banner);

    @Select("SELECT * FROM banners WHERE id = #{id}")
    Banner getBannerById(int id);

    @Select("SELECT * FROM banners")
    List<Banner> getAllBanner();

    @Update("UPDATE banners SET use_page = #{usePage}, img = #{img}, link = #{link}, " +
            "start_date = #{startDate}, end_Date = #{endDate}, number = #{number} " +
            "WHERE id = #{id}")
    void updateBanner(int id);

    @Delete("DELETE FROM banners WHERE id = #{id}")
    void deleteBanner(int id);
}
