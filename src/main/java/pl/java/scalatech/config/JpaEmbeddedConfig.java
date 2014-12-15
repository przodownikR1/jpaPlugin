package pl.java.scalatech.config;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;

@Configuration
@Slf4j
@Profile(value = "test")
public class JpaEmbeddedConfig extends JpaConfig {

    @Override
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Override
    public Database dataBase() {
        return Database.H2;
    }

}
