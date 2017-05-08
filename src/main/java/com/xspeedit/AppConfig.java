package com.xspeedit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Classe de config Spring
 * @author jferezou
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.xspeedit.*" })
@PropertySource("classpath:config.properties")
public class AppConfig {

}
