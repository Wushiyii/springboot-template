package com.wushiyii.template.springboottemplateserver.config;


import com.wushiyii.template.springboottemplateserver.common.utils.DynamicDataSourceTransactionManager;
import com.wushiyii.template.springboottemplateserver.component.DynamicPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.wushiyii.template.springboottemplatebase.dao.mapper"})
public class MybatisConfig {

    @Resource(name = "dynamicDataSource")
    private DataSource dynamicDataSource;

    @Resource(name = "dynamicPlugin")
    private DynamicPlugin dynamicPlugin;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{dynamicPlugin});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DynamicDataSourceTransactionManager dynamicDataSourceTransactionManager() {
        DynamicDataSourceTransactionManager dataSourceTransactionManager = new DynamicDataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }

}
