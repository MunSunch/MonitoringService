package com.munsun.monitoring_service.backend.configurations.root;

import com.munsun.monitoring_service.commons.configurations.SpringContextCommonsConfig;
import com.munsun.utils.logger.configurations.SpringContextLoggerConfig;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

@Configuration
@Import(value = {SpringContextCommonsConfig.class, SpringContextLoggerConfig.class})
@ComponentScan(basePackages = "com.munsun.monitoring_service.backend")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringContextBackendConfig {}