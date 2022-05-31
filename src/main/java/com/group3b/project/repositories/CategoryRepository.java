package com.group3b.project.repositories;

import com.group3b.project.models.Activity;
import com.group3b.project.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;
@Repository
public class CategoryRepository implements ICategoryRepository{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("JdbcBuilder")
    public void setJdbcTemplate(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO public.category (category_id,title) VALUES ('" + category.getCategory_id() + "','" + category.getTitle() +"');";
        String sql2 = "INSERT INTO public.user_category_color(color_id, user_id, category_id) VALUES('" + category.getColor_id() + "','"+category.getUser_id()+"','"+category.getCategory_id()+"');";
        int firstQuery = jdbcTemplate.update(sql);
        int secondQuery = jdbcTemplate.update(sql2);
        return firstQuery > 0 && secondQuery > 0;
    }

    @Override
    public List<Category> getCategories(UUID user_id) {
        List<Category>  category;
        String sql = "SELECT ucc.category_id, color_id, user_id, title FROM category c join user_category_color ucc on c.category_id = ucc.category_id where user_id = " + user_id + ";";

        try {
            category = jdbcTemplate.query(sql, (rs, rowNum) -> new Category(rs.getObject("category_id", java.util.UUID.class),
                    rs.getObject("user_id", java.util.UUID.class),
                    rs.getInt("color_id"),
                    rs.getString("title")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
        return category;
    }
}
