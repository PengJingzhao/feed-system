package com.pjz.feed.service;

import com.pjz.feed.entity.Feed;
import com.pjz.feed.entity.bo.FollowPageBo;
import com.pjz.feed.entity.bo.ItemPublishBo;
import com.pjz.feed.entity.vo.FollowPageVo;
import com.pjz.feed.entity.vo.FollowVo;
import com.pjz.feed.entity.vo.ItemDetailVo;
import com.pjz.feed.entity.vo.UserItemPageVo;
import com.pjz.feed.mapper.FeedMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@DubboService
@Slf4j
public class FeedServiceImpl implements FeedService {

    @DubboReference
    private ItemService itemService;

    @DubboReference
    private RelationService relationService;

    @Resource
    private FeedMapper feedMapper;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Override
    public Long publish(ItemPublishBo itemPublishBo) {
        Long itemId = itemService.publish(itemPublishBo);
        return itemId;
    }

    @Override
    public List<ItemDetailVo> getUserFeed(Long userId, Long current, Long size) {
        // 先获取当前用户的关注列表
        FollowPageVo followingPage = relationService.getFollowingPage(new FollowPageBo(userId, current, size));
        Assert.notNull(followingPage, "当前用户没有关注任何人");
        List<Long> following = followingPage.getFollowing().stream().map(FollowVo::getId).collect(Collectors.toList());

        // 先从缓存中获取用户发布的动态
        Set<Long> cachedFeeds = redisTemplate.opsForZSet().reverseRange("feed:" + userId, (current - 1) * size, current * size - 1);
        if (cachedFeeds != null && !cachedFeeds.isEmpty()) {
            List<ItemDetailVo> itemDetailVoList = itemService.getItemDetailByItemIds(cachedFeeds);
            return itemDetailVoList;
        }


        // 时间倒叙查询用户发布的动态列表
        List<Feed> feeds = feedMapper.findByUserIdByCreatedAtByDesc(following, current, size);

        // 查询动态详情列表
        List<Long> itemIds = feeds.stream().map(Feed::getItemId).collect(Collectors.toList());
        if (itemIds.isEmpty()) {
            throw new IllegalArgumentException("该用户的关注用户没有发布动态");
        }
        List<ItemDetailVo> itemDetailVos = itemService.getItemDetailByIds(itemIds);

        // 将查询的动态缓存起来
        feeds.forEach(feed -> redisTemplate.opsForZSet().add("feed:" + userId, feed.getItemId(), feed.getCreatedAt().getTime()));

        return itemDetailVos;
    }

    @Override
    @Async
    public void saveFeedsToDatabase(List<Long> followers, Long itemId) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<Feed> feeds = followers.stream().map(followerId -> {
            Feed feed = new Feed();
            feed.setItemId(itemId);
            feed.setUserId(followerId);
            feed.setCreatedAt(timestamp);
            return feed;
        }).collect(Collectors.toList());

        feedMapper.batchInsert(feeds);
    }
}
