package com.example.tang.wuhua.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tang.wuhua.Constant;
import com.example.tang.wuhua.Data.Moment;
import com.example.tang.wuhua.R;
import com.squareup.picasso.Picasso;

import net.datafans.android.common.widget.imageview.CommonImageView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 18-7-5.
 */

public class SimpleMomentAdapter extends RecyclerView.Adapter<SimpleMomentAdapter.ViewHolder>{

    private List<Moment> momentList;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDateDay;
        private TextView tvDateMonth;
        private TextView tvContent;
        private ImageView ivImg;
        //private CommonImageView ivImg;
        private TextView tvImgNum;

        public ViewHolder(View view) {
            super(view);
            tvDateDay = view.findViewById(R.id.timeDay);
            tvDateMonth = view.findViewById(R.id.timeMonth);
            tvContent = view.findViewById(R.id.text_in_items_userTextImage);
            ivImg = view.findViewById(R.id.firstImg_in_items_userTextImage);
            tvImgNum = view.findViewById(R.id.photoCount_in_items_userTextImage);
        }
    }

    public SimpleMomentAdapter(Context context, List<Moment> moments) {
        momentList = moments;
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_self_homepage, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date dt = momentList.get(position).getPublishTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        List<String> images = momentList.get(position).getImages();
        if (images != null) {
            if (images.size() > 0) {
                if (!images.get(0).equals("")) {
                    holder.ivImg.setVisibility(View.VISIBLE);
                    //holder.ivImg.loadImage(Constant.Value.BASE_URL + images.get(0));
                    Picasso.with(mContext).load(Constant.Value.BASE_URL + images.get(0)).into(holder.ivImg);
                    int count = 0;
                    for (int i = 0; i < images.size(); i++) {
                        if (!images.get(i).equals("")) {
                            count++;
                        }
                        else {
                            break;
                        }
                    }
                    holder.tvImgNum.setText("共" + count + "张");
                }
                else {
                    holder.ivImg.setVisibility(View.GONE);
                    holder.tvImgNum.setVisibility(View.GONE);
                }
            }
            else {
                holder.ivImg.setVisibility(View.GONE);
            }
        }
        else {
            holder.ivImg.setVisibility(View.GONE);
        }
        holder.tvDateDay.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        holder.tvDateMonth.setText(Integer.toString(cal.get(Calendar.MONTH) + 1) + "月");
        holder.tvContent.setText(momentList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return momentList.size();
    }
}
