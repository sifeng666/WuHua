package com.example.tang.wuhua.model.response.card;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 包含一条评论的必要信息
 * @author z1ycheng
 */

public class CommentCard {

    // 该条评论的id（唯一标识）
    @SerializedName("Cid")
    private String commentId;

    // 评论人id
    @SerializedName("Source_c")
    private String sourceId;

    // 被评论的人的id
    @SerializedName("Dest_c")
    private String destinationId;

    // 评论内容
    @SerializedName("Text_c")
    private String text;

    // 该条评论所在的朋友圈的id
    @SerializedName("Mid")
    private String momentId;

    // 评论的时间
    @SerializedName("Time_c")
    private Date commentTime;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "CommentCard{" +
                "commentId='" + commentId + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", destinationId='" + destinationId + '\'' +
                ", text='" + text + '\'' +
                ", momentId='" + momentId + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }
}
