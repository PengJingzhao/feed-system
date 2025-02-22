package com.pjz.feed.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjz.feed.entity.Likes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikesMapper extends BaseMapper<Likes> {
    default void add(Likes likes) {

        insert(likes);

    }

    default void remove(Likes likes) {

        LambdaUpdateWrapper<Likes> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Likes::getUserId, likes.getUserId())
                .eq(Likes::getItemId, likes.getItemId());

        delete(wrapper);
    }
}
