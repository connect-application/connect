package com.uwaterloo.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uwaterloo.connect.model.ActivityChildMapping;
import com.uwaterloo.connect.model.ActivitySchedule;
import java.util.List;

public interface ActivityMappingRepository extends JpaRepository<ActivityChildMapping, Integer>{

        @Query("Select a from ActivityChildMapping a where a.parentActivityId = :postId")
        List<ActivityChildMapping> findChildActivitiesByParentId(Integer postId);

    
}
