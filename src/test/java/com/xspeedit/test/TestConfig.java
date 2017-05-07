package com.xspeedit.test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration spring de test
 * @author jferezou
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.xspeedit.service.*"})
@PropertySource("classpath:config-test.properties")
public class TestConfig {

}
