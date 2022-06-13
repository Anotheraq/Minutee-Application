package com.group3b.project.repositories;

import com.group3b.project.models.User;
import org.json.JSONArray;
import org.json.JSONObject;

public interface IChartRepository {
    JSONObject getTimeActivityOneYear(User user);
    JSONObject getTimeActivityOneMonth(User user);
    JSONObject getTimeActivityOneWeek(User user);
    JSONArray getTimeActivityOneDay(User user);
}
