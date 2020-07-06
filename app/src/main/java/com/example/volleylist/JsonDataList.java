package com.example.volleylist;

import java.util.Date;

class JsonDataList{

   public String userId;
   public String id;
   public String title;
   public String body;
   public String taskDate;


    public JsonDataList(String userId, String id, String title, String body, String taskDate) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
        this.taskDate = taskDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTaskDate() {
        Date date = new Date();

        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

}
