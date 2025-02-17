package com.pjz.feed.entity.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailPageBo {

    private Long page;

    private Long size;

    private Long userId;

}
