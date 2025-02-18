package com.pjz.feed.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelation {

    private Long id;

    private Long userId;

    private Long followId;

    private LocalDateTime createdAt;

}
