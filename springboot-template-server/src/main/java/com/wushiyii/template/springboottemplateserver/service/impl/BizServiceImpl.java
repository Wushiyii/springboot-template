package com.wushiyii.template.springboottemplateserver.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wushiyii.template.springboottemplatebase.common.dict.ErrorCode;
import com.wushiyii.template.springboottemplatebase.dao.dao.BizDao;
import com.wushiyii.template.springboottemplatebase.exception.InvalidParamException;
import com.wushiyii.template.springboottemplatebase.model.domain.SampleDO;
import com.wushiyii.template.springboottemplatebase.model.vo.SampleVO;
import com.wushiyii.template.springboottemplateserver.service.BizService;
import com.wushiyii.template.springboottemplateserver.vo.PageResultVO;
import com.wushiyii.template.springboottemplateserver.vo.PageVO;
import com.wushiyii.template.springboottemplateserver.vo.SampleKeysReqVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BizServiceImpl implements BizService {

    @Resource
    private BizDao bizDao;

    @Override
    public Integer create(SampleVO sampleVO) {
        SampleDO sampleDO = new SampleDO();
        BeanUtils.copyProperties(sampleVO, sampleDO);

        return bizDao.insert(sampleDO);
    }

    @Override
    public Integer update(SampleVO sampleVO) {
        SampleDO sampleDO = new SampleDO();
        BeanUtils.copyProperties(sampleVO, sampleDO);

        return bizDao.update(sampleDO);
    }

    @Override
    public SampleVO getById(Integer id) {
        return Optional.ofNullable(bizDao.selectById(id))
                .orElseThrow(() -> new InvalidParamException(ErrorCode.BAD_REQUEST_ERROR))
                .toVO();
    }

    @Override
    public PageResultVO listPageByKeys(SampleKeysReqVO sampleKeysReqVO) {

        Page<SampleDO> page = PageHelper.startPage(sampleKeysReqVO.getPageNum(), sampleKeysReqVO.getPageSize());

        List<SampleDO> sampleDOS = bizDao.selectByKeys(sampleKeysReqVO.getKeys());
        List<SampleVO> sampleVOS = Optional.ofNullable(sampleDOS)
                .orElse(new ArrayList<>())
                .stream()
                .map(SampleDO::toVO)
                .collect(Collectors.toList());

        return PageResultVO.builder()
                .resultList(Collections.singletonList(sampleVOS))
                .pageInfo(PageVO.builder()
                        .pageNum(page.getPageNum())
                        .pageSize(page.getPageSize())
                        .total(page.getTotal())
                        .pages(page.getPages())
                        .build())
                .build();
    }
}
