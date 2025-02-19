package com.pjz.feed.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FollowVo implements Serializable {

    private Long id;

    private String username;

    private String avatar;

}
