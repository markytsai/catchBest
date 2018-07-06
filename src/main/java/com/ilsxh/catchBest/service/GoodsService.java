package com.ilsxh.catchBest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilsxh.catchBest.dao.GoodsDao;
import com.ilsxh.catchBest.domain.CatchBestGoods;
import com.ilsxh.catchBest.vo.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	GoodsDao goodsDao;

	public List<GoodsVo> listGoodsVo() {
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);

	}

	// public GoodsVo getGoodsVoByGoodsId(long goodsId) {
	// return goodsDao.getGoodsVoByGoodsId(goodsId);
	// }
	//
	public boolean reduceStock(GoodsVo goods) {
		CatchBestGoods g = new CatchBestGoods();
		g.setGoodsId(goods.getId());
		int ret = goodsDao.reduceStock(g);
		return ret > 0;
	}

	public void resetStock(List<GoodsVo> goodsList) {
		for (GoodsVo goods : goodsList) {
			CatchBestGoods g = new CatchBestGoods();
			g.setGoodsId(goods.getId());
			g.setStockCount(goods.getStockCount());
			goodsDao.resetStock(g);
		}
	}

}
