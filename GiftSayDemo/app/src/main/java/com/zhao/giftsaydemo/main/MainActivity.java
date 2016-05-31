package com.zhao.giftsaydemo.main;

/**
 * Created by 华哥哥 on 16/5/12.
 * 真正的主页面
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.category.CategoryFragment;
import com.zhao.giftsaydemo.home.HomeFragment;
import com.zhao.giftsaydemo.pop.PopFragment;
import com.zhao.giftsaydemo.search.SearchActivity;
import com.zhao.giftsaydemo.user.UserFragment;
import com.zhao.giftsaydemo.util.ExampleUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.jpush.android.api.JPushInterface;

@BindContent(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static boolean isForeground = false;

    @BindView(R.id.main_activity_home_btn)
    private RadioButton homeBtn;
    @BindView(R.id.main_activity_category_btn)
    private RadioButton categoryBtn;
    @BindView(R.id.main_activity_pop_btn)
    private RadioButton popBtn;
    @BindView(R.id.main_activity_user_btn)

    private RadioButton userBtn;
    private FragmentTransaction transaction;


    @Override
    public void initData() {
        // 进入页面就显示HomeFragment
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_replace_layout, new HomeFragment());
        homeBtn.setChecked(true);
        transaction.commit();
        setOnClick();


        registerMessageReceiver();  // used for receive msg
    }

    // 给RadioButton设置点击事件
    private void setOnClick() {
        homeBtn.setOnClickListener(this);
        categoryBtn.setOnClickListener(this);
        popBtn.setOnClickListener(this);
        userBtn.setOnClickListener(this);
        findViewById(R.id.title_right_iv).setOnClickListener(this);
    }


    // 点击各个RadioButton切换页面
    @Override
    public void onClick(View v) {
        transaction = getSupportFragmentManager().beginTransaction();
        TextView titleTv;
        titleTv = (TextView) findViewById(R.id.title_name_tv);
        switch (v.getId()) {
            case R.id.main_activity_home_btn:
                findViewById(R.id.title).setVisibility(View.VISIBLE);
                transaction.replace(R.id.main_activity_replace_layout, new HomeFragment());
                findViewById(R.id.title_left_iv).setVisibility(View.VISIBLE);
                findViewById(R.id.title_name_iv).setVisibility(View.VISIBLE);
                titleTv.setVisibility(View.INVISIBLE);
                //findViewById(R.id.title_fragment_category_tab).setVisibility(View.INVISIBLE);


                break;
            case R.id.main_activity_pop_btn:
                findViewById(R.id.title).setVisibility(View.VISIBLE);

                transaction.replace(R.id.main_activity_replace_layout, new PopFragment());
                findViewById(R.id.title_left_iv).setVisibility(View.INVISIBLE);
                findViewById(R.id.title_name_iv).setVisibility(View.INVISIBLE);
                titleTv.setVisibility(View.VISIBLE);
                titleTv.setText("热门");
                titleTv.setTextSize(16);
                //findViewById(R.id.title_fragment_category_tab).setVisibility(View.INVISIBLE);


                break;
            case R.id.main_activity_category_btn:
                transaction.replace(R.id.main_activity_replace_layout, new CategoryFragment());
                findViewById(R.id.title).setVisibility(View.GONE);

                break;
            case R.id.main_activity_user_btn:
                transaction.replace(R.id.main_activity_replace_layout, new UserFragment());
                findViewById(R.id.title).setVisibility(View.GONE);
                break;
            case R.id.title_right_iv:
                startActivity(new Intent(this, SearchActivity.class));
                break;

        }
        transaction.commit();


    }


    // 以下是推送相关内容


    private void init() {
        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                //setCostomMsg(showMsg.toString());
            }
        }
    }


}
