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
public class ItemDetailPageBo implements Serializable {

    private Long page;

    private Long size;

    private Long userId;

}
