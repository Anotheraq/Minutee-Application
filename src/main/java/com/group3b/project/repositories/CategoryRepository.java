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
    public List<Category> getCategories(UUID user_id) {
        List<Category>  category;
        String sql = "SELECT * FROM category;";

        try {
            category = jdbcTemplate.query(sql, (rs, rowNum) -> new Category(rs.getObject("category_id", java.util.UUID.class),
                    rs.getString("color"),
                    rs.getString("title")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
        return category;
    }
}
