package com.pjz.feed.controller;

import com.pjz.feed.entity.bo.FollowPageBo;
import com.pjz.feed.entity.bo.ItemDetailPageBo;
import com.pjz.feed.entity.vo.FollowPageVo;
import com.pjz.feed.entity.vo.FollowVo;
import com.pjz.feed.entity.vo.ItemDetailVo;
import com.pjz.feed.entity.vo.UserItemPageVo;
import com.pjz.feed.service.ItemService;
import com.pjz.feed.service.RelationService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @DubboReference
    private ItemService itemService;

    @DubboReference
    private RelationService relationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserFeed(@PathVariable("userId") Long userId, @RequestParam() Long current, @RequestParam() Long size) {

        // 先获取当前用户的关注列表
        FollowPageVo followingPage = relationService.getFollowingPage(new FollowPageBo(userId, current, size));
        List<Long> following = followingPage.getFollowing().stream().map(FollowVo::getId).collect(Collectors.toList());

        // 根据关注列表获取用户发布的动态
        List<UserItemPageVo> items = itemService.getItemsByUserIds(following, current, size);

        return ResponseEntity.ok(items);
    }

}
