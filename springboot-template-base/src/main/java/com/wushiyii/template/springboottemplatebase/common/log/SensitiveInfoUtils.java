package com.wushiyii.template.springboottemplatebase.common.log;

import org.apache.commons.lang3.StringUtils;

public class SensitiveInfoUtils {

    /**
     * 真实姓名脱敏(展示姓名第一个字,其余用*替换)
     *
     * @param fullName
     * @return
     */
    public static String handleTrueName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }

        return "*";
    }

    /**
     * 身份证号码脱敏(展示身份证号码前3位和最后4位,其余用*替换)
     *
     * @param idCardNo
     * @return
     */
    public static String handleIdCardNo(String idCardNo) {
        if (StringUtils.isBlank(idCardNo)) {
            return "";
        }
        return StringUtils.left(idCardNo, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idCardNo, 4),
                StringUtils.length(idCardNo), "*"), "***"));
    }

    /**
     * 手机号码脱敏(展示手机号码前3位和最后4位,其余用*替换)
     *
     * @param phoneNo
     * @return
     */
    public static String handlePhoneNo(String phoneNo) {
        if (StringUtils.isBlank(phoneNo)) {
            return "";
        }
        return StringUtils.left(phoneNo, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(phoneNo, 4),
                StringUtils.length(phoneNo), "*"), "***"));
    }

    /**
     * 银行卡号脱敏(展示银行卡号前6位和最后4位,其余用*替换)
     *
     * @param bankcardNo
     * @return
     */
    public static String handleBankcardNo(String bankcardNo) {
        if (StringUtils.isBlank(bankcardNo)) {
            return "";
        }
        return StringUtils.left(bankcardNo, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(bankcardNo, 4),
                StringUtils.length(bankcardNo), "*"), "******"));
    }

    /**
     * 其它(全部用*替换)
     *
     * @param msg
     * @return
     */
    public static String handleOther(String msg) {
        if (StringUtils.isBlank(msg)) {
            return "";
        }
        return StringUtils.rightPad("", StringUtils.length(msg), "*");
    }
}
