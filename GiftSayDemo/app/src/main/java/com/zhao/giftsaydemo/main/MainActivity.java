package com.zhao.giftsaydemo.main;


import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
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
import com.zhao.giftsaydemo.user.UserFragment;

@BindContent(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {
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
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_replace_layout, new HomeFragment());
        homeBtn.setChecked(true);
        transaction.commit();
        setOnClick();

    }

    private void setOnClick() {
        homeBtn.setOnClickListener(this);
        categoryBtn.setOnClickListener(this);
        popBtn.setOnClickListener(this);
        userBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        transaction = getSupportFragmentManager().beginTransaction();
        TextView titleTv;
        titleTv = (TextView) findViewById(R.id.title_name_tv);
        switch (v.getId()) {
            case R.id.main_activity_home_btn:
                findViewById(R.id.title).setVisibility(View.VISIBLE);
                transaction.replace(R.id.main_activity_replace_layout, new HomeFragment());
                findViewById(R.id.title_signin_iv).setVisibility(View.VISIBLE);
                findViewById(R.id.title_name_iv).setVisibility(View.VISIBLE);
                titleTv.setVisibility(View.INVISIBLE);
                //findViewById(R.id.title_fragment_category_tab).setVisibility(View.INVISIBLE);


                break;
            case R.id.main_activity_pop_btn:
                findViewById(R.id.title).setVisibility(View.VISIBLE);

                transaction.replace(R.id.main_activity_replace_layout, new PopFragment());
                findViewById(R.id.title_signin_iv).setVisibility(View.INVISIBLE);
                findViewById(R.id.title_name_iv).setVisibility(View.INVISIBLE);
                titleTv.setVisibility(View.VISIBLE);
                titleTv.setTextColor(Color.WHITE);
                titleTv.setText("热门");
                titleTv.setTextSize(18);
                //findViewById(R.id.title_fragment_category_tab).setVisibility(View.INVISIBLE);


                break;
            case R.id.main_activity_category_btn:
                transaction.replace(R.id.main_activity_replace_layout, new CategoryFragment());
                findViewById(R.id.title).setVisibility(View.GONE);
                //findViewById(R.id.title_fragment_category_tab).setVisibility(View.VISIBLE);

                break;
            case R.id.main_activity_user_btn:
                transaction.replace(R.id.main_activity_replace_layout, new UserFragment());
                findViewById(R.id.title).setVisibility(View.GONE);

        }
        transaction.commit();


    }
}
