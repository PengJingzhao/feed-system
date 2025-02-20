package com.pjz.feed.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjz.feed.entity.UserRelation;
import com.pjz.feed.entity.bo.FollowPageBo;
import com.pjz.feed.entity.vo.FollowVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper
public interface UserRelationMapper extends BaseMapper<UserRelation> {
    default boolean add(UserRelation userRelation) {
        int row = insert(userRelation);
        return row > 0;
    }

    default Page<UserRelation> getFollowingPage(FollowPageBo followPageBo) {
        Page<UserRelation> page = new Page<>();
        page.setCurrent(followPageBo.getCurrent());
        page.setSize(followPageBo.getSize());

        LambdaQueryWrapper<UserRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRelation::getUserId, followPageBo.getUserId());
        return selectPage(page, wrapper);
    }

    default List<Long> getFollowersList(Long userId) {
        LambdaQueryWrapper<UserRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRelation::getFollowId, userId)
                .select(UserRelation::getUserId);
        List<Long> followers = selectObjs(wrapper).stream().map(o -> (Long) o).collect(Collectors.toList());
        return followers;
    }
}
