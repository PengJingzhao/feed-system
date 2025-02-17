package com.pjz.feed.entity.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPublishBo {

    private Long userId;

    private String content;

    private List<String> mediaUrls;

}
