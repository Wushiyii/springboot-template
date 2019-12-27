package com.wushiyii.template.springboottemplateserver.vo;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResultVO<T> {

    private List<T> resultList;

    private PageVO pageInfo;
}
