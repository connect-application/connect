package com.uwaterloo.connect.serviceImpl;

import com.uwaterloo.connect.dto.Leaderboard;
import com.uwaterloo.connect.model.ConnectGroups;
import com.uwaterloo.connect.model.GroupMembers;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.ActivityRepository;
import com.uwaterloo.connect.repository.GroupMemberRepository;
import com.uwaterloo.connect.repository.GroupRepository;
import com.uwaterloo.connect.repository.UserRepository;
import com.uwaterloo.connect.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMemberRepository memberRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Leaderboard> getLeaderboard(Integer groupId, Integer leaderboardType){
        List<Leaderboard> leaderBoard = new ArrayList<>();
        ConnectGroups group = groupRepository.findConnectGroupsByGroupId(groupId);
        List<GroupMembers> groupMembers = memberRepository.findGroupMembersByGroupId(groupId);
        LocalDateTime timeNow = LocalDateTime.now();
        for(GroupMembers member: groupMembers){
            Leaderboard item = new Leaderboard();
            User user = userRepository.findUserById(member.getUserId());
            if(Objects.nonNull(user)){
                item.setUser(user);
                List<Integer> acs = calculateActFinishedForMember(member.getUserId(), group.getCategoryId(), leaderboardType, timeNow);
                item.setActivitiesFinished(acs.get(0));
                item.setActivitiesInProgress(acs.get(1));
                leaderBoard.add(item);
            }
        }
        Collections.sort(leaderBoard, Comparator.comparingInt(Leaderboard::getActivitiesFinished).reversed());
        return leaderBoard;
    }

    public List<Integer> calculateActFinishedForMember(Integer userId, Integer categoryId, Integer leaderboardType, LocalDateTime timeNow){

        List<Integer> activities = new ArrayList<>();
        LocalDateTime time;
        switch (leaderboardType){
            case 0: time = timeNow.minusDays(1);
                break;
            case 1: time = timeNow.minusWeeks(1);
                break;
            case 2: time = timeNow.minusMonths(1);
                break;
            case 3: time = timeNow.minusYears(1);
                break;
            default: return new ArrayList<>(Arrays.asList(0,0));
        }
        activities.add(activityRepository.findActivitiesFinishedForUser(userId, time, timeNow));
        activities.add(activityRepository.findActivitiesInProgressForUser(userId, time, timeNow));
        return activities;
    }
}
