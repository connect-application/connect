package com.uwaterloo.connect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attachmentId;
    private Integer postId;
    private byte[] file;

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
