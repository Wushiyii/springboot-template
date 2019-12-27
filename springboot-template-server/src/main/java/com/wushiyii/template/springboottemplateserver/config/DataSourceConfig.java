package com.wushiyii.template.springboottemplateserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wushiyii.template.springboottemplateserver.common.utils.DynamicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application-db.properties")
public class DataSourceConfig {

    /**
     * master连接池
     * @return
     */
    @Bean(initMethod = "init", name = "masterDataSource", destroyMethod = "close")
    @ConfigurationProperties("app.ds.master")
    public DruidDataSource masterDataSource() {
        return new DruidDataSource();
    }

    /**
     * slave连接池
     * @return
     */
    @Bean(initMethod = "init", name = "slaveDataSource", destroyMethod = "close")
    @ConfigurationProperties("app.ds.slave")
    public DruidDataSource slaveDataSource() {
        return new DruidDataSource();
    }

    /**
     * 动态连接池
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        return new DynamicDataSource(masterDataSource(), slaveDataSource());
    }
}
