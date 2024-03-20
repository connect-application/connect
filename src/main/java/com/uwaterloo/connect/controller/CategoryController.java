package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.dto.Leaderboard;
import com.uwaterloo.connect.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.uwaterloo.connect.Constants.Constants.ERROR;
import static com.uwaterloo.connect.Constants.GroupEndpointURLs.GET_LEADERBOARD;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(GET_LEADERBOARD)
    public List<Leaderboard> getLeaderboard(@RequestParam("categoryId") Integer categoryId, @RequestParam("leaderboardTimeType") Integer leaderboardTimeType, @RequestParam("leaderboardType") Integer leaderboardType){
        try{
            return categoryService.getLeaderboard(categoryId, leaderboardTimeType, leaderboardType);
        }catch (Exception e){
            System.out.println(ERROR+e);
            return null;
        }
    }
}
