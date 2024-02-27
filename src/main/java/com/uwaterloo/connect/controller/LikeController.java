package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Like;
import com.uwaterloo.connect.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.uwaterloo.connect.Constants.Constants.ERROR;
import static com.uwaterloo.connect.Constants.Constants.SUCCESS;
import static com.uwaterloo.connect.Constants.LikeEndpointURLs.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikeRepository likeRepository;

    @PostMapping(TOGGLE_POST_LIKE)
    public String togglePostLike(@RequestParam("postId") Integer postId, @RequestParam(value = "userId") Integer userId){
        try{
            String action = "";
            Like like = likeRepository.findUserLikeOnPost(postId, userId);
            if(Objects.isNull(like)){
                likeRepository.save(new Like(postId, userId));
                action = ": Liked";
            }
            else{
                likeRepository.delete(like);
                action = ": unliked";
            }
            return SUCCESS+action;
        }
        catch (Exception e){
            return ERROR+e;
        }
    }

    @GetMapping(GET_POST_LIKES)
    public List<Like> getPostLikes(@RequestParam("postId") Integer postId){
        try{
            System.out.println("hit on getPostLikes");
            return likeRepository.findLikesOnPost(postId);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping(GET_COUNT_LIKES)
    public String getPostLikesCount(@RequestParam("postId") Integer postId){
        try{
            return String.valueOf(likeRepository.findLikecountOnPost(postId));
        }catch (Exception e){
            return ERROR+e;
        }
    }

}
