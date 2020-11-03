package com.wang.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.utils.PageUtils;
import com.wang.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author wang
 * @email 1916622321@qq.com
 * @date 2020-11-02 12:52:03
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

