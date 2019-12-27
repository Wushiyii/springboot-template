package com.wushiyii.template.springboottemplateserver.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {

    /**
     * 记录总数
     */
    private Long total;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页数据条数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;
}
