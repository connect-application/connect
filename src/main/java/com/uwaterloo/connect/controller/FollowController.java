package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Follow;
import com.uwaterloo.connect.repository.FollowRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.uwaterloo.connect.Constants.Constants.ERROR;
import static com.uwaterloo.connect.Constants.Constants.SUCCESS;
import static com.uwaterloo.connect.Constants.FollowEndpointURLs.*;


@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @PostMapping(TOGGLE_FOLLOW)
    public String toggleFollow(@RequestParam("toFollow") Integer toFollowId){
        try{
            Integer userId = userActionAuthenticator.getLoggedUser().getId().intValue();
            String action = "";
            Follow follow = followRepository.findFollow(userId, toFollowId);
            if(Objects.isNull(follow)){
                followRepository.save(new Follow(toFollowId, userId));
                action = ": Followed";
            }
            else{
                followRepository.delete(follow);
                action = ": Unfollowed";
            }
            return SUCCESS+action;
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @GetMapping(GET_FOLLOWERS)
    public List<Follow> getFollowers(@RequestParam("userId") Integer userId){
        try{
            return followRepository.findFollowers(userId);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping(GET_FOLLOWING)
    public List<Follow> getFollowing(@RequestParam("userId") Integer userId){
        try{
            return followRepository.findFollowing(userId);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping(GET_COUNT_FOLLOWERS)
    public String getFollowersCount(@RequestParam("userId") Integer userId){
        try{
            return String.valueOf(followRepository.findCountFollowers(userId));
        }catch (Exception e){
            return ERROR+e;
        }
    }

    @GetMapping(GET_COUNT_FOLLOWING)
    public String getFollowingCount(@RequestParam("userId") Integer userId){
        try{
            return String.valueOf(followRepository.findCountFollowing(userId));
        }catch (Exception e){
            return ERROR+e;
        }
    }
}
