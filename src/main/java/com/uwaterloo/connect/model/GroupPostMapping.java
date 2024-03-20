package com.uwaterloo.connect.model;
import jakarta.persistence.*;

@Entity
@Table(name = "group_post")

public class GroupPostMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "post_id")
    private Integer postId;


    public GroupPostMapping(Integer groupId, Integer postId) {
        this.groupId = groupId;
        this.postId = postId;
    }

}
