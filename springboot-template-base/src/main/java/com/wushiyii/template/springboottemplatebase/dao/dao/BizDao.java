package com.wushiyii.template.springboottemplatebase.dao.dao;

import com.wushiyii.template.springboottemplatebase.model.domain.SampleDO;
import java.util.List;

public interface BizDao {

    Integer insert(SampleDO sampleDO);

    Integer update(SampleDO sampleDO);

    SampleDO selectById(Integer id);

    List<SampleDO> selectByKeys(List<String> keys);
}
