package com.wyf.model;

import java.util.Date;

/**
 * Created by w7397 on 2017/3/20.
 */
public class Comment {
    private int id;
    private int userId;
    private int entityId;
    private int entityType;
    private String content;
    private Date createdDate;
    private int status;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(Date createDate) {
        this.createdDate = createDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateDate() {
        return createdDate;
    }

    public int getStatus() {
        return status;
    }
}
