package com.pjz.feed.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjz.feed.entity.Item;
import com.pjz.feed.entity.bo.ItemDetailPageBo;
import com.pjz.feed.entity.vo.ItemDetailPageVo;

public interface ItemMapper extends BaseMapper<Item> {


    default Long addItem(Item item){

        int success = insert(item);

        if (success!=1){
            throw new IllegalArgumentException("插入用户动态失败");
        }

        return item.getId();
    };

    default Item getItemById(Long itemId){
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Item::getId, itemId);
        return selectOne(wrapper);
    };

    default Page<Item> getItemPageByUserId(ItemDetailPageBo itemDetailPageBo){

        Page<Item> page = new Page<>();
        page.setCurrent(itemDetailPageBo.getPage());
        page.setSize(itemDetailPageBo.getSize());

        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();

        return selectPage(page,wrapper);
    }
}
