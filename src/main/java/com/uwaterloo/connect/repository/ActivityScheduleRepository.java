package com.uwaterloo.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.uwaterloo.connect.model.ActivitySchedule;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.query.Param;


public interface ActivityScheduleRepository extends JpaRepository<ActivitySchedule, Integer> {

    @Query("Select a From ActivitySchedule a WHERE a.activityId = :postId")
    ActivitySchedule findScheduledActivityByPostId(Integer postId);


    @Query("SELECT a FROM ActivitySchedule a WHERE a.nextRunDate <= :currentTimestamp")
    List<ActivitySchedule> findAllByNextRunDateBeforeOrEqualTo(@Param("currentTimestamp") LocalDateTime currentTimestamp);

    @Modifying
    @Query("DELETE FROM ActivitySchedule a WHERE a.nextRunDate IS NULL")
    void deleteActivities();
    
}
