package com.zhao.giftsaydemo.base;


/**
 * Created by 华哥哥 on 16/5/17.
 */
public abstract class TestFragment extends BaseFragment {

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }

    }


    private void onInVisible() {

    }

    private void onVisible() {
        lazyLoad();
    }
    protected abstract void lazyLoad();



}
