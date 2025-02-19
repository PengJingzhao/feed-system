package com.pjz.feed.service;

import com.pjz.feed.entity.bo.FollowPageBo;
import com.pjz.feed.entity.bo.ItemPublishBo;
import com.pjz.feed.entity.vo.FollowPageVo;
import com.pjz.feed.entity.vo.FollowVo;
import com.pjz.feed.entity.vo.UserItemPageVo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@DubboService
public class FeedServiceImpl implements FeedService {

    @DubboReference
    private ItemService itemService;

    @DubboReference
    private RelationService relationService;

    @Override
    public Long publish(ItemPublishBo itemPublishBo) {
        Long itemId = itemService.publish(itemPublishBo);
        return itemId;
    }

    @Override
    public List<UserItemPageVo> getUserFeed(Long userId, Long current, Long size) {
        // 先获取当前用户的关注列表
        FollowPageVo followingPage = relationService.getFollowingPage(new FollowPageBo(userId, current, size));
        List<Long> following = followingPage.getFollowing().stream().map(FollowVo::getId).collect(Collectors.toList());

        // 根据关注列表获取用户发布的动态
        List<UserItemPageVo> items = itemService.getItemsByUserIds(following, current, size);
        return items;
    }
}
