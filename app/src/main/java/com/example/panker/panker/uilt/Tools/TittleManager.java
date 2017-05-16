package com.example.panker.panker.uilt.Tools;

/**
 * Created by user on 2016/7/13.
 * 界面标题管理类
 * Fenrir
 */

import com.example.panker.panker.R;
import com.example.panker.panker.ui.activity.SystemBarTintManagerHelper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TittleManager {
    private Activity mActivity;
    private LinearLayout mLeftLayout, mRightLayout;
    private SystemBarTintManagerHelper systemBarTintManagerHelper;
    private ImageView mLeftTitleView;
    private TextView mLeftTitleText;
    private ImageView mRightTitleView;
    private TextView mRightTitleText;
    private TextView mTitleName;
    private OnClickListener mDefaultlistener =  new OnClickListener() {
        @Override
        public void onClick(View v) {
            mActivity.finish();
        }
    };

    public enum TitleStyle {
        ONLY_TITLE, // 只有标题
        ONLY_BACK, // 有标题和左边的按钮（返回）
        BACK_AND_FAVORITE, // 返回与喜欢
        BACK_AND_SAVE, // 返回与保存
        BACK_AND_STEP,//返回与下一步
        LEFT_WITHOUT_IMG,
        ONLY_SETTING;//
    }

//    /**
//     * 此fragment用于主界面，无返回键
//     *
//     * @param view
//     */
//    public TittleManager(View view) {
//        mTitleName = (TextView) view.findViewById(R.id.title_name);
//        mLeftLayout = (LinearLayout) view.findViewById(R.id.tittle_left_layout);
//        mRightLayout = (LinearLayout) view.findViewById(R.id.tltie_right_layout);
//        mLeftTitleView = (ImageView) view.findViewById(R.id.tittle_left_image);
//        mLeftTitleText = (TextView) view.findViewById(R.id.tittle_left_text);
//        mRightTitleView = (ImageView) view.findViewById(R.id.title_right_image);
//        mRightTitleText = (TextView) view.findViewById(R.id.title_right_text);
//
//    }

    public TittleManager(Activity activity) {
        mActivity = activity;
        mTitleName = (TextView) activity.findViewById(R.id.title_name);
        mLeftLayout = (LinearLayout) activity.findViewById(R.id.tittle_left_layout);
        mRightLayout = (LinearLayout) activity.findViewById(R.id.tltie_right_layout);

        mLeftTitleView = (ImageView) activity.findViewById(R.id.tittle_left_image);
        mLeftTitleText = (TextView) activity.findViewById(R.id.tittle_left_text);
        mRightTitleView = (ImageView) activity.findViewById(R.id.title_right_image);
        mRightTitleText = (TextView) activity.findViewById(R.id.title_right_text);
        systemBarTintManagerHelper = new SystemBarTintManagerHelper(activity);
    }

    public void setTitleStyle(TitleStyle flag, String titleName) {
        mTitleName.setText(titleName);
        switch (flag) {
            case ONLY_TITLE:
                setCustomTitle(0, "", titleName, 0, "");
                break;
            case ONLY_BACK:
                setCustomTitle(R.drawable.tittle_image_back, "", titleName, 0, "");
                setLeftTitleListener(mDefaultlistener);
                break;
            case BACK_AND_FAVORITE:
                setCustomTitle(R.drawable.tittle_image_back, "", titleName, R.drawable.tittle_yes_favorite, "");
                setLeftTitleListener(mDefaultlistener);
                break;
            case BACK_AND_SAVE:
                setCustomTitle(R.drawable.tittle_image_back, "", titleName, 0, "保存");
                setLeftTitleListener(mDefaultlistener);
                break;
            case BACK_AND_STEP:
                setCustomTitle(R.drawable.tittle_image_back,"",titleName,0,"下一步");
                setRightTitleListener(mDefaultlistener);
                break;
            case ONLY_SETTING:
                setCustomTitle(R.drawable.tools, "", titleName, 0, "");
                setLeftTitleListener(mDefaultlistener);
                break;
            case LEFT_WITHOUT_IMG:
                setCustomTitle(0, "", titleName, 0, "");
                setLeftTitleListener(mDefaultlistener);
                break;
        }
    }

    /**
     * @param arg0 标题左图标的资源id
     * @param arg1 标题左文字的内容
     * @param arg2 标题名
     * @param arg3 标题右图标的资源id
     * @param arg4 标题右文字的内容
     */
    public void setCustomTitle(int arg0, String arg1, @NonNull String arg2, int arg3, String arg4) {
        mTitleName.setText(arg2);
        if (arg0 != 0 || !TextUtils.isEmpty(arg1)) {
            if (arg0 != 0) {
                mLeftTitleView.setBackgroundResource(arg0);
            } else {
                mLeftTitleView.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(arg1)) {
                mLeftTitleText.setText(arg1);
            } else {
                mLeftTitleText.setVisibility(View.GONE);
            }
        } else {
            mLeftLayout.setVisibility(View.GONE);
        }

        if (arg3 != 0 || !TextUtils.isEmpty(arg4)) {
            if (arg3 != 0) {
                mRightTitleView.setBackgroundResource(arg3);
            } else {
                mRightTitleView.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(arg4)) {
                mRightTitleText.setText(arg4);
            } else {
                mRightTitleText.setVisibility(View.GONE);
            }
        } else {
            mRightLayout.setVisibility(View.GONE);
        }
    }
    public void setLeftImage(){
        mLeftTitleView.setVisibility(View.VISIBLE);
    }
    public void setLeftText(String text) {
        mLeftTitleText.setText(text);
    }

    public void setRightText(String text) {
        mRightTitleText.setText(text);
    }

    public void setTitleName(String titleName) {
        mTitleName.setText(titleName);
    }

    public void setLeftTitleListener(OnClickListener listener) {
        mLeftLayout.setOnClickListener(listener);
    }

    public void setRightTitleListener(OnClickListener listener) {
        mRightLayout.setOnClickListener(listener);
    }
}