package com.uwaterloo.connect.service;

import com.uwaterloo.connect.dto.Leaderboard;

import java.util.List;

public interface GroupService {
    public List<Leaderboard> getLeaderboard(Integer groupId, Integer leaderboardType);
}
