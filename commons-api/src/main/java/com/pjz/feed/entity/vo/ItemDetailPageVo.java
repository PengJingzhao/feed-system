package com.pjz.feed.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailPageVo {

    private List<ItemDetailVo> items;

    private Long total;

    private Long current;

    private Long size;
}
