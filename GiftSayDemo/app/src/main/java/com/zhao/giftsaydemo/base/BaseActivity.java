package com.zhao.giftsaydemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;

import java.lang.reflect.Field;

/**
 * Created by 华哥哥 on 16/5/9.
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentInject();
        viewInject();
        initData();
    }

    // 初始化数据
    public abstract void initData();

    // 初始化组件
    private void viewInject() {
        Class myClass = this.getClass();
        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BindView.class)) {
                BindView bindView = field.getAnnotation(BindView.class);
                int id = bindView.value();
                if (id > 0) {
                    field.setAccessible(true);
                    try {
                        field.set(this, this.findViewById(id));
                    } catch (IllegalAccessException e) {

                    }
                }
            }
        }

    }

    // 绑定布局
    private void contentInject() {
        Class myClass = this.getClass();
        if (myClass.isAnnotationPresent(BindContent.class)) {
            BindContent bindContent = (BindContent) myClass.getAnnotation(BindContent.class);
            int id = bindContent.value();
            if (id > 0) {
                setContentView(id);
            }
        }
    }
}
