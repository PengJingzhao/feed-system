package com.pjz.feed.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDetailPageVo implements Serializable {

    private List<ItemDetailVo> items;

    private Long total;

    private Long current;

    private Long size;
}
