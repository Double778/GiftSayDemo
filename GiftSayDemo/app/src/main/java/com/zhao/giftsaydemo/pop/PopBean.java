package com.zhao.giftsaydemo.pop;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/12.
 * 热门页面数据类
 */
public class PopBean {



    private int code;


    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {

        private PagingBean paging;

        private List<ItemsBean> items;

        public PagingBean getPaging() {
            return paging;
        }

        public void setPaging(PagingBean paging) {
            this.paging = paging;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class PagingBean {
            private String next_url;

            public String getNext_url() {
                return next_url;
            }

            public void setNext_url(String next_url) {
                this.next_url = next_url;
            }
        }

        public static class ItemsBean implements Parcelable{


            private DataBean1 data;
            private String type;

            protected ItemsBean(Parcel in) {
                type = in.readString();
            }

            public static final Creator<ItemsBean> CREATOR = new Creator<ItemsBean>() {
                @Override
                public ItemsBean createFromParcel(Parcel in) {
                    return new ItemsBean(in);
                }

                @Override
                public ItemsBean[] newArray(int size) {
                    return new ItemsBean[size];
                }
            };

            public DataBean1 getData() {
                return data;
            }

            public void setData(DataBean1 data) {
                this.data = data;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(type);
            }

            public static class DataBean1 implements Parcelable{
                private Object brand_id;
                private Object brand_order;
                private int category_id;
                private String cover_image_url;
                private String cover_webp_url;
                private int created_at;
                private String description;
                private int editor_id;
                private int favorites_count;
                private int id;
                private boolean is_favorite;
                private String keywords;
                private String name;
                private String price;
                private String purchase_id;
                private Object purchase_shop_id;
                private int purchase_status;
                private int purchase_type;
                private String purchase_url;
                private int subcategory_id;
                private int updated_at;
                private String url;
                private List<?> ad_monitors;
                private List<String> image_urls;
                private List<?> post_ids;
                private List<String> webp_urls;

                protected DataBean1(Parcel in) {
                    category_id = in.readInt();
                    cover_image_url = in.readString();
                    cover_webp_url = in.readString();
                    created_at = in.readInt();
                    description = in.readString();
                    editor_id = in.readInt();
                    favorites_count = in.readInt();
                    id = in.readInt();
                    is_favorite = in.readByte() != 0;
                    keywords = in.readString();
                    name = in.readString();
                    price = in.readString();
                    purchase_id = in.readString();
                    purchase_status = in.readInt();
                    purchase_type = in.readInt();
                    purchase_url = in.readString();
                    subcategory_id = in.readInt();
                    updated_at = in.readInt();
                    url = in.readString();
                    image_urls = in.createStringArrayList();
                    webp_urls = in.createStringArrayList();
                }

                public static final Creator<DataBean1> CREATOR = new Creator<DataBean1>() {
                    @Override
                    public DataBean1 createFromParcel(Parcel in) {
                        return new DataBean1(in);
                    }

                    @Override
                    public DataBean1[] newArray(int size) {
                        return new DataBean1[size];
                    }
                };

                public Object getBrand_id() {
                    return brand_id;
                }

                public void setBrand_id(Object brand_id) {
                    this.brand_id = brand_id;
                }

                public Object getBrand_order() {
                    return brand_order;
                }

                public void setBrand_order(Object brand_order) {
                    this.brand_order = brand_order;
                }

                public int getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

                public String getCover_image_url() {
                    return cover_image_url;
                }

                public void setCover_image_url(String cover_image_url) {
                    this.cover_image_url = cover_image_url;
                }

                public String getCover_webp_url() {
                    return cover_webp_url;
                }

                public void setCover_webp_url(String cover_webp_url) {
                    this.cover_webp_url = cover_webp_url;
                }

                public int getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(int created_at) {
                    this.created_at = created_at;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public int getEditor_id() {
                    return editor_id;
                }

                public void setEditor_id(int editor_id) {
                    this.editor_id = editor_id;
                }

                public int getFavorites_count() {
                    return favorites_count;
                }

                public void setFavorites_count(int favorites_count) {
                    this.favorites_count = favorites_count;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public boolean isIs_favorite() {
                    return is_favorite;
                }

                public void setIs_favorite(boolean is_favorite) {
                    this.is_favorite = is_favorite;
                }

                public String getKeywords() {
                    return keywords;
                }

                public void setKeywords(String keywords) {
                    this.keywords = keywords;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getPurchase_id() {
                    return purchase_id;
                }

                public void setPurchase_id(String purchase_id) {
                    this.purchase_id = purchase_id;
                }

                public Object getPurchase_shop_id() {
                    return purchase_shop_id;
                }

                public void setPurchase_shop_id(Object purchase_shop_id) {
                    this.purchase_shop_id = purchase_shop_id;
                }

                public int getPurchase_status() {
                    return purchase_status;
                }

                public void setPurchase_status(int purchase_status) {
                    this.purchase_status = purchase_status;
                }

                public int getPurchase_type() {
                    return purchase_type;
                }

                public void setPurchase_type(int purchase_type) {
                    this.purchase_type = purchase_type;
                }

                public String getPurchase_url() {
                    return purchase_url;
                }

                public void setPurchase_url(String purchase_url) {
                    this.purchase_url = purchase_url;
                }

                public int getSubcategory_id() {
                    return subcategory_id;
                }

                public void setSubcategory_id(int subcategory_id) {
                    this.subcategory_id = subcategory_id;
                }

                public int getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(int updated_at) {
                    this.updated_at = updated_at;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public List<?> getAd_monitors() {
                    return ad_monitors;
                }

                public void setAd_monitors(List<?> ad_monitors) {
                    this.ad_monitors = ad_monitors;
                }

                public List<String> getImage_urls() {
                    return image_urls;
                }

                public void setImage_urls(List<String> image_urls) {
                    this.image_urls = image_urls;
                }

                public List<?> getPost_ids() {
                    return post_ids;
                }

                public void setPost_ids(List<?> post_ids) {
                    this.post_ids = post_ids;
                }

                public List<String> getWebp_urls() {
                    return webp_urls;
                }

                public void setWebp_urls(List<String> webp_urls) {
                    this.webp_urls = webp_urls;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(category_id);
                    dest.writeString(cover_image_url);
                    dest.writeString(cover_webp_url);
                    dest.writeInt(created_at);
                    dest.writeString(description);
                    dest.writeInt(editor_id);
                    dest.writeInt(favorites_count);
                    dest.writeInt(id);
                    dest.writeByte((byte) (is_favorite ? 1 : 0));
                    dest.writeString(keywords);
                    dest.writeString(name);
                    dest.writeString(price);
                    dest.writeString(purchase_id);
                    dest.writeInt(purchase_status);
                    dest.writeInt(purchase_type);
                    dest.writeString(purchase_url);
                    dest.writeInt(subcategory_id);
                    dest.writeInt(updated_at);
                    dest.writeString(url);
                    dest.writeStringList(image_urls);
                    dest.writeStringList(webp_urls);
                }
            }
        }
    }
}
