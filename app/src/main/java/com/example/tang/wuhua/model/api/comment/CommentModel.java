package com.example.tang.wuhua.model.api.comment;

import java.util.Date;

/**
 * @author z1ycheng
 */

public class CommentModel {

    // 该条评论的id（唯一标识）
    private String commentId;

    // 该条评论所在的朋友圈的id
    private String momentId;

    // 评论内容
    private String content;

    // 评论时间
    private Date commentTime;

    // 评论人id
    private String senderId;

    // 被评论人的id
    private String recieverId;



    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }
}
