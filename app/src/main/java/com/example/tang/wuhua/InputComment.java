package com.example.tang.wuhua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tang on 27/06/2018.
 */

public class InputComment extends FrameLayout {

    private Context context;

    private EditText editText;
    private TextView sendButton;
    private View mask;

    private long commentId;

    private Delegate delegate;

    public InputComment(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    private void initView() {
        View inputView = LayoutInflater.from(context).inflate(R.layout.activity_input_comment, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(inputView, params);

        mask = inputView.findViewById(R.id.mask_input_comment);
        editText = (EditText) inputView.findViewById(R.id.edit_input_comment);
        sendButton = (TextView) inputView.findViewById(R.id.btn_send_input_comment);


        mask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
                if (delegate == null) return;

                String text = editText.getText().toString();

                editText.setText("");

                if (text.equals("")) return;

                delegate.onCommentCreate(commentId, text);
            }
        });

    }

    public void show() {
        setVisibility(VISIBLE);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);

    }


    public void hide() {
        setVisibility(GONE);
        setPlaceHolder("");
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    public void setPlaceHolder(String text){
        editText.setHint(text);
    }



    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }


    public static interface Delegate {

        void onCommentCreate(long commentId, String text);
    }


}
