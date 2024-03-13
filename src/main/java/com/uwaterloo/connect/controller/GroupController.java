package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.dto.Leaderboard;
import com.uwaterloo.connect.model.ConnectGroups;
import com.uwaterloo.connect.model.GroupMembers;
import com.uwaterloo.connect.repository.GroupMemberRepository;
import com.uwaterloo.connect.repository.GroupRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.uwaterloo.connect.Constants.Constants.*;
import static com.uwaterloo.connect.Constants.GroupEndpointURLs.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMemberRepository groupMemberRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Autowired
    GroupService groupService;

    @PostMapping(CREATE_GROUP)
    public String createGroup(@RequestParam("groupName") String groupName, @RequestParam(value = "categoryId") Integer categoryId){
        try{
            ConnectGroups connectGroup = new ConnectGroups();
            connectGroup.setGroupName(groupName);
            connectGroup.setGroupOwner(userActionAuthenticator.getLoggedUser().getId().intValue());
            connectGroup.setCategoryId(categoryId);
            //groupCode
            String groupCode = createGroupCode();
            connectGroup.setGroupCode(groupCode);
            groupRepository.save(connectGroup);
            //Add owner to group
            createGroupMemberShip(connectGroup, connectGroup.getGroupCode());
            return SUCCESS+groupCode;
        }catch (Exception e){
            return ERROR+e;
        }
    }

    private String createGroupCode(){
        final String CHARACTERS = GROUP_CODE_SOURCE;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(GROUP_CODE_LENGTH);
        for (int i = 0; i < GROUP_CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    @PostMapping(UPDATE_GROUP_OWNER)
    public String updateGroupOwner(@RequestParam("groupId") Integer groupId, @RequestParam(value = "newGroupOwner") Integer newGroupOwner){
        try{
            ConnectGroups group = groupRepository.findConnectGroupsByGroupId(groupId);
            GroupMembers membership = groupMemberRepository.isUserinGroup(groupId, newGroupOwner);
            if(Objects.nonNull(group) && group.getGroupOwner().equals(userActionAuthenticator.getLoggedUser().getId().intValue()) && Objects.nonNull(membership)){
                group.setGroupOwner(newGroupOwner);
                groupRepository.save(group);
                return SUCCESS;
            }
            else if(Objects.isNull(membership)){
                return ERROR + " New Owner Not in group";
            }
            else {
                return ERROR + " Not Group Owner";
            }
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @PostMapping(UPDATE_GROUP_CATEGORY)
    public String updateGroupCategory(@RequestParam("groupId") Integer groupId, @RequestParam(value = "newCategory") Integer newCategoryId){
        try{
            ConnectGroups group = groupRepository.findConnectGroupsByGroupId(groupId);
            if(Objects.nonNull(group) && group.getGroupOwner().equals(userActionAuthenticator.getLoggedUser().getId().intValue())){
                group.setCategoryId(newCategoryId);
                groupRepository.save(group);
                return SUCCESS;
            }
            else{
                return ERROR + " Not Group Owner";
            }
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @PostMapping(DELETE_GROUP)
    public String deleteGroup(@RequestParam("groupId") Integer groupId){
        try{
            ConnectGroups group = groupRepository.findConnectGroupsByGroupId(groupId);
            if(Objects.nonNull(group) && group.getGroupOwner().equals(userActionAuthenticator.getLoggedUser().getId().intValue())){
                groupRepository.delete(group);
                return SUCCESS;
            }
            else{
                return ERROR + " Not Group Owner";
            }
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @PostMapping(JOIN_GROUP)
    public String joinGroup(@RequestParam("groupId") Integer groupId, @RequestParam(value = "code") String code){
        try{
            ConnectGroups group = groupRepository.findConnectGroupsByGroupId(groupId);
            return createGroupMemberShip(group, code);
        }catch (Exception e){
            return ERROR+e;
        }
    }

    public String createGroupMemberShip(ConnectGroups group, String code){
        try{
            if(Objects.nonNull(group) && group.getGroupCode().equals(code)){
                GroupMembers member = new GroupMembers();
                member.setGroupId(group.getGroupId());
                member.setUserId(userActionAuthenticator.getLoggedUser().getId().intValue());
                groupMemberRepository.save(member);
                return SUCCESS;
            }
            else{
                return ERROR+" Invalid code";
            }
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @PostMapping(EXIT_GROUP)
    public String exitGroup(@RequestParam("groupId") Integer groupId){
        try{
            GroupMembers membership = groupMemberRepository.isUserinGroup(groupId, userActionAuthenticator.getLoggedUser().getId().intValue());
            if(Objects.nonNull(membership) && !groupRepository.findGroupOwnerByGroupId(groupId).equals(userActionAuthenticator.getLoggedUser().getId().intValue())){
                groupMemberRepository.delete(membership);
                return SUCCESS;
            }
            else if(Objects.isNull(membership)){
                return ERROR + "Not a member";
            }
            else{
                return ERROR+" groupOwner cannot exit. Please change ownership";
            }

        }catch (Exception e){
            return ERROR+e;
        }
    }

    @GetMapping(GET_LEADERBOARD)
    public List<Leaderboard> getLeaderboard(@RequestParam("groupId") Integer groupId, @RequestParam("leaderboardTimeType") Integer leaderboardTimeType, @RequestParam("leaderboardType") Integer leaderboardType){
        try{
            return groupService.getLeaderboard(groupId, leaderboardTimeType, leaderboardType);
        }catch (Exception e){
            System.out.println(ERROR+e);
            return null;
        }
    }

}
