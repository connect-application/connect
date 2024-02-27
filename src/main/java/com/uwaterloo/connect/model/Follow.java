package com.uwaterloo.connect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "follow")
@IdClass(FollowId.class)
public class Follow {
    @Id
    private Integer userId;
    @Id
    private Integer followedBy;

    public Follow(Integer userId, Integer followedBy) {
        this.userId = userId;
        this.followedBy = followedBy;
    }

    public Follow() {

    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFollowedBy(Integer followedBy) {
        this.followedBy = followedBy;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getFollowedBy() {
        return followedBy;
    }
}
