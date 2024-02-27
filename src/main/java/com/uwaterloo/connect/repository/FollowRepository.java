package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    @Query("select a from Follow a where a.userId = :toFollowId and a.followedBy = :userId")
    Follow findFollow(@Param("userId") Integer userId, @Param("toFollowId") Integer toFollowId);

    @Query("select a from Follow a where a.userId = :userId")
    List<Follow> findFollowers(@Param("userId") Integer userId);

    @Query("select a from Follow a where a.followedBy = :userId")
    List<Follow> findFollowing(@Param("userId") Integer userId);

    @Query("select count (a) from Follow a where a.userId = :userId")
    Integer findCountFollowers(@Param("userId") Integer userId);

    @Query("select count (a) from Follow a where a.followedBy = :userId")
    Integer findCountFollowing(@Param("userId") Integer userId);
}
