package pl.java.scalatech.test;

import lombok.extern.slf4j.Slf4j;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.config.JpaEmbeddedConfig;
import pl.java.scalatech.config.Metrics2Config;
import pl.java.scalatech.config.PropertiesLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PropertiesLoader.class, JpaEmbeddedConfig.class })
@ActiveProfiles(value = "test")
@Transactional
@Slf4j
public class ApplicationTests {
  

    @Test
    public void contextLoads() throws InterruptedException {
        Assertions.assertThat(true);
        Thread.sleep(2000);
    }

}
