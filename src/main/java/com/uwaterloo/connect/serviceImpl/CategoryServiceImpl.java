package com.uwaterloo.connect.serviceImpl;

import com.uwaterloo.connect.dto.Leaderboard;
import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.PostRepository;
import com.uwaterloo.connect.repository.UserRepository;
import com.uwaterloo.connect.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupServiceImpl groupService;

    @Override
    public List<Leaderboard> getLeaderboard(Integer categoryId, Integer leaderboardTimeType, Integer leaderboardType){
        List<Leaderboard> leaderBoard = new ArrayList<>();
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime time = switch (leaderboardTimeType) {
            case 0 -> timeNow.minusDays(1);
            case 1 -> timeNow.minusWeeks(1);
            case 2 -> timeNow.minusMonths(1);
            case 3 -> timeNow.minusYears(1);
            default -> timeNow;
        };

        List<Integer> userIds;
        if(leaderboardType == 2){
            userIds = userRepository.findUsersByActivitiesFinishedBetweenTimeForCategory(time, timeNow, categoryId);
        }
        else{
            userIds = userRepository.findUsersByActivitiesInProgressBetweenTimeForCategory(time, timeNow, categoryId);
        }

        for(Integer userId: userIds.stream().distinct().collect(Collectors.toList())){
            Leaderboard rank = new Leaderboard();
            User user = userRepository.findUserById(userId);
            if(Objects.nonNull(user)){
                rank.setUser(user);
                List<Integer> acs = groupService.calculateActFinishedForMember(userId, categoryId, leaderboardTimeType,timeNow);
                rank.setActivitiesFinished(acs.get(0));
                rank.setActivitiesInProgress(acs.get(1));
                leaderBoard.add(rank);
            }
        }

        if(leaderboardType == 2){
            Collections.sort(leaderBoard, Comparator.comparingInt(Leaderboard::getActivitiesInProgress).reversed());
        }
        else{
            Collections.sort(leaderBoard, Comparator.comparingInt(Leaderboard::getActivitiesFinished).reversed());
        }
        return leaderBoard;
    }
}
