package com.wushiyii.template.springboottemplateserver.common.utils;


import com.wushiyii.template.springboottemplateserver.common.dict.DynamicDataSourceEnum;

public class DynamicDataSourceHolder {

    private static final ThreadLocal<DynamicDataSourceEnum> holder = new ThreadLocal<>();

    public static void putDataSource(DynamicDataSourceEnum dataSource){
        holder.set(dataSource);
    }

    public static DynamicDataSourceEnum getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }
}
