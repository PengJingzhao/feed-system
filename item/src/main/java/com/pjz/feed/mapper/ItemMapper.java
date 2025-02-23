package com.pjz.feed.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjz.feed.entity.Item;
import com.pjz.feed.entity.bo.ItemDetailPageBo;
import com.pjz.feed.entity.vo.ItemDetailPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {


    default Long addItem(Item item) {

        int success = insert(item);

        if (success == 0) {
            throw new IllegalArgumentException("插入用户动态失败");
        }

        return item.getId();
    }


    default Item getItemById(Long itemId) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Item::getId, itemId);
        return selectOne(wrapper);
    }


    default Page<Item> getItemPageByUserId(ItemDetailPageBo itemDetailPageBo) {

        Page<Item> page = new Page<>();
        page.setCurrent(itemDetailPageBo.getPage());
        page.setSize(itemDetailPageBo.getSize());

        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();

        return selectPage(page, wrapper);
    }

    default List<Item> getItemByIds(Set<Long> cachedFeeds) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Item::getId, cachedFeeds);
        return selectList(wrapper);
    }

    default List<Item> getItemByIds(List<Long> itemIds){
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Item::getId, itemIds);
        return selectList(wrapper);
    }

}
