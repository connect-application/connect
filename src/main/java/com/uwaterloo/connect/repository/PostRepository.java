package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(Integer userId);

    List<Post> findByUserIdAndIsPublic(Integer userId, Boolean isPublic);

    @Query("select a from Post a where a.userId in  :toFollowId and a.isPublic = true")
    List<Post> findFollowingPosts(@Param("toFollowId") List<Integer> userIds);
}