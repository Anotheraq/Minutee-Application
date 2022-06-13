package com.group3b.project.repositories;

import com.group3b.project.models.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface IChartRepository {
    HashMap<String, Double> getTimeActivityOneYear(User user);
    HashMap<String, Double> getTimeActivityOneMonth(User user);
    HashMap<String, Double> getTimeActivityOneWeek(User user);
    JSONArray getTimeActivityOneDay(User user);
}
