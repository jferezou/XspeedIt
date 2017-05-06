package com.xspeedIt;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.xspeedIt.*" })
@PropertySource("classpath:config-test.properties")
public class TestConfig {

}
