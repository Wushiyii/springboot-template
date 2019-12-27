package com.wushiyii.template.springboottemplateserver.common.utils;

import com.google.common.collect.Maps;
import com.wushiyii.template.springboottemplateserver.common.dict.DynamicDataSourceEnum;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 写数据源
     */
    private Object writeDataSource;

    /**
     * 读数据源
     */
    private Object readDataSource;

    @Override
    public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = Maps.newHashMap();
        targetDataSources.put(DynamicDataSourceEnum.WRITE.name(), writeDataSource);
        if (readDataSource != null) {
            targetDataSources.put(DynamicDataSourceEnum.READ.name(), readDataSource);
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {

        DynamicDataSourceEnum dynamicDataSourceEnum = DynamicDataSourceHolder.getDataSource();

        if (DynamicDataSourceEnum.READ == dynamicDataSourceEnum) {
            return DynamicDataSourceEnum.READ.name();
        } else {
            return DynamicDataSourceEnum.WRITE.name();
        }
    }

    public DynamicDataSource(Object writeDataSource, Object readDataSource) {
        this.writeDataSource = writeDataSource;
        this.readDataSource = readDataSource;
    }
}
