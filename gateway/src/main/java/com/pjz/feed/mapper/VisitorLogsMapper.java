package com.pjz.feed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjz.feed.entity.VisitorsLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisitorLogsMapper extends BaseMapper<VisitorsLog> {

    default void add(VisitorsLog visitorsLog){
        insert(visitorsLog);
    }
}
