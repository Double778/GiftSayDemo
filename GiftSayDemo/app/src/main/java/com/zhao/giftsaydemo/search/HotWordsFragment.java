package com.zhao.giftsaydemo.search;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zhao.giftsaydemo.R;
import com.zhao.giftsaydemo.annotation.BindContent;
import com.zhao.giftsaydemo.annotation.BindView;
import com.zhao.giftsaydemo.base.BaseFragment;
import com.zhao.giftsaydemo.util.VolleySingle;

/**
 * Created by 华哥哥 on 16/5/27.
 */
@BindContent(R.layout.fragment_hot_words)
public class HotWordsFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.fragment_hot_words_gv)
    private GridView gridView;
    private HotWordsBean hotWordsBean;
    private String word;
    private GetWordByOnClick getWordByOnClick;

    public void setGetWordByOnClick(GetWordByOnClick getWordByOnClick) {
        this.getWordByOnClick = getWordByOnClick;
    }

    @Override
    public void initData() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.item_search_activity_hot_words_fragment_gv);

        VolleySingle.addRequest("http://api.liwushuo.com/v2/search/hot_words", HotWordsBean.class, new Response.Listener<HotWordsBean>() {
            @Override
            public void onResponse(HotWordsBean response) {
                hotWordsBean = response;
                for (int i = 0; i < response.getData().getHot_words().size(); i++) {
                    adapter.add(response.getData().getHot_words().get(i));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        word = hotWordsBean.getData().getHot_words().get(position);
        getWordByOnClick.getWord(word);
    }

    public interface GetWordByOnClick {
        void getWord(String word);
    }

}
