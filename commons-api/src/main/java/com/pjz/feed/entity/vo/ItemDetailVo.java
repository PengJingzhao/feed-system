package com.pjz.feed.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailVo {

    private Long itemId;

    private Long userId;

    private String content;

    private List<String> mediaUrls;

    private LocalDateTime createdAt;
}
