package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    @Query("select a from Like a where a.postId= :postId and a.likedBy = :userId")
    Like findUserLikeOnPost(@Param("postId") Integer postId,@Param("userId") Integer userId );

    @Query("select a from Like a where a.postId= :postId")
    List<Like> findLikesOnPost(@Param("postId") Integer postId);

    @Query("select count (a) from Like a where a.postId= :postId")
    Integer findLikecountOnPost(@Param("postId") Integer postId);
}
