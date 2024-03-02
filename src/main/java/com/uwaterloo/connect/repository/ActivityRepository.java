package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("select a from Activity a where a.postId = :postId")
    Activity findActivityByPostId(@Param("postId") Integer postId);

    @Query("select count(a) from Activity a JOIN Post p on a.postId = p.postId where p.userId = :userId and a.statusId = 4 and a.endTime > :startDate and a.endTime < :endDate")
    Integer findActivitiesFinishedForUser(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select count(a) from Activity a JOIN Post p on a.postId = p.postId where p.userId = :userId and a.statusId = 2 and a.startTime > :startDate and a.startTime < :endDate")
    Integer findActivitiesInProgressForUser(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}