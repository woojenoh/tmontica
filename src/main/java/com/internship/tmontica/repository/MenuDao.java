package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Menu;
import com.internship.tmontica.dto.Option;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface MenuDao {

    @Insert("INSERT INTO menus(name_ko, name_eng, product_price, category_ko, category_eng, monthly_menu, usable," +
                                "img, description, sell_price, discount_rate, created_date, creator_id, stock)"+
            "VALUES (#{nameKo},#{nameEng}, #{productPrice}, #{categoryKo}, #{categoryEng}, #{monthlyMenu} , #{usable} ,"+
                    "#{img}, #{description}, #{sellPrice}, #{discountRate}, #{createdDate},  #{creatorId}, #{stock})")
    int addMenu(Menu menu);

    @Insert("INSERT INTO menu_options(menu_id, option_id) VALUES (#{menuId}, #{optionId})")
    int addMenuOption(@Param("menuId")int menuId, @Param("optionId")int optionId);

    @Select("SELECT * FROM menus WHERE id = #{id}")
    Menu getMenuById(int id);

    @Select("SELECT * FROM menus")
    List<Menu> getAllMenus();

    @Select("SELECT * FROM menus WHERE category_eng = #{category}")
    List<Menu> getMenusByCategory(String category);

    @Select("SELECT * FROM menus WHERE monthly_menu = 1 order by created_date desc Limit 4")
    List<Menu> getMonthlyMenus();

    @Update("UPDATE menus" +
            "SET name_ko = #{nameKo}, name_eng = #{nameEng}, product_price = #{productPrice}," +
            "category_ko = #{categoryKo}, category_eng = #{categoryEng}, monthly_menu = #{monthlyMenu} , usable = #{usable} ," +
            "img = #{img}, description = #{description}, sell_price = #{sellPrice}," +
            "discount_rate = #{discountRate}, stock = #{stock}, updated_date = #{updatedDate}," +
            "updater_id = #{updaterId}")
    void updateMenu(Menu menu);

    @Delete("DELETE FROM menus WHERE id = #{id}")
    void deleteMenu(int id);

//    List<Option> getOptionsById(int id);

//    @Select("SELECT * FROM menus")
//    @Results(value = {
//            @Result(property="id", column="id"),
//            @Result(property="nameEng", column="name_eng"),
//            @Result(property="productPrice", column="product_price"),
//            @Result(property="categoryKo", column="categoryKo"),
//            @Result(property="categoryEng", column="category_eng"),
//            @Result(property="monthlyMenu", column="monthly_menu"),
//            @Result(property="usable", column="usable"),
//            @Result(property="img", column="img"),
//            @Result(property="description", column="description"),
//            @Result(property="discountRate", column="discount_rate"),
//            @Result(property="createdDate", column="created_date"),
//            @Result(property="updatedDate", column="updated_date"),
//            @Result(property="creatorId", column="creator_id"),
//            @Result(property="updaterId", column="updater_id"),
//            @Result(property="stock", column="stock"),
//            @Result(property="nameKo", column="name_ko"),
//            @Result(property="options", javaType=List.class, column="id",
//                    many=@Many(select="getOptionsById"))
//    })
//    List<Menu> getAllMenus();


    @Select("SELECT options.name, options.price, options.type, options.id FROM options INNER JOIN menu_options " +
            "ON menu_options.menu_id = #{id} WHERE menu_options.option_id = options.id")
    List<Option> getOptionsById(int id);
}
