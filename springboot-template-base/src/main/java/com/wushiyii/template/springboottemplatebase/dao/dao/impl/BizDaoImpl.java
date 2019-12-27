package com.wushiyii.template.springboottemplatebase.dao.dao.impl;

import com.wushiyii.template.springboottemplatebase.dao.dao.BizDao;
import com.wushiyii.template.springboottemplatebase.dao.mapper.BizMapper;
import com.wushiyii.template.springboottemplatebase.model.domain.SampleDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class BizDaoImpl implements BizDao {

    @Resource
    private BizMapper bizMapper;

    @Override
    public Integer insert(SampleDO sampleDO) {
        return bizMapper.insert(sampleDO);
    }

    @Override
    public Integer update(SampleDO sampleDO) {
        return bizMapper.update(sampleDO);
    }

    @Override
    public SampleDO selectById(Integer id) {
        return bizMapper.selectById(id);
    }

    @Override
    public List<SampleDO> selectByKeys(List<String> keys) {
        return bizMapper.selectByKeys(keys);
    }
}
