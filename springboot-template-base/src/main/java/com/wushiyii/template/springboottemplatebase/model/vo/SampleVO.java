package com.wushiyii.template.springboottemplatebase.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleVO {
    private Integer id;
    private String name;
    private String value;
    private Date createTime;
    private Date modifyTime;
}
