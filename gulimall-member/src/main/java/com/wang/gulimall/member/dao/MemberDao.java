package com.wang.gulimall.member.dao;

import com.wang.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author wang
 * @email 1916622321@qq.com
 * @date 2020-11-02 12:52:03
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
