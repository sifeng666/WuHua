package com.example.tang.wuhua;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.tang.wuhua.Adapter.MomentAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 18-6-30.
 */

public class MomentFragment extends Fragment {

    //@BindView(R.id.main_recyclerView)
    RecyclerView mainRecyclerview;
    RefreshLayout refreshLayout;


    private List<Integer> momentList = new ArrayList<>();
    private MomentAdapter momentAdapter;
    public LocationClient mLocationClient; //位置信息
    private double latitude; //经度
    private double longitude; //纬度

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_moment, container, false);
        mainRecyclerview = v.findViewById(R.id.main_recyclerView);
        refreshLayout = v.findViewById(R.id.refreshLayout);
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(new MomentFragment.MyLocationListener());
        //ButterKnife.bind(v);

        requestLocation();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                for (int i = 3; i < 6; i++) {
                    momentList.add(i);
                }
                momentAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

        for (int i = 0; i < 3; i++) {
            momentList.add(i);
        }
        final LinearLayoutManager mainLayoutManager = new LinearLayoutManager(getContext());
        mainRecyclerview.setLayoutManager(mainLayoutManager);
        momentAdapter = new MomentAdapter(momentList);
        mainRecyclerview.setAdapter(momentAdapter);
        momentAdapter.setClickListener(new MomentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MomentAdapter.ViewName viewName, final MomentAdapter.ViewHolder holder, int position) {
                if (viewName == MomentAdapter.ViewName.USERNAME) {
                    TextView tv = (TextView) view;
                    Toast.makeText(getContext(), tv.getText(), Toast.LENGTH_SHORT).show();
                } else if (viewName == MomentAdapter.ViewName.COMMENT) {
                    //mainLayoutManager.setStackFromEnd(true); // 使输入框弹出时内容上移
                    //弹出输入框

                    mainLayoutManager.scrollToPositionWithOffset(position, 0); // 使输入框弹出时内容上移
                    //Log.d("position", Integer.toString(position));

                    View popupView = LayoutInflater.from(getContext()).inflate(R.layout.activity_input_comment, null);
                    if (holder.popupWindow == null) {
                        holder.popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, false);
                    }

                    holder.popupWindow.setFocusable(true); // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
                    holder.popupWindow.setOutsideTouchable(false); // 设置允许在外点击消失。无效？
                    holder.popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景。 无效？
                    holder.popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); //软键盘不会挡着popupwindow
                    //holder.popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    holder.popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //显示菜单

                    //监听菜单的关闭事件
                    holder.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            ;
                        }
                    });
                    holder.popupWindow.setTouchable(true);

                    //监听触屏事件
                    holder.popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                holder.popupWindow.dismiss();
                            }
                            return false;
                        }
                    });

                    //弹出软键盘，需要异步处理
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }, 0);

                    final EditText etCommentInput = (EditText) popupView.findViewById(R.id.edit_input_comment);
                    TextView btnSubmit = (TextView) popupView.findViewById(R.id.btn_send_input_comment);
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String content = etCommentInput.getText().toString();
                            if (content.length() == 0) {
                                Toast.makeText(getContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
                            View commentView = View.inflate(getContext(), R.layout.items_comment, null);
                            TextView tvPerson1 = commentView.findViewById(R.id.text_person_1);
                            TextView tvPerson2 = commentView.findViewById(R.id.text_person_2);
                            TextView tvReply = commentView.findViewById(R.id.text_choose_reply_or_colon);
                            TextView tvContent = commentView.findViewById(R.id.text_comment_content);
                            tvPerson1.setText(holder.tvUsername.getText().toString());
                            tvPerson2.setVisibility(View.GONE);
                            tvReply.setText(":");
                            tvContent.setText(content);
                            holder.llComment.addView(commentView);
                            etCommentInput.setText("");
                            holder.popupWindow.dismiss();
                        }
                    });

                } else if (viewName == MomentAdapter.ViewName.LIKE) {
                    StringBuilder likeName = new StringBuilder(holder.tvLikePeople.getText().toString());
                    String myUsername = holder.tvUsername.getText().toString();
                    if (!holder.isLike) {
                        likeName.append(", ").append(myUsername);
                        holder.isLike = true;
                        holder.tvLikePeople.setText(likeName);
                        //holder.lbLike.setLiked(true);
                    } else {
                        holder.isLike = false;
                        likeName.delete(likeName.length() - (myUsername.length() + 2), likeName.length());
                        holder.tvLikePeople.setText(likeName);
                        //holder.lbLike.setLiked(false);
                    }
                }
            }
        });
        return v;
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            latitude = bdLocation.getLatitude();
            longitude = bdLocation.getLongitude();
            Toast.makeText(getContext(), bdLocation.getStreet(), Toast.LENGTH_SHORT).show();
            mLocationClient.stop();
        }
    }
}
