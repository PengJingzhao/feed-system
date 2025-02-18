package com.pjz.feed.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowersPageVo {

    private Long current;

    private Long total;

    private Long size;

    private List<FollowVo> followers;

    private Long userId;

}
