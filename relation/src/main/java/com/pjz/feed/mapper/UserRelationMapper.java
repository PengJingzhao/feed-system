package com.pjz.feed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjz.feed.entity.UserRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRelationMapper extends BaseMapper<UserRelation> {
    default boolean add(UserRelation userRelation) {
        int row = insert(userRelation);
        return row > 0;
    }
}
