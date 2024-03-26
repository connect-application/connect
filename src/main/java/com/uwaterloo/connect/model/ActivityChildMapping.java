package com.uwaterloo.connect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "activity_mapping")
public class ActivityChildMapping {

    @Id
    Integer activityId;

    Integer parentActivityId;

    public void setActivityId(Integer activityId){
        this.activityId = activityId;
    }

    public Integer getActivityId(){
        return activityId;
    }

    public void setParentActivityId(Integer parentId){
        this.parentActivityId = parentId;
    }
    public Integer getParentActivityId(){
        return parentActivityId;
    }
    
}
