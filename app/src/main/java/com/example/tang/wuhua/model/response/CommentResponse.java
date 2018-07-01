package com.example.tang.wuhua.model.response;

import com.example.tang.wuhua.model.response.card.CommentCard;

import java.util.List;

/**
 * 获取所有评论的响应
 * @author z1ycheng
 */

public class CommentResponse {

    private int state;

    private List<CommentCard> commentCards;

    public boolean success(){
        return state == 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<CommentCard> getCommentCards() {
        return commentCards;
    }

    public void setCommentCards(List<CommentCard> commentCards) {
        this.commentCards = commentCards;
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "state=" + state +
                ", commentCards=" + commentCards +
                '}';
    }
}
