package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.ConnectGroups;
import com.uwaterloo.connect.model.User;

import java.util.List;

public interface SearchService {
    List<User> searchUsers(String userName);
    List<ConnectGroups> searchGroups(String groupName);
}
