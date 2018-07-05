package com.ilsxh.catchBest.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ilsxh.catchBest.domain.CatchBestUser;

@Mapper
public interface CatchBestUserDao {

	@Select("select * from catchbest_user where id = #{id}")
	public CatchBestUser getById(@Param("id") long id);

	@Update("update catchbest_user set password = #{password} where id = #{id}")
	public void update(CatchBestUser toBeUpdate);
}
