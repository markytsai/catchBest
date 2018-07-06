package com.ilsxh.catchBest.dao;
/**
* @description
* @author Caizhenya mail:tsaizhenya@gmail.com
* @date   2018年6月30日 上午10:23:18
*
*/

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ilsxh.catchBest.domain.CatchBestGoods;
import com.ilsxh.catchBest.vo.GoodsVo;

@Mapper
public interface GoodsDao {
	@Select("select g.*, cg.sale_price, cg.stock_count, cg.start_date, cg.end_date from catchbest_goods cg left join goods g on cg.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();

	@Select("select g.*, cg.sale_price, cg.stock_count, cg.start_date, cg.end_date from catchbest_goods cg left join goods g on cg.goods_id = g.id where goods_id=#{goodsId}")
	public GoodsVo getGoodsVoByGoodsId(long goodsId);

	@Update("update catchbest_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
	public int reduceStock(CatchBestGoods g);

	@Update("update catchbest_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
	public int resetStock(CatchBestGoods g);

}
