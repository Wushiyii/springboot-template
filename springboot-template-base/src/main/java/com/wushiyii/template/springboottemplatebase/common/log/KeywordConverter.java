package com.wushiyii.template.springboottemplatebase.common.log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordConverter {
    public KeywordConverter() {
        keywordMap = new HashMap<>();
        keywordMap.put("name", "trueName");
        keywordMap.put("trueName", "trueName");
        keywordMap.put("true_name", "trueName");
        keywordMap.put("userName", "trueName");
        keywordMap.put("user_name", "trueName");


        keywordMap.put("idNumber", "idCardNo");
        keywordMap.put("id_number", "idCardNo");

        keywordMap.put("bankCardNo", "bankcardNo");
        keywordMap.put("bank_card_no", "bankcardNo");
        keywordMap.put("bankNo", "bankcardNo");
        keywordMap.put("bank_no", "bankcardNo");

        keywordMap.put("alipayUserPid", "bankcardNo");
        keywordMap.put("alipay_user_pid", "bankcardNo");

        keywordMap.put("alipay_user_id", "other");
        keywordMap.put("alipay_userid", "other");
        keywordMap.put("alipay_account", "other");

        keywordMap.put("mobile", "phoneNo");
        keywordMap.put("borrowPhoneNum", "phoneNo");
        keywordMap.put("borrow_phone_num", "phoneNo");

        keywordMap.put("address", "other");


    }

    private static Pattern pattern = Pattern.compile("[_0-9a-zA-Z]");
    protected Map<String, String> keywordMap;

    public Map<String, String> getKeywordMap() {
        return keywordMap;
    }

    public void setKeywordMap(Map<String, String> keywordMap) {
        this.keywordMap = keywordMap;
    }

    public String invokeMsg(final String oriMsg) {
        String tempMsg = oriMsg;
        try {
            Set<String> keysArray = keywordMap.keySet();
            for (String key : keysArray) {
                int index = -1;
                do {
                    index = tempMsg.indexOf(key, index + 1);
                    if (index != -1) {
                        if (isWordChar(tempMsg, key, index)) {
                            continue;
                        }
                        int valueStart = getValueStartIndex(tempMsg, index + key.length());
                        int valueEnd = getValueEndEIndex(tempMsg, valueStart);
                        // 对获取的值进行脱敏
                        String subStr = tempMsg.substring(valueStart, valueEnd);
                        if (subStr.equals("null")) {
                            continue;
                        }
                        subStr = facade(subStr, keywordMap.get(key));
                        tempMsg = tempMsg.substring(0, valueStart) + subStr + tempMsg.substring(valueEnd);
                    }
                } while (index != -1);
            }
        } catch (Exception e) {
            return tempMsg;
        }
        return tempMsg;
    }

    /**
     * 脱敏门面
     *
     * @param msg
     * @param key
     * @return
     */
    protected String facade(String msg, String key) {
        String result = msg;
        KeywordTypeEnum keywordTypeEnum = KeywordTypeEnum.getMessageType(key);
        if (keywordTypeEnum == null) {
            keywordTypeEnum = KeywordTypeEnum.OTHER;
        }
        switch (keywordTypeEnum) {
            case TRUE_NAME:
                result = SensitiveInfoUtils.handleTrueName(msg);
                break;
            case ID_CARD_NO:
                result = SensitiveInfoUtils.handleIdCardNo(msg);
                break;
            case BANKCARD_NO:
                result = SensitiveInfoUtils.handleBankcardNo(msg);
                break;
            case PHONE_NO:
                result = SensitiveInfoUtils.handlePhoneNo(msg);
                break;
            case OTHER:
                result = SensitiveInfoUtils.handleOther(msg);
                break;
        }
        return result;
    }

    /**
     * 判断key是否为单词内字符
     *
     * @param msg   待检查字符串
     * @param key   关键字
     * @param index 起始位置
     * @return 判断结果
     */
    private boolean isWordChar(String msg, String key, int index) {
        if (index != 0) {
            // 判断key前面一个字符
            char preCh = msg.charAt(index - 1);
            Matcher match = pattern.matcher(preCh + "");
            if (match.matches()) {
                return true;
            }
        }
        // 判断key后面一个字符
        char nextCh = msg.charAt(index + key.length());
        Matcher match = pattern.matcher(nextCh + "");
        if (match.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 寻找key对应值的开始位置
     *
     * @param msg        待检查字符串
     * @param valueStart 开始寻找位置
     * @return key对应值的开始位置
     */
    private int getValueStartIndex(String msg, int valueStart) {
        do {
            char ch = msg.charAt(valueStart);
            if (ch == ':' || ch == '=') {
                valueStart++;
                valueStart = loopSkipChar(msg, valueStart);
                break;
            } else {
                valueStart++;
            }
        } while (valueStart < msg.length());

        return valueStart;
    }

    /**
     * 跳过一些字符
     *
     * @param msg        如果 "name":\\\"sdf\\\" 的这种情况，需要跳过 " \ 字符
     * @param valueStart
     * @return
     */
    private int loopSkipChar(String msg, int valueStart) {
        if (valueStart < msg.length()) {
            char ch = msg.charAt(valueStart);
            if (ch == '"' || ch == '\\') {
                valueStart++;
                if (ch == '\\') {
                    valueStart = loopSkipChar(msg, valueStart);
                }
                return valueStart;
            } else {
                return valueStart;
            }
        }
        return valueStart;
    }

    /**
     * 寻找key对应值的结束位置
     *
     * @param msg      待检查字符串
     * @param valueEnd 开始寻找位置
     * @return key对应值的结束位置
     */
    private int getValueEndEIndex(String msg, int valueEnd) {
        do {
            if (valueEnd == msg.length()) {
                break;
            }
            char ch = msg.charAt(valueEnd);

            if (ch == '"') {
                if (valueEnd + 1 == msg.length()) {
                    break;
                }
                char nextCh = msg.charAt(valueEnd + 1);
                if (nextCh == ';' || nextCh == ',' || nextCh == '}' || ch == '\n') {
                    while (valueEnd > 0) {
                        char preCh = msg.charAt(valueEnd - 1);
                        if (preCh != '\\') {
                            break;
                        }
                        valueEnd--;
                    }
                    break;
                } else {
                    valueEnd++;
                }
            } else if (ch == ';' || ch == ',' || ch == '}' || ch == '\n') {
                break;
            } else {
                valueEnd++;
            }
        } while (valueEnd < msg.length());

        return valueEnd;
    }

}
