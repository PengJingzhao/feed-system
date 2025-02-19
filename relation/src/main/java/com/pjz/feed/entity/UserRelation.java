package com.pjz.feed.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_relation")
public class UserRelation {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("follow_id")
    private Long followId;

    @TableField("created_at")
    private LocalDateTime createdAt;

}
