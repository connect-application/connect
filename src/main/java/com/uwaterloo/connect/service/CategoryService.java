package com.uwaterloo.connect.service;

import com.uwaterloo.connect.dto.Leaderboard;

import java.util.List;

public interface CategoryService {
    public List<Leaderboard> getLeaderboard(Integer categoryId, Integer leaderboardTimeType, Integer leaderboardType);
}
