package com.wang.gulimall.order.dao;

import com.wang.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author wang
 * @email 1916622321@qq.com
 * @date 2020-11-02 12:56:28
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}