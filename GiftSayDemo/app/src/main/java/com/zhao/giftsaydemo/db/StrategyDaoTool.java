package com.zhao.giftsaydemo.db;


import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 华哥哥 on 16/5/24.
 */
public class StrategyDaoTool {
    private StrategyDao strategyDao;

    public StrategyDaoTool() {
        strategyDao = GreenDaoSingle.getInstance().getStrategyDao();
    }

    public void clean(){
        strategyDao.deleteInTx(this.queryStrategyByIsLiked(false));
    }

    public void addStrategy(Strategy strategy) {
        if (hasThisData(strategy.getUrl())) {
            return;
        }
        strategyDao.insert(strategy);

    }

    public boolean hasThisData(String url) {
        boolean isHas = false;
        if (strategyDao.queryBuilder().where(StrategyDao.Properties.Url.eq(url)).list().size() > 0) {

            if (url.equals(strategyDao.queryBuilder().where(StrategyDao.Properties.Url.eq(url)).list().get(0).getUrl())) {
                isHas = true;
            }
        }
        return isHas;
    }

    public List<Strategy> queryStrategyByChannels(int value) {
        List<Strategy> strategies = strategyDao.queryBuilder().where(StrategyDao.Properties.Channels.eq(value)).orderAsc(StrategyDao.Properties.Id).list();
        return strategies;
    }

    public List<Strategy> queryStrategyByIsLiked(boolean value) {
        List<Strategy> strategies = strategyDao.queryBuilder().where(StrategyDao.Properties.IsLiked.eq(value)).list();
        return strategies;

    }

    public void update(Strategy strategy){
        strategyDao.update(strategy);
    }

    public Strategy queryStrategyByUrl(String value) {
        List<Strategy> strategies = strategyDao.queryBuilder().where(StrategyDao.Properties.Url.eq(value)).list();
        Log.d("StrategyDaoTool", "strategies.size():" + strategies.size());
        if (strategies.size() > 0) {
            return strategies.get(0);
        }
        return null;

    }


}
