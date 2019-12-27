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

public class InsertLangDriver extends XMLLanguageDriver implements LanguageDriver {

    private final List<String> IGNORE_FIELDS = Collections.singletonList("id");

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder fields = new StringBuilder();
            StringBuilder values = new StringBuilder();
            fields.append("(");

            for (Field field : parameterType.getDeclaredFields()) {
                if (IGNORE_FIELDS.contains(field.getName())) {
                    continue;
                }
                fields.append('`' + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()) + "`,");
                values.append("#{" + field.getName() + "},");
            }

            fields.deleteCharAt(fields.lastIndexOf(","));
            values.deleteCharAt(values.lastIndexOf(","));
            fields.append(") values (" + values.toString() + ")");

            script = matcher.replaceAll(fields.toString());
            script = "<script>" + script + "</script>";
        }

        return super.createSqlSource(configuration, script, parameterType);
    }

}
