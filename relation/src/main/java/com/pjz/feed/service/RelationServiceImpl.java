package com.pjz.feed.service;


import com.pjz.feed.entity.UserRelation;
import com.pjz.feed.entity.bo.FollowPageBo;
import com.pjz.feed.entity.vo.FollowPageVo;
import com.pjz.feed.mapper.UserRelationMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@DubboService
public class RelationServiceImpl implements RelationService {

    @Resource
    private UserRelationMapper userRelationMapper;

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
        return null;
    }

    @Override
    public FollowPageVo getFollowersPage(FollowPageBo followPageBo) {
        return null;
    }

    @Override
    public boolean check(Long userId, Long followId) {
        return false;
    }
}
