package com.munsun.monitoring_service.backend.configurations.root;

import com.munsun.monitoring_service.commons.configurations.SpringContextCommonsConfig;
import com.munsun.utils.logger.configurations.SpringContextLoggerConfig;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Import(value = {SpringContextCommonsConfig.class, SpringContextLoggerConfig.class})
@ComponentScan(basePackages = "com.munsun.monitoring_service.backend")
@EnableAspectJAutoProxy
public class SpringContextBackendConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }
}