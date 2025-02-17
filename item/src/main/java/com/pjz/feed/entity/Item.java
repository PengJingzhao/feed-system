package com.pjz.feed.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Long id;

    private Long userId;

    private String content;

    private List<String> mediaUrls;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
