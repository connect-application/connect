package com.uwaterloo.connect.repository;

import com.uwaterloo.connect.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    List<Attachment> findAttachmentByPostId(Integer postId);

    @Query("select a.postId from Attachment a where a.attachmentId= :attachmentId")
    Integer findPostIdByAttachment(@Param("attachmentId") Integer attachmentId);
}
