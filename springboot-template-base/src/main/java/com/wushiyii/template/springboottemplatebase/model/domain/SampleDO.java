package com.wushiyii.template.springboottemplatebase.model.domain;

import com.wushiyii.template.springboottemplatebase.model.vo.SampleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleDO {
    private Integer id;
    private String name;
    private String value;
    private Date createTime;
    private Date modifyTime;

    public SampleVO toVO() {
        SampleVO sampleVO = new SampleVO();
        BeanUtils.copyProperties(this, sampleVO);

        return sampleVO;
    }
}
