package com.wushiyii.template.springboottemplateserver.controller;

import com.wushiyii.template.springboottemplateserver.service.BizService;
import com.wushiyii.template.springboottemplateserver.vo.BaseDataVO;
import com.wushiyii.template.springboottemplateserver.vo.SampleKeysReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("biz")
public class BizController extends BaseController {

    @Resource
    private BizService bizService;

    @PostMapping("listByKeys")
    public BaseDataVO listByKeys(@RequestBody SampleKeysReqVO sampleKeysReqVO) {
        return successModel(bizService.listPageByKeys(sampleKeysReqVO));
    }

    @GetMapping("getById")
    public BaseDataVO getById(@Param("id") Integer id) {
        return successModel(bizService.getById(id));
    }

}
