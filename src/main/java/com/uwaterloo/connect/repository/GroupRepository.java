package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.ConnectGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<ConnectGroups, Integer> {

    ConnectGroups findConnectGroupsByGroupId(Integer groupId);

    @Query("select a.groupOwner from ConnectGroups a where a.groupId = :groupId")
    Integer findGroupOwnerByGroupId(@Param("groupId") Integer groupId);
}
