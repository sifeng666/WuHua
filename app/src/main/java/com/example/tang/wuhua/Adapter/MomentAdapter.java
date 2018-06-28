package com.example.tang.wuhua.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.tang.wuhua.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by root on 18-6-26.
 */

public class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.ViewHolder> {

    private List<Integer> mMomentList;

    public enum ViewName {
        ITEM,
        USERNAME,
        COMMENT,
        LIKE
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ViewName viewName, ViewHolder holder, int position);
    }

    private OnItemClickListener mItemClickListener;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUsername; //用户名
        public ImageView ivComment; //评论按钮
        public LinearLayout llComment;
        public LikeButton lbLike; //点赞按钮
        public TextView tvLikePeople; //点赞的用户名
        public boolean isLike;
        public PopupWindow popupWindow;

        public ViewHolder(View view) {
            super(view);
            tvUsername = (TextView) view.findViewById(R.id.text_usr_name);
            ivComment = (ImageView) view.findViewById(R.id.img_comment);
            llComment = (LinearLayout) view.findViewById(R.id.layout_comment);
            lbLike = (LikeButton) view.findViewById(R.id.img_heart);
            tvLikePeople = (TextView) view.findViewById(R.id.text_all_like_people);
            isLike = false;
        }
    }

    public MomentAdapter(List<Integer> list) {
        mMomentList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_main_board, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ViewHolder mHolder = holder;
        final View lbv = (View) holder.lbLike;

        if (mItemClickListener != null) {
            holder.tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, ViewName.USERNAME, mHolder, position);
                }
            });
            holder.ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, ViewName.COMMENT, mHolder, position);
                }
            });
            holder.lbLike.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    mItemClickListener.onItemClick(lbv, ViewName.LIKE, mHolder, position);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    mItemClickListener.onItemClick(lbv, ViewName.LIKE, mHolder, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMomentList.size();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        mItemClickListener = clickListener;
    }

}
