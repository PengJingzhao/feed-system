package com.pjz.feed.service;

import com.pjz.feed.entity.bo.ItemDetailPageBo;
import com.pjz.feed.entity.bo.ItemPublishBo;
import com.pjz.feed.entity.vo.ItemDetailPageVo;
import com.pjz.feed.entity.vo.ItemDetailVo;
import com.pjz.feed.entity.vo.UserItemPageVo;

import java.util.List;

public interface ItemService {

    /**
     * 用户发布动态，支持文本，图像和视频
     *
     * @param itemPublishBo
     * @return 所发布的动态的Id
     */
    Long publish(ItemPublishBo itemPublishBo);

    /**
     * 通过id查询动态详情
     * @param itemId
     * @return
     */
    ItemDetailVo getItemDetail(Long itemId);


    /**
     * 查询某个用户发布的动态列表，支持分页
     * @param itemDetailPageBo
     * @return
     */
    ItemDetailPageVo getItemDetailPage(ItemDetailPageBo itemDetailPageBo);


    List<UserItemPageVo> getItemsByUserIds(List<Long> following, Long current, Long size);
}
