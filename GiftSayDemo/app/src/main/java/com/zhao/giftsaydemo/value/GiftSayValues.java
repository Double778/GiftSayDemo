package com.zhao.giftsaydemo.value;

/**
 * Created by 华哥哥 on 16/6/4.
 */
public class GiftSayValues {
    public static final String LIKE_CHANGED_RECEIVER = "com.zhao.giftsaydemo.LikeChanged";


    public static final String INTENT_CHANNELS_ID = "Id";
    public static final String INTENT_CHANNELS_NAME = "name";

    // 主页
    public static final String HOME_SLIDE_SHOW_URL = "http://api.liwushuo.com/v2/banners";
    public static final String HOME_SUBJECT_URL = "http://api.liwushuo.com/v2/secondary_banners?gender=2&generation=1";
    public static final String HOME_CHANNELS_URL_START = "http://api.liwushuo.com/v2/channels/";
    public static final String HOME_CHANNELS_URL_END = "/items?limit=20&ad=2&gender=2&offset=0&generation=1";
    public static final String HOME_TAB_URL = "http://api.liwushuo.com/v2/channels/preset?gender=1&generation=4";
    public static final String HOME_FIRST_CHANNELS_URL = "http://api.liwushuo.com/v2/channels/108/items?limit=20&ad=2&gender=2&offset=0&generation=1";
    public static final int HOME_FIRST_CHANNELS_ID = 108;

    // 礼物
    public static final String CATEGORY_GIFT_URL = "http://api.liwushuo.com/v2/item_categories/tree";
    public static final String GIFT_CHANNELS_URL_START = "http://api.liwushuo.com/v2/item_subcategories/";
    public static final String GIFT_CHANNELS_URL_END = "/items?limit=20&offset=20";
    public static final String INTENT_GIFT_CHANNELS_TAG = "tag";
    public static final String INTENT_GIFT_DETAILS_DATA = "data";
    public static final int FROM_GIFT_CHANNELS_ACTIVITY = 1;
    public static final String INTENT_BUY_URL = "buyUrl";

    // 攻略
    public static final String CATEGORY_STRATEGY_URL = "http://api.liwushuo.com/v2/channel_groups/all";
    public static final String CATEGORY_STRATEGY_HEAD_VIEW_URL = "http://api.liwushuo.com/v2/collections?limit=20&offset=0";
    public static final String STRATEGY_ALL_SUBJECT_DETAILS_URL_START = "http://www.liwushuo.com/posts/";
    public static final int FROM_SUBJECT_CHANNELS_ACTIVITY = 1;
    public static final String STRATEGY_SUBJECT_CHANNELS_URL_START = "http://api.liwushuo.com/v2/collections/";
    public static final String STRATEGY_SUBJECT_CHANNELS_URL_END = "/posts?limit=20&offset=0";
    public static final String STRATEGY_CHANNELS_URL_START = "http://api.liwushuo.com/v2/channels/";
    public static final String STRATEGY_CHANNELS_URL_END = "/items?limit=20&offset=0";
    public static final String INTENT_SUBJECT_CHANNELS_TAG = "tag";
    public static final String INTENT_STRATEGY_DETAILS_URL = "url";

    // 热门
    public static final String POP_URL = "http://api.liwushuo.com/v2/items?gender=1&generation=4&limit=50&oddset=0";

    //SharedPreferences
    public static final String SP_NAME = "Input";
    // 搜索
    public static final String SEARCH_INPUT = "input";
    public static final String SEARCH_HOT_WORDS_URL ="http://api.liwushuo.com/v2/search/hot_words";

}
