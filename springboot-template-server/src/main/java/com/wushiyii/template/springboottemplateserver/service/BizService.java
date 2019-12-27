package com.wushiyii.template.springboottemplateserver.service;

import com.wushiyii.template.springboottemplatebase.model.vo.SampleVO;
import com.wushiyii.template.springboottemplateserver.vo.PageResultVO;
import com.wushiyii.template.springboottemplateserver.vo.SampleKeysReqVO;

public interface BizService {

    Integer create(SampleVO sampleVO);

    Integer update(SampleVO sampleVO);

    SampleVO getById(Integer id);

    PageResultVO listPageByKeys(SampleKeysReqVO sampleKeysReqVO);
}
