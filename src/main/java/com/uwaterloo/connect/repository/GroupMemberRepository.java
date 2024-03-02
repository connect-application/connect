package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMembers, Integer> {

    @Query("select a from GroupMembers a where a.groupId= :groupId and a.userId = :userId")
    GroupMembers isUserinGroup(@Param("groupId") Integer groupId, @Param("userId") Integer userId );

    List<GroupMembers> findGroupMembersByGroupId(Integer groupId);
}
