package com.wushiyii.template.springboottemplatebase.dao.mapper.plugin;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertNotNullLangDriver extends XMLLanguageDriver implements LanguageDriver {

    private final List<String> IGNORE_FIELDS = Collections.singletonList("id");

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder key = new StringBuilder();
            StringBuilder value = new StringBuilder();
            key.append("<trim suffixOverrides=\",\">");
            value.append("<trim suffixOverrides=\",\">");

            for (Field field : parameterType.getDeclaredFields()) {
                if (IGNORE_FIELDS.contains(field.getName())) {
                    continue;
                }

                String keyFields = "<if test=\"_field != null\">_column,</if>";
                key.append(keyFields.replaceAll("_field", field.getName())
                        .replaceAll("_column", "`" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()) + "`"));

                String valueFields = "<if test=\"_field != null\">#{_field},</if>";
                value.append(valueFields.replaceAll("_field", field.getName()));
            }

            key.append("</trim>");
            value.append("</trim>");

            StringBuilder fields = new StringBuilder();
            fields.append("(").append(key).append(") values (").append(value).append(")");
            script = matcher.replaceAll(fields.toString());
            script = "<script>" + script + "</script>";
        }

        return super.createSqlSource(configuration, script, parameterType);
    }
}
