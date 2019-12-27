package com.wushiyii.template.springboottemplateserver.config;


import com.wushiyii.template.springboottemplatebase.common.utils.OKHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {

    @Bean
    public OKHttpClient okHttpClient() {
        return new OKHttpClient(3000);
    }
}
