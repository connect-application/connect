package com.uwaterloo.connect.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.uwaterloo.connect.model.Follow;
import com.uwaterloo.connect.repository.FollowRepository;
import com.uwaterloo.connect.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowRepository followRepository;

      public List<Follow> getFollowing(@RequestParam("userId") Integer userId){
        try{
            return followRepository.findFollowing(userId);
        }catch (Exception e){
            return null;
        }
    }


}