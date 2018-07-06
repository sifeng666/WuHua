package com.example.tang.wuhua.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tang.wuhua.Data.Moment;
import com.example.tang.wuhua.R;

import org.w3c.dom.Text;

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

        public ViewHolder(View view) {
            super(view);
            tvDateDay = view.findViewById(R.id.timeDay);
            tvDateMonth = view.findViewById(R.id.timeMonth);
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

    }

    @Override
    public int getItemCount() {
        return momentList.size();
    }
}
