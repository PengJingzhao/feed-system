package com.pjz.feed.entity.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FollowPageBo implements Serializable {

    private Long userId;

    private Long current;

    private Long size;

}
