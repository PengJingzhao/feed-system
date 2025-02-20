package com.pjz.feed.service;

import com.pjz.feed.entity.bo.ItemPublishBo;
import com.pjz.feed.entity.vo.ItemDetailVo;
import com.pjz.feed.entity.vo.UserItemPageVo;

import java.util.List;

public interface FeedService {

    Long publish(ItemPublishBo itemPublishBo);

    List<ItemDetailVo> getUserFeed(Long userId, Long current, Long size);
}
