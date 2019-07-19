package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.Option;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface MenuDao {
//    int addMenu(Menu menu);
//    int addMenuOption(@Param("menuId")int menuId, @Param("optionId")int optionId);
//    Menu getMenuById(int id);
//    List<Menu> getAllMenus();
//    List<Menu> getMenusByCategory(String category);
//    List<Menu> getMonthlyMenus();
//    void updateMenu(Menu menu);
//    void deleteMenu(int id);

//    List<Option> getOptionsById(int id);

    /*
     *  one to many Select.
     */
//    @Select("SELECT master.nama, master.usia FROM test.master WHERE master.nama = #{nama}")
//    @Results(value = {
//            @Result(property="nama", column="nama"),
//            @Result(property="usia", column="usia"),
//            @Result(property="contohs", javaType=List.class, column="nama",
//                    many=@Many(select="getContohs"))
//    })
//    Master selectUsingAnnotations(String nama);
//
//    @Select("SELECT contoh.id, contoh.nama, contoh.alamat FROM test.contoh WHERE contoh.nama = #{nama}")
//    List<Contoh> getContohs(String nama);

    @Select("SELECT * FROM menus")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="nameEng", column="name_eng"),
            @Result(property="productPrice", column="product_price"),
            @Result(property="categoryKo", column="categoryKo"),
            @Result(property="categoryEng", column="category_eng"),
            @Result(property="monthlyMenu", column="monthly_menu"),
            @Result(property="usable", column="usable"),
            @Result(property="img", column="img"),
            @Result(property="description", column="description"),
            @Result(property="discountRate", column="discount_rate"),
            @Result(property="createdDate", column="created_date"),
            @Result(property="updatedDate", column="updated_date"),
            @Result(property="creatorId", column="creator_id"),
            @Result(property="updaterId", column="updater_id"),
            @Result(property="stock", column="stock"),
            @Result(property="nameKo", column="name_ko"),
            @Result(property="options", javaType=List.class, column="id",
                    many=@Many(select="getOptionsById"))
    })
    List<Menu> getAllMenus();


    @Select("SELECT options.name, options.price, options.type, options.id FROM options INNER JOIN menu_options " +
            "ON menu_options.menu_id = #{id} WHERE menu_options.option_id = options.id")
    List<Option> getOptionsById(int id);
}
