package com.munsun.monitoring_service.backend.configurations.root;

import com.munsun.monitoring_service.commons.configurations.SpringContextCommonsConfig;
import com.munsun.monitoring_service.commons.configurations.YamlPropertySourceFactory;
import com.munsun.utils.logger.configurations.SpringContextLoggerConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertySourceDescriptor;
import org.springframework.core.io.support.PropertySourceProcessor;

@Configuration
@Import(value = {SpringContextCommonsConfig.class, SpringContextLoggerConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = "com.munsun.monitoring_service.backend")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringContextBackendConfig {
}