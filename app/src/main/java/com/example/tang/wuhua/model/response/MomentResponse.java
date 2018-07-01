package com.example.tang.wuhua.model.response;

import com.example.tang.wuhua.model.response.card.MomentCard;

import java.util.List;

/**
 * 获取多条朋友圈的响应
 * @author z1ycheng
 */

public class MomentResponse {

    private int state;

    private List<MomentCard> momentCards;

    public boolean success(){
        return state == 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<MomentCard> getMomentCards() {
        return momentCards;
    }

    public void setMomentCards(List<MomentCard> momentCards) {
        this.momentCards = momentCards;
    }

    @Override
    public String toString() {
        return "MomentResponse{" +
                "state=" + state +
                ", momentCards=" + momentCards +
                '}';
    }
}
