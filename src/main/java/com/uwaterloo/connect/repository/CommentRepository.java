package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<PostComment, Integer> {
    List<PostComment> findByPostId(Integer postId);
}