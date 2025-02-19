package com.pjz.feed.entity.bo;


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
public class ItemPublishBo implements Serializable {

    private Long userId;

    private String content;

    private List<String> mediaUrls;

}
