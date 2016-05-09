package com.zhao.giftsaydemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;

import java.lang.reflect.Field;

/**
 * Created by 华哥哥 on 16/5/9.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 绑定布局
        Class myClass = this.getClass();
        if (myClass.isAnnotationPresent(BindContent.class)) {
            BindContent bindContent = (BindContent) myClass.getAnnotation(BindContent.class);
            int id = bindContent.value();
            if (id > 0) {
                return inflater.inflate(id, container, false);
            }
        }

        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Class myClass = this.getClass();
        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BindView.class)) {
                BindView bindView = field.getAnnotation(BindView.class);
                int id = bindView.value();
                if (id > 0) {
                    field.setAccessible(true);
                    try {
                        field.set(this, view.findViewById(id));
                    } catch (IllegalAccessException e) {

                    }
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public abstract void initData();

}
