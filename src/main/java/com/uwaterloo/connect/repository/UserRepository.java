package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Post;
import com.uwaterloo.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.userName = :userName OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("userName") String userName, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User a SET a.active = TRUE, a.updatedAt = :updatedAt WHERE a.email = :email")
    int enableUser(String email, LocalDateTime updatedAt);

    User findUserById(Integer userId);

    @Query("select p.userId from Post p JOIN Activity a on a.postId = p.postId where a.statusId = 4 and a.endTime > :startDate and a.endTime < :endDate and a.categoryId = :categoryId")
    List<Integer> findUsersByActivitiesFinishedBetweenTimeForCategory(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("categoryId") Integer categoryId);

    @Query("select p.userId from Post p JOIN Activity a on a.postId = p.postId where a.statusId = 4 and a.startTime > :startDate and a.startTime < :endDate and a.categoryId = :categoryId")
    List<Integer> findUsersByActivitiesInProgressBetweenTimeForCategory(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("categoryId") Integer categoryId);

}
