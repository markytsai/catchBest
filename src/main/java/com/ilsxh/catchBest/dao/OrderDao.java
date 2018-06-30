package com.ilsxh.catchBest.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.ilsxh.catchBest.domain.CatchBestOrder;
import com.ilsxh.catchBest.domain.OrderInfo;

@Mapper
public interface OrderDao {	

	@Select("select * from catchbest_order where user_id=#{userId} and goods_id=#{goodsId}")
	public CatchBestOrder getCatchbestOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

	@Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
			+ "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
	@SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
	public long insert(OrderInfo orderInfo);

	@Insert("insert into catchbest_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
	public int insertCatchbestOrder(CatchBestOrder catchbestOrder);

}
