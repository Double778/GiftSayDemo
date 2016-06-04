package com.zhao.giftsaydemo.search;


import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseActivity;
import com.zhao.giftsaydemo.search.result.SearchResultFragment;
import com.zhao.giftsaydemo.value.GiftSayValues;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 华哥哥 on 16/5/27.
 */
@BindContent(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements View.OnClickListener, HotWordsFragment.GetWordByOnClick {
    @BindView(R.id.activity_search_input_et)
    private EditText inputEt;
    @BindView(R.id.activity_search_go_tv)
    private TextView goTv;
    @BindView(R.id.activity_search_back_iv)
    private ImageView backIv;
    private String input;
    private HotWordsFragment hotWordsFragment;


    @Override
    public void initData() {
        goTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        hotWordsFragment = new HotWordsFragment();
        hotWordsFragment.setGetWordByOnClick(this);
        inputEt.addTextChangedListener(textWatcher);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_search_replace_fl, hotWordsFragment);
        transaction.commit();


    }

    private void getInputContent() {
        input = getUTF8XMLString(inputEt.getText().toString().trim());
        SharedPreferences sp = getSharedPreferences(GiftSayValues.SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(GiftSayValues.SEARCH_INPUT, input);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_go_tv:
                replaceToSearchResultFragment();


                break;
            case R.id.activity_search_back_iv:
                finish();
                break;
        }
    }

    // UTF-8转换
    public String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8 = "";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }

    @Override
    public void getWord(String word) {
        inputEt.setText(word);
        replaceToSearchResultFragment();

    }

    public void replaceToSearchResultFragment() {
        getInputContent();
        if (input.length() > 0) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_search_replace_fl, new SearchResultFragment());
            transaction.commit();
        } else {
            Toast.makeText(this, "请先输入搜索内容", Toast.LENGTH_SHORT).show();
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            input = String.valueOf(s);
            if (input.length() >0 ){

                inputEt.setSelection(input.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (input.length() == 0) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_search_replace_fl, hotWordsFragment);
                transaction.commit();
            }
        }
    };

}
