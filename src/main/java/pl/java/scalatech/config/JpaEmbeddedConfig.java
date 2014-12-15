package pl.java.scalatech.config;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@EnableJpaRepositories(basePackages = "pl.java.scalatech.repository")
@PropertySource("classpath:spring-data.properties")
@PropertySource("classpath:application.properties")
@Slf4j
@Profile(value = "test")
public class JpaEmbeddedConfig extends JpaConfig {

    @Override
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

}
