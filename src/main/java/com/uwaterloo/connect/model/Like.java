package com.uwaterloo.connect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes")
@IdClass(LikeId.class)

public class Like {
    @Id
    private Integer postId;
    @Id
    private Integer likedBy;

    public Like(Integer postId, Integer userId) {
        this.postId = postId;
        this.likedBy = userId;
    }

    public Like() {

    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setLikedBy(Integer likedBy) {
        this.likedBy = likedBy;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getLikedBy() {
        return likedBy;
    }
}
