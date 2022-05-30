package com.group3b.project.repositories;

import com.group3b.project.models.Activity;

import java.util.UUID;

public interface IActivityRepository {
    boolean addActivity(Activity activity);
    Activity getActivities(UUID user_id);
}
