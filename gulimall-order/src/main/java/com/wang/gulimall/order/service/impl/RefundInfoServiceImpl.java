package com.wang.gulimall.order.service.impl;

import com.wang.common.utils.PageUtils;
import com.wang.common.utils.Query;
import com.wang.gulimall.order.dao.RefundInfoDao;
import com.wang.gulimall.order.entity.RefundInfoEntity;
import com.wang.gulimall.order.service.RefundInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;


@Service("refundInfoService")
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoDao, RefundInfoEntity> implements RefundInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RefundInfoEntity> page = this.page(
                new Query<RefundInfoEntity>().getPage(params),
                new QueryWrapper<RefundInfoEntity>()
        );

        return new PageUtils(page);
    }

    public static <T> void test(Supplier<T> supplier) throws Exception {
        System.out.println(1);
        test1(supplier);

    }

    public static <T> void test1(Supplier<T> supplier) throws Exception {
        System.out.println(2);
        System.out.println(supplier.get());
    }

    @FunctionalInterface
    public interface BaseConsumer<T> {

        T accept() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        test(()->{
            try {
                System.out.println("test");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });


    }
}