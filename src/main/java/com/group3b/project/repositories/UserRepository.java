package com.group3b.project.repositories;

import com.group3b.project.ProjectApplication;
import com.group3b.project.models.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository implements IUserRepository{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("JdbcBuilder")
    public void setJdbcTemplate(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO public.user (email, password) VALUES ('" + user.getEmail()+"', '"+user.getPassword()+"');";
        return jdbcTemplate.update(sql) > 0;
    }

    @Override
    public User getUser(String email) {
        User user;
        String sql = "SELECT user_id, email, password FROM public.user WHERE email = '" + email + "';";
        try {
            user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(rs.getInt("user_id"),
                                                                            rs.getString("email"),
                                                                            rs.getString("password")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
        return user;
    }
}
