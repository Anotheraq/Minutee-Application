package com.group3b.project.repositories;

import com.group3b.project.models.Chart;
import com.group3b.project.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Repository
public class ChartRepository implements IChartRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("JdbcBuilder")
    public void setJdbcTemplate(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public HashMap<String, Double> getTimeActivityOneYear(User user){
        List<Chart> chart;
        HashMap<String, Double> json = new HashMap<>();
        Long secInYear = 31_556_926L;
        Long tempYear = 31_556_926L;
        String sql = "SELECT c.title, ROUND(SUM(EXTRACT(EPOCH FROM (time_ended - time_started)))) " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+ user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_started BETWEEN date_trunc('year', current_date)::timestamp and (date_trunc('year', current_date) + interval '1 year' - interval '1 day')::timestamp " +
                "GROUP BY c.title;";
        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getInt("round")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }

        for(Chart ac: chart){
            tempYear-=ac.getTotalTime();
            json.put(ac.getTitle(), (double) round((double)(100*ac.getTotalTime())/secInYear, 2));
        }
        if(secInYear > 0) {
            json.put("idle", (double) round((double)(100*tempYear)/secInYear, 2));
        }

        return json;
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public HashMap<String, Double> getTimeActivityOneMonth(User user){
        List<Chart> chart;
        HashMap<String, Double> json = new HashMap<>();

        Long secInMonth = 2_629_743L;
        Long tempMonth = 2_629_743L;
        String sql = "SELECT c.title, ROUND(SUM(EXTRACT(EPOCH FROM (time_ended - time_started)))) " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+ user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_started BETWEEN date_trunc('month', current_date)::timestamp and (date_trunc('month', current_date) + interval '1 month' - interval '1 day')::timestamp " +
                "GROUP BY c.title;";
        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getInt("round")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }

        for(Chart ac: chart){
            tempMonth-=ac.getTotalTime();
            json.put(ac.getTitle(), (double) round((double)(100*ac.getTotalTime())/secInMonth, 2));
        }
        if(secInMonth > 0) {
            json.put("idle", (double) round((double)(100*tempMonth)/secInMonth, 2));
        }

        return json;
    }
    public HashMap<String, Double> getTimeActivityOneWeek(User user){
        List<Chart> chart;
        HashMap<String, Double> jsonMap = new HashMap<>();
        Long secInWeek = 604_800L;
        Long tempTime = 604_800L;
        String sql = "SELECT c.title, ROUND(SUM(EXTRACT(EPOCH FROM (time_ended - time_started)))) " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+ user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_started BETWEEN date_trunc('week', current_date)::timestamp and (date_trunc('week', current_date) + interval '1 week')::timestamp " +
                "GROUP BY c.title;";
        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getInt("round")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }

        System.out.println(chart.get(0).getTitle());
        for(Chart ac: chart){
            System.out.println(ac.getTitle() + " " + ac.getTotalTime());
            tempTime-=ac.getTotalTime();
            jsonMap.put(ac.getTitle(), (double) round((double)(100*ac.getTotalTime())/secInWeek,2));
        }
        if(tempTime > 0) {
            jsonMap.put("idle", (double) round((double)(100*tempTime)/secInWeek, 2));
        }
        return jsonMap;
    }


    public JSONArray getTimeActivityOneDay(User user){
        List<Chart> chart;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        JSONObject json = new JSONObject();
        JSONObject json1;
        JSONArray jsonArray = new JSONArray();

        String sql = "SELECT c.title, time_ended, time_started " +
                "FROM activity a join category c on a.category_id = c.category_id " +
                "where a.user_id = '"+user.getId()+"' and time_ended != '1111-11-11 11:11:11' " +
                "and time_ended BETWEEN date_trunc('day', current_date)::timestamp and (date_trunc('day', current_date) + interval '1 day' - interval '1 ms')::timestamp;";


        try{
            chart = jdbcTemplate.query(sql, (rs, rowNum) -> new Chart(rs.getString("title"),
                    rs.getTimestamp("time_started"),
                    rs.getTimestamp("time_ended")));
        }catch(EmptyResultDataAccessException e){
            return null;
        }

        for(Chart ac: chart){
            json1 = new JSONObject();
            cal1.setTime(ac.getTimeStarted());
            cal2.setTime(ac.getTimeEnded());
            if(cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) {
                LocalDate localDate = LocalDate.now();
                ac.setTimeStarted(Timestamp.valueOf(localDate + " 00:00:00"));
                cal1.setTime(ac.getTimeStarted());
            }
            ac.setTotalTime((ac.getTimeEnded().getTime() - ac.getTimeStarted().getTime())/1000);
            json1.put("ClassName", ac.getTitle());
            json1.put("start", cal1.get(Calendar.HOUR_OF_DAY));
            json1.put("end", cal2.get(Calendar.HOUR_OF_DAY));
            jsonArray.put(json1);
        }

        return jsonArray;
    }
}
