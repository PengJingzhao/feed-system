package com.pjz.feed.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjz.feed.entity.Likes;
import com.pjz.feed.entity.UserRelation;
import com.pjz.feed.entity.bo.FollowPageBo;
import com.pjz.feed.entity.bo.LikeBo;
import com.pjz.feed.entity.bo.UnLikeBo;
import com.pjz.feed.entity.vo.FollowPageVo;
import com.pjz.feed.entity.vo.FollowVo;
import com.pjz.feed.mapper.LikesMapper;
import com.pjz.feed.mapper.UserRelationMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@DubboService
public class RelationServiceImpl implements RelationService {

    @Resource
    private UserRelationMapper userRelationMapper;

    @Resource
    private LikesMapper likesMapper;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Override
    public boolean follow(Long userId, Long followId) {

        UserRelation userRelation = new UserRelation();
        userRelation.setUserId(userId);
        userRelation.setFollowId(followId);
        userRelation.setCreatedAt(LocalDateTime.now());

        boolean success = userRelationMapper.add(userRelation);

        return success;
    }

    @Override
    public boolean unfollow(Long userId, Long followId) {
        return false;
    }

    @Override
    public FollowPageVo getFollowingPage(FollowPageBo followPageBo) {

        Page<UserRelation> page = userRelationMapper.getFollowingPage(followPageBo);
        List<FollowVo> following = page.getRecords().stream().map(userRelation -> {
            FollowVo followVo = new FollowVo();
            followVo.setId(userRelation.getFollowId());
            return followVo;
        }).collect(Collectors.toList());
        FollowPageVo followPageVo = new FollowPageVo();
        followPageVo.setFollowing(following);
        followPageVo.setCurrent(page.getCurrent());
        followPageVo.setTotal(page.getTotal());

        return followPageVo;
    }

    @Override
    public FollowPageVo getFollowersPage(FollowPageBo followPageBo) {
        return null;
    }

    @Override
    public boolean check(Long userId, Long followId) {
        return false;
    }

    @Override
    public List<Long> getFollowersList(Long userId) {

        return userRelationMapper.getFollowersList(userId);
    }

    @Override
    public Void like(LikeBo likeBo) {

        Likes likes = new Likes();
        BeanUtils.copyProperties(likeBo, likes);

        // 更新数据库
        likesMapper.add(likes);

        // 更新缓存
        redisTemplate.opsForHash().increment("counts:likes", likeBo.getItemId(), 1);

        return null;
    }

    @Override
    public Void unlike(UnLikeBo unLikeBo) {

        Likes likes = new Likes();
        BeanUtils.copyProperties(unLikeBo, likes);

        // 更新数据库
        likesMapper.remove(likes);

        // 更新缓存
        redisTemplate.opsForHash().increment("counts:likes", unLikeBo.getItemId(), -1);

        return null;
    }
}
