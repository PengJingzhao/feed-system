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
public class FollowPageVo implements Serializable {

    private Long current;

    private Long total;

    private Long size;

    private List<FollowVo> following;

    private Long userId;

}
