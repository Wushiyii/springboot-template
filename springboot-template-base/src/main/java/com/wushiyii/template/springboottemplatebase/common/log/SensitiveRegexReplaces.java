package com.wushiyii.template.springboottemplatebase.common.log;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.pattern.RegexReplacement;
import org.apache.logging.log4j.status.StatusLogger;


@Plugin(name = "replaces", category = "Core", printObject = true)
public final class SensitiveRegexReplaces {

    private static final Logger LOGGER = StatusLogger.getLogger();

    // replace标签，复用log4j已有plugin， replaces 下可以0，1，多个replace
    private final RegexReplacement[] replaces;

    private SensitiveRegexReplaces(RegexReplacement[] replaces) {
        this.replaces = replaces;
    }

    /**
     * 格式化输出日志信息， 此方法会执行多个正则表达式匹配与替换
     *
     * @param msg
     * @return
     */
    public String format(String msg) {
        for (RegexReplacement replace : replaces) {
            msg = replace.format(msg);
        }
        return msg;
    }

    /**
     * 实现pluginFactory， 用于生成pugin
     *
     * @param replaces
     * @return
     */
    @PluginFactory
    public static SensitiveRegexReplaces createRegexReplacement(
            @PluginElement("replaces") final RegexReplacement[] replaces) {
        if (replaces == null) {
            LOGGER.info("no replaces is defined");
            return null;
        }
        if (replaces.length == 0) {
            LOGGER.warn("have the replaces , but no replace is set");
            return null;
        }
        return new SensitiveRegexReplaces(replaces);
    }

}