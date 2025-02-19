package com.pjz.feed.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDetailVo implements Serializable {

    private Long itemId;

    private Long userId;

    private String content;

    private List<String> mediaUrls;

    private LocalDateTime createdAt;
}
