package com.example.tang.wuhua.Data;

import com.example.tang.wuhua.model.response.card.CommentCard;

/**
 * Created by root on 18-6-28.
 */

public class Comment {

    private String sender;
    private String receiver;
    private String senderId;
    private String receiverId;
    private int type; //评论类型：1表示只评论 2表示回复别人
    private String content;
    private String id;

    public Comment(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.type = 1;
        this.receiver = "";
    }

    public Comment(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = 2;
        this.content = content;
    }

    public Comment(CommentCard commentCard) {
        this.sender = commentCard.getSourceNickname();
        this.senderId = commentCard.getSourceId();
        this.receiver = commentCard.getDestinationNickname();
        this.receiverId = commentCard.getDestinationId();
        this.content = commentCard.getText();
        if (this.receiverId == null) {
            this.type = 1;
        }
        else {
            this.type = 2;
        }
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
