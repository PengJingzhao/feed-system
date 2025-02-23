package com.pjz.feed.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjz.feed.entity.Item;
import com.pjz.feed.entity.bo.ItemDetailPageBo;
import com.pjz.feed.entity.bo.ItemPublishBo;
import com.pjz.feed.entity.vo.ItemDetailPageVo;
import com.pjz.feed.entity.vo.ItemDetailVo;
import com.pjz.feed.entity.vo.UserItemPageVo;
import com.pjz.feed.mapper.ItemMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@DubboService
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public Long publish(ItemPublishBo itemPublishBo) {

        Item item = new Item();

        BeanUtils.copyProperties(itemPublishBo, item);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        // 将新数据插入数据库
        Long itemId = itemMapper.addItem(item);

        // 发送消息，提醒下游服务更新数据
        kafkaTemplate.send("feed_topic",String.valueOf(itemPublishBo.getUserId()),String.valueOf(itemId));

        return itemId;
    }

    @Override
    public ItemDetailVo getItemDetail(Long itemId) {

        Item item = itemMapper.getItemById(itemId);

        ItemDetailVo itemDetailVo = new ItemDetailVo();
        BeanUtils.copyProperties(item, itemDetailVo);

        return itemDetailVo;
    }

    @Override
    public ItemDetailPageVo getItemDetailPage(ItemDetailPageBo itemDetailPageBo) {

        Page<Item> page = itemMapper.getItemPageByUserId(itemDetailPageBo);
        List<Item> items = page.getRecords();

        ItemDetailPageVo itemDetailPageVo = new ItemDetailPageVo();

        List<ItemDetailVo> itemDetailVoList = items.stream().map(item -> {

            ItemDetailVo itemDetailVo = new ItemDetailVo();
            BeanUtils.copyProperties(item, itemDetailVo);

            return itemDetailVo;
        }).collect(Collectors.toList());
        itemDetailPageVo.setItems(itemDetailVoList);

        itemDetailPageVo.setSize(page.getSize());
        itemDetailPageVo.setTotal(page.getTotal());
        itemDetailPageVo.setCurrent(page.getCurrent());

        return itemDetailPageVo;
    }


    @Override
    public List<ItemDetailVo> getItemDetailByItemIds(Set<Long> cachedFeeds) {

        List<Item> items = itemMapper.getItemByIds(cachedFeeds);

        List<ItemDetailVo> itemDetailVoList = items.stream().map(item -> {
            ItemDetailVo itemDetailVo = new ItemDetailVo();
            BeanUtils.copyProperties(item, itemDetailVo);
            return itemDetailVo;
        }).collect(Collectors.toList());

        return itemDetailVoList;
    }

    @Override
    public List<ItemDetailVo> getItemDetailByIds(List<Long> itemIds) {

        List<Item> items = itemMapper.getItemByIds(itemIds);
        List<ItemDetailVo> itemDetailVoList = items.stream().map(item -> {
            ItemDetailVo itemDetailVo = new ItemDetailVo();
            BeanUtils.copyProperties(item, itemDetailVo);
            return itemDetailVo;
        }).collect(Collectors.toList());

        return itemDetailVoList;
    }
}
