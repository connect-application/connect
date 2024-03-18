package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.ConnectGroups;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/userName")
    public List<User> searchUsers(@RequestParam("userName") String userName){
        return searchService.searchUsers(userName);
    }

    @GetMapping("/groupName")
    public List<ConnectGroups> searchGroups(@RequestParam("groupName") String groupName){
        return searchService.searchGroups(groupName);
    }
}
