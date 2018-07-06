package com.example.tang.wuhua;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.tang.wuhua.Data.Comment;
import com.example.tang.wuhua.Data.Moment;
import com.example.tang.wuhua.Data.User;
import com.example.tang.wuhua.model.parameter.CommentModel;
import com.example.tang.wuhua.model.parameter.LikeModel;
import com.example.tang.wuhua.model.parameter.MomentModel;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.model.response.CommentResponse;
import com.example.tang.wuhua.model.response.LikeResponse;
import com.example.tang.wuhua.model.response.MomentResponse;
import com.example.tang.wuhua.model.response.UserResponse;
import com.example.tang.wuhua.model.response.card.CommentCard;
import com.example.tang.wuhua.model.response.card.LikeCard;
import com.example.tang.wuhua.model.response.card.MomentCard;
import com.example.tang.wuhua.net.Network;
import com.example.tang.wuhua.net.helper.NetworkHelper;
import com.example.tang.wuhua.net.service.RemoteService;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 18-6-30.
 */

public class MomentFragment extends Fragment {

    //@BindView(R.id.main_recyclerView)
    RecyclerView mainRecyclerview;
    RefreshLayout refreshLayout;

    private static final String BAIDU_MAP_KEY = "VLmb30ftbg4nTLF5uTKqduTVtOxp7mCi";
    private ArrayList<Moment> momentList;
    private ArrayList<User> userList;
    private MomentAdapter momentAdapter;
    public LocationClient mLocationClient; //位置信息
    private User me;
    private View personView; //在评论点击函数中用到
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
        me = ((MainActivity) getActivity()).getUser();
        //ButterKnife.bind(v);
        momentList = new ArrayList<>();
        requestLocation();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                requestMoments(latitude, longitude, true, new Date());
                //initData();
                //momentAdapter.notifyDataSetChanged();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

        refreshLayout.autoRefresh();

