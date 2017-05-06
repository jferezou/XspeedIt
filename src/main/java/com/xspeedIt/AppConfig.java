package com.xspeedIt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.xspeedIt.*" })
@PropertySource("classpath:com/xspeedIt/config.properties")
public class AppConfig {

}
