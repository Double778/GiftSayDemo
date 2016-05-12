package com.zhao.giftsaydemo.home.bean;

/**
 * Created by 华哥哥 on 16/5/11.
 */
public class TestBean {
    private String content;
    private int ivId;
    private int ivIdSmall;

    public TestBean() {
    }

    public TestBean(String content, int ivId, int ivIdSmall) {
        this.content = content;
        this.ivId = ivId;
        this.ivIdSmall = ivIdSmall;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIvId() {
        return ivId;
    }

    public void setIvId(int ivId) {
        this.ivId = ivId;
    }

    public int getIvIdSmall() {
        return ivIdSmall;
    }

    public void setIvIdSmall(int ivIdSmall) {
        this.ivIdSmall = ivIdSmall;
    }
}
