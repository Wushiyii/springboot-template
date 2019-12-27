package com.wushiyii.template.springboottemplateserver.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BaseDataVO {
    private Integer code;
    private Object data;
    private String message;
}