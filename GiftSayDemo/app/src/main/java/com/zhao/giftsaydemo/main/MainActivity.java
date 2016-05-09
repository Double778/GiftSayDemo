package com.zhao.giftsaydemo.main;


import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

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
        switch (v.getId()) {
            case R.id.main_activity_home_btn:
                transaction.replace(R.id.main_activity_replace_layout, new HomeFragment());
                break;
            case R.id.main_activity_pop_btn:
                transaction.replace(R.id.main_activity_replace_layout, new PopFragment());
                break;
            case R.id.main_activity_category_btn:
                transaction.replace(R.id.main_activity_replace_layout, new CategoryFragment());
                break;
            case R.id.main_activity_user_btn:
                transaction.replace(R.id.main_activity_replace_layout, new UserFragment());
        }
        transaction.commit();


    }
}
