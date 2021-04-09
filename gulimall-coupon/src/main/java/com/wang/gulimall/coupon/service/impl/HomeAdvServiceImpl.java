package com.wang.gulimall.coupon.service.impl;

import com.wang.common.utils.PageUtils;
import com.wang.common.utils.Query;
import com.wang.gulimall.coupon.dao.HomeAdvDao;
import com.wang.gulimall.coupon.entity.HomeAdvEntity;
import com.wang.gulimall.coupon.service.HomeAdvService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("homeAdvService")
public class HomeAdvServiceImpl extends ServiceImpl<HomeAdvDao, HomeAdvEntity> implements HomeAdvService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeAdvEntity> page = this.page(
                new Query<HomeAdvEntity>().getPage(params),
                new QueryWrapper<HomeAdvEntity>()
        );

        return new PageUtils(page);
    }

}