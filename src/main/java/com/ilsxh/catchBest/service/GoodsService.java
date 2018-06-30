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
	public void reduceStock(GoodsVo goods) {
		CatchBestGoods g = new CatchBestGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}

}
