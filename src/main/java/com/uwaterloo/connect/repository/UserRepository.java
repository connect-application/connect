package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
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

}
