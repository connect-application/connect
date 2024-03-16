package com.uwaterloo.connect.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.uwaterloo.connect.model.GroupPostMapping;

@Repository
public interface GroupPostMappingRepository extends JpaRepository<GroupPostMapping, Integer> {

    @Query("SELECT g FROM GroupPostMapping g WHERE g.groupId = :groupId")
    List<GroupPostMapping> findByGroupId(Integer groupId);

    @Modifying
    @Query("DELETE FROM GroupPostMapping g WHERE g.postId = :postId")
    void deleteGroupPosts(@Param("postId") Integer postId);

}
