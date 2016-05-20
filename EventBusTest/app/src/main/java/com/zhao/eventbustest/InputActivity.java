package com.zhao.eventbustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EventBus.getDefault().register(this);
        setContentView(R.layout.activity_input);
        editText = (EditText) findViewById(R.id.input_et);
        findViewById(R.id.start_btn).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
       // Message message = new Message(editText.getText().toString());
        // 发送事件
       // EventBus.getDefault().post(message);
        startActivity(new Intent(this, SecondActivity.class));
    }

//    @Override
//    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
//
//        super.onDestroy();
//    }


//    /**
//     * 事件在哪个线程中发布出来, 那么事件就在哪个线程之中运行
//     * 保证一点 在这里尽量避免耗时的操作, 因为它会阻塞事件的传递
//     */
//    @Subscribe(threadMode = ThreadMode.PostThread)
//    public void postGetData(){
//        Log.d("PostThread", Thread.currentThread().getName());
//    }
//
//    /**
//     * 无论事件在哪个线程发布出来, 该事件的处理都会在UI线程中运行
//     * 该方法可以用来刷新UI, 但是同样要避免耗时操作
//     */
//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void getMessage(Message message){
//        editText.setText(message.getMessage());
//
//        Log.d("MainThread", Thread.currentThread().getName());
//
//    }
//
//    /**
//     * 如果事件是在UI线程中发布出来的, 那么该事件处理都会在新的线程中运行
//     * 如果事件本来就是在子线程中发布出来的, 那么处理该事件也在发布事件的线程中执行
//     * 注意: 这里不能进行UI更新
//     */
//    @Subscribe(threadMode = ThreadMode.BackgroundThread)
//    public void backgroundGetData(){
//        Log.d("BackgroundThread", Thread.currentThread().getName());
//    }
//
//    /**
//     * 无论事件在哪个线程中发布出来, 该事件都在子线程中执行
//     * 注意: 这里也不能进行UI更新
//     */
//    @Subscribe(threadMode = ThreadMode.Async)
//    public void asyncTaskGetData(){
//        Log.d("AsyncTaskThread", Thread.currentThread().getName());
//    }
}
