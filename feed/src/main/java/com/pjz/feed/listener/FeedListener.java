package com.pjz.feed.listener;

import com.pjz.feed.service.FeedService;
import com.pjz.feed.service.RelationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

@Component
@Slf4j
public class FeedListener {

    @DubboReference
    private RelationService relationService;

    @Resource
    private FeedService feedService;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @KafkaListener(topics = "feed_topic", groupId = "feed_group")
    public void handleFeedMessage(ConsumerRecord<String, String> record) {
        Long userId = Long.valueOf(record.key());
        Long itemId = Long.valueOf(record.value());

        // 获取粉丝列表
        List<Long> followers = relationService.getFollowersList(userId);

        // 将动态写入feed缓存
        followers.forEach(followerId -> redisTemplate.opsForZSet().add("feed:" + followerId, itemId, System.currentTimeMillis()));

        // 将feed异步写入数据库
        // 为了不阻塞kafka消费方法，应该使用异步的方法和数据库交互
        feedService.saveFeedsToDatabase(followers, itemId);
    }

}
