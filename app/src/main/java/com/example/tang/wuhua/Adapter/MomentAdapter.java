package com.example.tang.wuhua.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tang.wuhua.Data.Comment;
import com.example.tang.wuhua.Data.Moment;
import com.example.tang.wuhua.MainActivity;
import com.example.tang.wuhua.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 18-6-26.
 */

public class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.ViewHolder> {

    private List<Moment> mMomentList;
    private Context context;

    public enum ViewName {
        ITEM,
        USERNAME,
        COMMENT,
        LIKE,
        SHOW
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ViewName viewName, ViewHolder holder, int position);
    }

    private OnItemClickListener mItemClickListener;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUsername; //用户名
        public ImageView ivComment; //评论按钮
        public LinearLayout llComment; //装载评论的layout
        public RelativeLayout llLike; //装载点赞的layout
        public TextView tvContent; //动态文字内容
        public TextView tvShowAndPack; //显示全文
        public LikeButton lbLike; //点赞按钮
        public TextView tvLikePeople; //点赞的用户名
        public boolean isLike;
        public PopupWindow popupWindow;

        public ViewHolder(View view) {
            super(view);
            tvUsername = (TextView) view.findViewById(R.id.text_usr_name);
            tvContent = (TextView) view.findViewById(R.id.text_usr_content);
            ivComment = (ImageView) view.findViewById(R.id.img_comment);
            llComment = (LinearLayout) view.findViewById(R.id.layout_comment);
            llLike = (RelativeLayout) view.findViewById(R.id.layout_like);
            lbLike = (LikeButton) view.findViewById(R.id.img_heart);
            tvLikePeople = (TextView) view.findViewById(R.id.text_all_like_people);
            tvShowAndPack = (TextView) view.findViewById(R.id.text_show_and_pack);
            isLike = false;
        }
    }

    public MomentAdapter(Context context, List<Moment> list) {
        mMomentList = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_main_board, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Moment moment = mMomentList.get(position);

        //设置发布者的名字
        holder.tvUsername.setText(moment.getOwner().getUsername());

        //设置文字内容，如果文字长度大于108个字，那么只显示部分内容，并显示全文按钮
        String momentContent = moment.getContent();
        if (momentContent.length() < 109) {
            holder.tvContent.setText(moment.getContent());
            holder.tvShowAndPack.setVisibility(View.GONE);
        }
        else {
            holder.tvContent.setText(momentContent.substring(0, 108));
            holder.tvShowAndPack.setVisibility(View.VISIBLE);
            holder.tvShowAndPack.setText("全文");
        }

        //显示评论内容
        holder.llComment.removeAllViews();
        for (int i = 0; i < mMomentList.get(position).getComments().size(); i++) {
            Comment comment = mMomentList.get(position).getComments().get(i);
            View commentView = View.inflate(context, R.layout.items_comment, null);
            TextView tvPerson1 = commentView.findViewById(R.id.text_person_1);
            TextView tvPerson2 = commentView.findViewById(R.id.text_person_2);
            TextView tvReply = commentView.findViewById(R.id.text_choose_reply_or_colon);
            TextView tvContent = commentView.findViewById(R.id.text_comment_content);
            if (comment.getType() == 1) {
                tvPerson1.setText(comment.getSender());
                tvPerson2.setVisibility(View.GONE);
                tvReply.setText(":");
                tvContent.setText(comment.getContent());
            }
            else if (comment.getType() == 2) {
                tvPerson1.setText(comment.getSender());
                tvPerson2.setVisibility(View.VISIBLE);
                tvPerson2.setText(comment.getReceiver());
                tvReply.setText("回复");
                tvContent.setText(comment.getContent());
            }
            holder.llComment.addView(commentView);
            tvPerson1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, ViewName.COMMENT, holder, position);
                }
            });
            tvPerson2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, ViewName.COMMENT, holder, position);
                }
            });
        }

        //显示点赞内容;
        if (mMomentList.get(position).getLikes().size() == 0) {
            holder.llLike.setVisibility(View.GONE);
        }
        else {
            holder.llLike.setVisibility(View.VISIBLE);
            StringBuilder name = new StringBuilder("      ");
            for (int i = 0; i < mMomentList.get(position).getLikes().size(); i++) {
                if (i != 0) {
                    name.append(", ");
                }
                name.append(mMomentList.get(position).getLikes().get(i));
            }
            holder.tvLikePeople.setText(name);
        }

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
            holder.tvShowAndPack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, ViewName.SHOW, holder, position);
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
