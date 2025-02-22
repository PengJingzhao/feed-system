package com.pjz.feed.controller;

import com.pjz.feed.entity.bo.*;
import com.pjz.feed.entity.vo.FollowPageVo;
import com.pjz.feed.entity.vo.FollowVo;
import com.pjz.feed.entity.vo.ItemDetailVo;
import com.pjz.feed.entity.vo.UserItemPageVo;
import com.pjz.feed.result.CommonResult;
import com.pjz.feed.service.FeedService;
import com.pjz.feed.service.ItemService;
import com.pjz.feed.service.RelationService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Resource
    private FeedService feedService;

    @DubboReference
    private RelationService relationService;

    @GetMapping("/user/{userId}")
    public CommonResult<List<ItemDetailVo>> getUserFeed(@PathVariable("userId") Long userId,
                                                        @RequestParam(name = "current", defaultValue = "1") Long current,
                                                        @RequestParam(name = "size", defaultValue = "10") Long size) {
        return CommonResult.operateSuccess(feedService.getUserFeed(userId, current, size));
    }

    @PostMapping("/item/publish")
    public CommonResult<Long> publish(@RequestBody ItemPublishBo itemPublishBo) {
        return CommonResult.operateSuccess(feedService.publish(itemPublishBo));
    }

    @PostMapping("/like")
    public CommonResult<Void> like(@RequestBody LikeBo likeBo){
        return CommonResult.operateSuccess(relationService.like(likeBo));
    }

    @DeleteMapping("/unlike")
    public CommonResult<Void> unlike(@RequestBody UnLikeBo unLikeBo){
        return CommonResult.operateSuccess(relationService.unlike(unLikeBo));
    }

}