        ((MainActivity) getActivity()).getTvMenuTitle().setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                mainRecyclerview.scrollToPosition(0);
                refreshLayout.autoRefresh();
            }
        }));

        final LinearLayoutManager mainLayoutManager = new LinearLayoutManager(getContext());
        mainRecyclerview.setLayoutManager(mainLayoutManager);
        momentAdapter = new MomentAdapter(getContext(), momentList);
        mainRecyclerview.setAdapter(momentAdapter);
        momentAdapter.setClickListener(new MomentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MomentAdapter.ViewName viewName, final MomentAdapter.ViewHolder holder, final int position) {
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
                    personView = view;
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String content = etCommentInput.getText().toString();
                            if (content.length() == 0) {
                                Toast.makeText(getContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Comment comment;
                            if (personView.getId() == R.id.img_comment) {
                                comment = new Comment(me.getUsername(), content);
                            }
                            else {
                                comment = new Comment(me.getUsername(), ((TextView) personView).getText().toString(), content);
                            }
                            momentList.get(position).getComments().add(comment);
                            momentAdapter.notifyDataSetChanged();
                            etCommentInput.setText("");
                            holder.popupWindow.dismiss();
                        }
                    });

                } else if (viewName == MomentAdapter.ViewName.LIKE) {
                    StringBuilder likeName = new StringBuilder(holder.tvLikePeople.getText().toString());
                    String myUsername = me.getUsername();
                    if (!momentList.get(position).isLike()) {
                        momentList.get(position).getLikes().add(myUsername);
                        momentList.get(position).setLike(true);
                        momentAdapter.notifyDataSetChanged();
                        //holder.lbLike.setLiked(true);
                    } else {
                        momentList.get(position).getLikes().remove(momentList.get(position).getLikes().size()-1);
                        momentList.get(position).setLike(false);
                        momentAdapter.notifyDataSetChanged();
                        //holder.lbLike.setLiked(false);
                    }
                } else if (viewName == MomentAdapter.ViewName.SHOW) {
                    String text = holder.tvShowAndPack.getText().toString();
                    if (text == "全文") {
                        holder.tvContent.setText(momentList.get(position).getContent());
                        holder.tvShowAndPack.setText("收起");
                    }
                    else if (text == "收起") {
                        holder.tvContent.setText(momentList.get(position).getContent().substring(0, 108));
                        holder.tvShowAndPack.setText("全文");
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
            //Toast.makeText(getContext(), bdLocation.getStreet(), Toast.LENGTH_SHORT).show();
            mLocationClient.stop();
        }
    }


    public void initData() {
        //momentList = new ArrayList<>();
        userList = new ArrayList<>();
        String [] users = new String[] {
                "梅西", "C罗", "詹姆斯", "库里"
        };
        String [] contents = {
                "今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错" +
                        "今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错" +
                        "今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错" +
                        "今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错今天天气不错",
                "安卓弄到我好烦",
                "世界杯再见",
                "哈哈哈哈"
        };
        for (int i = 0; i < users.length; i++) {
            userList.add(new User(users[i]));
            momentList.add(new Moment(userList.get(i), contents[i]));
        }
    }

    public void requestMoments(double latitude, double longitude, final boolean isMovingUp, Date time) {
        NetworkHelper.refreshMoments(latitude, longitude, isMovingUp, time, new Callback<MomentResponse>() {
            @Override
            public void onResponse(Call<MomentResponse> call, Response<MomentResponse> response) {
                if (response.isSuccessful()) {
                    MomentResponse result = response.body();
                    Log.d("momentResponse", result.toString());
                    if (result.success()) {
                        List<MomentCard> momentCardList = result.getMomentCards();
                        if (isMovingUp) {
                            momentList.clear();
                        }
                        for (int i = 0; i < momentCardList.size(); i++) {
                            momentList.add(new Moment(momentCardList.get(i)));
                            getUserInfo(momentList.get(i).getUserId(), i);
                            //getComments(momentList.get(i).getId(), i);
                            //getLikes(momentList.get(i).getId(), i);
                        }
                        momentAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MomentResponse> call, Throwable t) {

            }
        });
    }

    private void getComments(String mid, final int position) {
        NetworkHelper.getCommentsByMomentId(mid, new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    CommentResponse result = response.body();
                    if (result.success()) {
                        List<CommentCard> commentCardList = result.getCommentCards();
                        for (int i = 0; i < commentCardList.size(); i++) {
                            momentList.get(position).getComments().add(new Comment(commentCardList.get(i)));
                        }
                        momentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {

            }
        });
    }

    private void getLikes(String mid, final int position) {
        NetworkHelper.getLikesByMomentId(mid, new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if (response.isSuccessful()) {
                    LikeResponse result = response.body();
                    if (result.success()) {
                        List<LikeCard> likeCardList = result.getLikeCards();
                        //boolean isLike = false;
                        for (int i = 0; i < likeCardList.size(); i++) {
                            String name = likeCardList.get(i).getNickname();
                            if (name == me.getNickname()) {
                                momentList.get(position).setLike(true);
                            }
                            momentList.get(position).getLikes().add(name);
                        }
                        momentAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {

            }
        });
    }

    //position: momentList中的位置
    private void sendComment(String sourceId, String desId, String text, final String mid, Date time, final int position) {
        NetworkHelper.publishComment(new CommentModel(sourceId, desId, text, mid, time), new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().success()) {
                        getComments(mid, position);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    private void sendLike(final String mid, String uid, Date time, final int position) {
        NetworkHelper.like(new LikeModel(mid, uid, time), new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().success()) {
                        getLikes(mid, position);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    private void cancelLike(final String mid, String uid, Date time, final int position) {
        NetworkHelper.likeCancel(uid, mid, new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().success()) {
                        getLikes(mid, position);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    private void getUserInfo(String uid, final int position) {
        NetworkHelper.getUserInfoByUserId(uid, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse result = response.body();
                    Log.d("userInfo", result.toString());
                    if (result.success()) {
                        if (position == -1) {
                            me = new User(result);
                        } else {
                            momentList.get(position).setOwner(new User(result));
                        }
                        momentAdapter.notifyDataSetChanged();
                    }
                    Log.d("momentList", momentList.get(position).getOwner().getUsername());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("userInfo", "fail");
            }
        });
    }


}
