package com.example.tang.wuhua.model.response;

import com.example.tang.wuhua.model.response.card.LikeCard;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 获取所有点赞记录的响应
 * @author z1ycheng
 */

public class LikeResponse {

    private int state;

    @SerializedName("list")
    private List<LikeCard> likeCards;

    public boolean success(){
        return state == 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<LikeCard> getLikeCards() {
        return likeCards;
    }

    public void setLikeCards(List<LikeCard> likeCards) {
        this.likeCards = likeCards;
    }

    @Override
    public String toString() {
        return "LikeResponse{" +
                "state=" + state +
                ", likeCards=" + likeCards +
                '}';
    }
}
