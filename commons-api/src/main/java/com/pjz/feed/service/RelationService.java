package com.pjz.feed.service;

import com.pjz.feed.entity.bo.FollowPageBo;
import com.pjz.feed.entity.bo.LikeBo;
import com.pjz.feed.entity.bo.UnLikeBo;
import com.pjz.feed.entity.vo.FollowPageVo;

import java.util.List;

public interface RelationService {

    /**
     * 添加关注
     *
     * @param userId   当前用户
     * @param followId 要关注的用户
     * @return 是否添加关注成功
     */
    boolean follow(Long userId, Long followId);

    /**
     * 取消关注
     *
     * @param userId
     * @param followId
     * @return
     */
    boolean unfollow(Long userId, Long followId);

    /**
     * 获取当前用户的关注列表（支持分页）
     *
     * @param followPageBo
     * @return
     */
    FollowPageVo getFollowingPage(FollowPageBo followPageBo);

    /**
     * 获取当前用户的粉丝列表（支持分页）
     *
     * @param followPageBo
     * @return
     */
    FollowPageVo getFollowersPage(FollowPageBo followPageBo);

    /**
     * 检查当前用户是否关注了某个用户
     *
     * @param userId
     * @param followId
     * @return
     */
    boolean check(Long userId, Long followId);

    List<Long> getFollowersList(Long userId);

    /**
     * 用户点赞动态
     *
     * @param likeBo
     * @return
     */
    Void like(LikeBo likeBo);


    /**
     * 用户取消点赞动态
     *
     * @param unLikeBo
     * @return
     */
    Void unlike(UnLikeBo unLikeBo);
}
