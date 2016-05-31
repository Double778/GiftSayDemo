package com.zhao.giftsaydemo.db;

import android.database.sqlite.SQLiteDatabase;

import com.zhao.giftsaydemo.base.MyApplication;

/**
 * Created by 华哥哥 on 16/5/23.
 * GreenDao单例
 */
public class GreenDaoSingle {
    private SQLiteDatabase db;// 数据库
    private DaoMaster daoMaster;// 管理者
    private DaoSession daoSession;// 会话者
    private DaoMaster.DevOpenHelper helper;
    private StrategyDao strategyDao;

    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(MyApplication.context, "GiftSay.db", null);
        }
        return helper;
    }

    public SQLiteDatabase getDb() {
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    public StrategyDao getStrategyDao() {
        if (strategyDao == null) {
            strategyDao = getDaoSession().getStrategyDao();
        }
        return strategyDao;
    }


    private static GreenDaoSingle ourInstance = new GreenDaoSingle();

    public static GreenDaoSingle getInstance() {
        return ourInstance;
    }

    private GreenDaoSingle() {
    }
}
