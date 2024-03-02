package com.uwaterloo.connect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "connect_groups")
public class ConnectGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;
    private String groupName;
    private Integer categoryId;
    private String groupCode;
    private Integer groupOwner;


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(Integer groupOwner) {
        this.groupOwner = groupOwner;
    }
}
