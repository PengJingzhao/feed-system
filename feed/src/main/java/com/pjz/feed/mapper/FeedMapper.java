package com.pjz.feed.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjz.feed.entity.Feed;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper extends BaseMapper<Feed> {
    default List<Feed> findByUserIdByCreatedAtByDesc(List<Long> following, Long current, Long size){
        Page<Feed> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        LambdaQueryWrapper<Feed> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Feed::getId, following);
        Page<Feed> feedPage = selectPage(page, wrapper);
        return feedPage.getRecords();
    }
}
