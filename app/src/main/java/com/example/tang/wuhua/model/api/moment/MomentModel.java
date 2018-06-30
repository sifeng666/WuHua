package com.example.tang.wuhua.model.api.moment;

import java.util.Date;

/**
 * 朋友圈请求实体
 * @author z1ycheng
 */

public class MomentModel {

    private String momentId;

    // 文本
    private String text;

    // 附件
    private String attach;

    // 类型
    private int type;

    // 发布者的id
    private String publisherId;

    // 发布位置
    private String position;

    // 发布时间
    private Date publishAt;

    public String getMomentId() {
        return momentId;
    }

    public MomentModel(String momentId, String text, String attach, int type, String publisherId, String position, Date publishAt) {
        this.momentId = momentId;
        this.text = text;
        this.attach = attach;
        this.type = type;
        this.publisherId = publisherId;
        this.position = position;
        this.publishAt = publishAt;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }
}
