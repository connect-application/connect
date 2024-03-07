package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByFromUserIdAndToUserId(Integer fromUserId, Integer toUserId);

    @Query("SELECT DISTINCT c.toUserId FROM Chat c WHERE c.fromUserId = ?1")
    List<Integer> findDistinctToUserIdsByFromUserId(Integer fromUserId);
}
