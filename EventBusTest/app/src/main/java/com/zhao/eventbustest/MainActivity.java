package com.zhao.eventbustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends AppCompatActivity {
    /**
     * 需求: 不同的Activity之间传值
     */
    TextView showTv;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册事件
        EventBus.getDefault().register(this);

        showTv = (TextView) findViewById(R.id.showTv);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InputActivity.class));
            }
        });
    }

    // 线程的类型
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getData(Message data) {
        showTv.setText(data.getMessage());
    }

    /**
     * 事件在哪个线程中发布出来, 那么事件就在哪个线程之中运行
     * 保证一点 在这里尽量避免耗时的操作, 因为它会阻塞事件的传递
     */
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void postGetData(){
        Log.d("PostThread", Thread.currentThread().getName());
    }

    /**
     * 无论事件在哪个线程发布出来, 该事件的处理都会在UI线程中运行
     * 该方法可以用来刷新UI, 但是同样要避免耗时操作
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getMessage(Message message){

        Log.d("MainThread", Thread.currentThread().getName());

    }

    /**
     * 如果事件是在UI线程中发布出来的, 那么该事件处理都会在新的线程中运行
     * 如果事件本来就是在子线程中发布出来的, 那么处理该事件也在发布事件的线程中执行
     * 注意: 这里不能进行UI更新
     */
    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void backgroundGetData(Message message){
        Log.d("BackgroundThread", Thread.currentThread().getName());
    }

    /**
     * 无论事件在哪个线程中发布出来, 该事件都在子线程中执行
     * 注意: 这里也不能进行UI更新
     */
    @Subscribe(threadMode = ThreadMode.Async)
    public void asyncTaskGetData(Message message){
        Log.d("AsyncTaskThread", Thread.currentThread().getName());
    }



    @Override
    protected void onDestroy() {
        // 取消注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
