package com.pjz.feed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("visitor_logs")
public class VisitorsLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("client_ip")
    private String clientIp;

    @TableField("method")
    private String method;

    @TableField("visit_time")
    private Timestamp visitTime;

    @TableField("path")
    private String path;

}
