package com.group3b.project.repositories;

import com.group3b.project.models.Chart;
import com.group3b.project.models.Activity;

import com.group3b.project.models.User;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.sql.DataSource;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
@Repository
public class ActivityRepository implements IActivityRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("JdbcBuilder")
    public void setJdbcTemplate(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public boolean addActivity(Activity activity) {
        String sql = "INSERT INTO public.activity (description, time_started, time_ended, user_id, category_id) VALUES " +
                "('"+activity.getDescription()+"','"+activity.getTimeStarted()+"','"+activity.getTimeEnded()+"','"+activity.getUser_id()+"','"+activity.getCategory_id()+"');";
        return jdbcTemplate.update(sql) > 0;
    }

    @Override
    public List<Activity> getActivities(UUID user_id) {

        List<Activity> activity;
        String sql = "select * from activity where user_id = '" + user_id + "';";

        try {
            activity = jdbcTemplate.query(sql, (rs, rowNum) -> new Activity(rs.getInt("activity_id"),
                    rs.getObject("category_id", java.util.UUID.class),
                    rs.getObject("user_id", java.util.UUID.class),
                    rs.getString("description"),
                    rs.getTimestamp("time_started"),
                    rs.getTimestamp("time_ended")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
        return activity;
    }

    @Override
    public List<Activity> getUnifnishedActivities(UUID user_id) {

        List<Activity> activity;
        String sql = "SELECT * FROM activity WHERE user_id = '" + user_id + "' and time_ended = '1111-11-11 11:11:11';";

        try{
            activity = jdbcTemplate.query(sql, (rs, rowNum) -> new Activity(rs.getInt("activity_id"),
                    rs.getObject("category_id", java.util.UUID.class),
                    rs.getObject("user_id", java.util.UUID.class),
                    rs.getString("description"),
                    rs.getTimestamp("time_started"),
                    rs.getTimestamp("time_ended")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
        return activity;
    }

    public boolean updateActivity(UUID user_id, int activity_id){
        String sql = "UPDATE activity set time_ended = current_timestamp WHERE user_id = '"+user_id+"' and activity_id = '"+activity_id+"';";
        return jdbcTemplate.update(sql) > 0;
    }

}
