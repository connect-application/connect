package com.uwaterloo.connect.service;

import java.util.List;

import com.uwaterloo.connect.model.Follow;

public interface FollowService {
    public List<Follow> getFollowing(Integer userId);
}
