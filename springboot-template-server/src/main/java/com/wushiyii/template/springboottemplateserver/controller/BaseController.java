package com.wushiyii.template.springboottemplateserver.controller;


import com.wushiyii.template.springboottemplatebase.common.constants.Constants;
import com.wushiyii.template.springboottemplateserver.vo.BaseDataVO;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class BaseController {

    protected BaseDataVO buildBaseDataVO(int code, Object o) {
        return BaseDataVO.builder()
                .code(code)
                .data(o)
                .message(Constants.OK)
                .build();
    }

    protected BaseDataVO successModel() {
        return BaseDataVO.builder()
                .code(Constants.SUCCESS_CODE)
                .message(Constants.OK)
                .build();
    }

    protected BaseDataVO successModel(Object data) {
        return BaseDataVO.builder()
                .code(Constants.SUCCESS_CODE)
                .data(data)
                .message(Constants.OK)
                .build();
    }

    protected String encode(String str) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return URLEncoder.encode(str, "utf-8");
    }

}
