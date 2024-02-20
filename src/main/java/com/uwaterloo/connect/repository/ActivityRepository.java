package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("select a from Activity a where a.actId = :actId")
    Activity findActivityByActivityId(@Param("actId") Integer actId);


}