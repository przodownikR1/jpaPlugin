package pl.java.scalatech.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;



@Configuration
@EnableMBeanExport
@ComponentScan(basePackages="pl.java.scalatech.jmx")
public class JmxConfig {
    
}
