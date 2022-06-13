package com.group3b.project.repositories;

import com.group3b.project.models.Chart;
import com.group3b.project.models.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Repository
public class ChartRepository implements IChartRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("JdbcBuilder")
    public void setJdbcTemplate(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public JSONObject getTimeActivityOneYear(User user){
        List<Chart> chart;
        JSONObject json = new JSONObject();
        int secInYear = 31_556_926;
        String sql = "SELECT c.title, ROUND(SUM(EXTRACT(EPOCH FROM (time_ended - time_started)))) " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+ user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_started BETWEEN date_trunc('year', current_date)::timestamp and (date_trunc('year', current_date) + interval '1 year' - interval '1 day')::timestamp " +
                "GROUP BY c.title;";
        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getInt("round")));
        }catch(EmptyResultDataAccessException e){
            return json.put("idle", secInYear);
        }

        for(Chart ac: chart){
            System.out.println(ac.getTitle() + " " + ac.getTotalTime());
            secInYear-=ac.getTotalTime();
            json.put(ac.getTitle(), ac.getTotalTime());
        }
        if(secInYear > 0) {
            json.put("idle", secInYear);
        }

        return json;
    }
    public JSONObject getTimeActivityOneMonth(User user){
        List<Chart> chart;
        JSONObject json = new JSONObject();
        int secInMonth = 2_629_743;
        String sql = "SELECT c.title, ROUND(SUM(EXTRACT(EPOCH FROM (time_ended - time_started)))) " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+ user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_started BETWEEN date_trunc('month', current_date)::timestamp and (date_trunc('month', current_date) + interval '1 month' - interval '1 day')::timestamp " +
                "GROUP BY c.title;";
        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getInt("round")));
        }catch(EmptyResultDataAccessException e){
            return json.put("idle", secInMonth);
        }

        for(Chart ac: chart){
            System.out.println(ac.getTitle() + " " + ac.getTotalTime());
            secInMonth-=ac.getTotalTime();
            json.put(ac.getTitle(), ac.getTotalTime());
        }
        if(secInMonth > 0) {
            json.put("idle", secInMonth);
        }

        return json;
    }
    public JSONObject getTimeActivityOneWeek(User user){
        List<Chart> chart;
        JSONObject json = new JSONObject();
        int secInWeek = 604_800;
        String sql = "SELECT c.title, ROUND(SUM(EXTRACT(EPOCH FROM (time_ended - time_started)))) " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+ user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_started BETWEEN date_trunc('week', current_date)::timestamp and (date_trunc('week', current_date) + interval '1 week')::timestamp " +
                "GROUP BY c.title;";
        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getInt("round")));
        }catch(EmptyResultDataAccessException e){
            return json.put("idle", secInWeek);
        }

        for(Chart ac: chart){
            System.out.println(ac.getTitle() + " " + ac.getTotalTime());
            secInWeek-=ac.getTotalTime();
            json.put(ac.getTitle(), ac.getTotalTime());
        }
        if(secInWeek > 0) {
            json.put("idle", secInWeek);
        }
        return json;
    }
    public JSONObject getTimeActivityOneDay(User user){
        List<Chart> chart;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        JSONObject json = new JSONObject();


        String sql = "SELECT c.title, time_ended, time_started " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_ended BETWEEN date_trunc('day', current_date)::timestamp and (date_trunc('day', current_date) + interval '1 day' - interval '1 ms')::timestamp;";


        long secInDay = 86400;
        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getTimestamp("time_started"),
                    rs.getTimestamp("time_ended")));
        }catch(EmptyResultDataAccessException e){
            return json.put("idle", secInDay);
        }

        for(Chart ac: chart){
            cal1.setTime(ac.getTimeStarted());
            cal2.setTime(ac.getTimeEnded());
            if(cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) {
                LocalDate localDate = LocalDate.now();
                ac.setTimeStarted(Timestamp.valueOf(localDate + " 00:00:00"));
            }
            ac.setTotalTime((ac.getTimeEnded().getTime() - ac.getTimeStarted().getTime())/1000);
            secInDay -= ac.getTotalTime();
            json.put(ac.getTitle(), ac.getTotalTime());
        }
        if(secInDay > 0) {
            json.put("idle", secInDay);
        }

        return json;
    }
}
