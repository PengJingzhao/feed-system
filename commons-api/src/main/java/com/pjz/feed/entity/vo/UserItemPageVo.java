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
public class UserItemPageVo implements Serializable {

    private Long userId;

    private List<ItemDetailVo> items;

}
