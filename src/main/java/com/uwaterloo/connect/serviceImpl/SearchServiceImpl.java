package com.uwaterloo.connect.serviceImpl;

import com.uwaterloo.connect.model.ConnectGroups;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.GroupRepository;
import com.uwaterloo.connect.repository.UserRepository;
import com.uwaterloo.connect.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;


    @Override
    public List<User> searchUsers(String userName) {
        return userRepository.findUsersByUserName(userName);
    }

    @Override
    public List<ConnectGroups> searchGroups(String groupName) {
        return groupRepository.findGroupsByGroupName(groupName);
    }
}
