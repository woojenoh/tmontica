package com.internship.tmontica.banner;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BannerDao {

    @Select("SELECT * FROM banners WHERE id = #{id}")
    Banner getBannerById(int id);

    @Select("SELECT * FROM banners")
    List<Banner> getAllBanner();

    @Select("SELECT * FROM banners WHERE use_page = #{usePage} AND usable = 1")
    List<Banner> getBannersByUsePage(String usePage);

}
