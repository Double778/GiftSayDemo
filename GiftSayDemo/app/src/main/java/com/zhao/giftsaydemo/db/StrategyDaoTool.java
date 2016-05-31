package com.zhao.giftsaydemo.db;

import android.util.Log;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/24.
 * 数据库操作类 封装
 */
public class StrategyDaoTool {
    private StrategyDao strategyDao;

    public StrategyDaoTool() {
        strategyDao = GreenDaoSingle.getInstance().getStrategyDao();
    }

    // 清理没收藏的数据
    public void clean() {
        strategyDao.deleteInTx(this.queryStrategyByIsLiked(false));
    }

    // 添加数据进数据库
    public void addStrategy(Strategy strategy) {
        // 根据url查重
        if (hasThisData(strategy.getUrl())) {
            return;
        }
        strategyDao.insert(strategy);

    }

    // 根据url判断数据库中是否存在此条数据
    public boolean hasThisData(String url) {
        boolean isHas = false;
        if (strategyDao.queryBuilder().where(StrategyDao.Properties.Url.eq(url)).list().size() > 0) {

            if (url.equals(strategyDao.queryBuilder().where(StrategyDao.Properties.Url.eq(url)).list().get(0).getUrl())) {
                isHas = true;
            }
        }
        return isHas;
    }

    // 根据频道查询数据
    public List<Strategy> queryStrategyByChannels(int value) {
        List<Strategy> strategies = strategyDao.queryBuilder().where(StrategyDao.Properties.Channels.eq(value)).orderAsc(StrategyDao.Properties.Id).list();
        return strategies;
    }

    // 根据是否收藏查询数据
    public List<Strategy> queryStrategyByIsLiked(boolean value) {
        List<Strategy> strategies = strategyDao.queryBuilder().where(StrategyDao.Properties.IsLiked.eq(value)).list();
        return strategies;

    }

    // 修改数据
    public void update(Strategy strategy) {
        strategyDao.update(strategy);
    }

    // 根据url查询
    public Strategy queryStrategyByUrl(String value) {
        List<Strategy> strategies = strategyDao.queryBuilder().where(StrategyDao.Properties.Url.eq(value)).list();
        if (strategies.size() > 0) {
            Log.d("StrategyDaoTool", "strategies.size():" + strategies.size());
            return strategies.get(0);
        }
        return null;

    }


}
