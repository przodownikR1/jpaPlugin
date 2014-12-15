package pl.java.scalatech.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import pl.java.scalatech.jpa.CustomHibernateJpaDialect;

@EnableJpaRepositories(basePackages = "pl.java.scalatech.repository")
@PropertySource("classpath:spring-data.properties")
@PropertySource("classpath:application.properties")
@Slf4j
@Profile(value = "test")
public class JpaEmbeddedConfig {

    @Autowired
    private Environment env;

    @Value("${dataSource.driverClassName}")
    private String driver;

    @Value("${dataSource.url}")
    private String url;

    @Value("${dataSource.username}")
    private String username;

    @Value("${dataSource.password}")
    private String password;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private Boolean hbm2ddlAuto;

    @Value("${boneCp.partition.count}")
    private int partitionCount;

    @Value("${boneCp.partition.minConnectionsPerPartition}")
    private int minConnectionsPerPartition;

    @Value("${boneCp.partition.maxConnectionsPerPartition}")
    private int maxConnectionsPerPartition;

    @Value("${hibernate.show.sql}")
    private Boolean showSql;

    @Value("${jpa.package}")
    private String jpaPackage;

    /*
     * @Bean
     * public Flyway flyway() {
     * Flyway flyway = new Flyway();
     * flyway.setDataSource(dataSource());
     * flyway.migrate();
     * return flyway;
     * }
     */

   

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    public Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        /*
         * props.put("hibernate.cache.use_query_cache", "true");
         * props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
         * props.put("hibernate.cache.provider_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
         * props.put("hibernate.cache.use_second_level_cache", "true");
         */
        return props;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setJpaDialect(customJpaDialect());
        lef.setDataSource(dataSource());
        lef.setJpaVendorAdapter(jpaVendorAdapter());
        lef.setJpaPropertyMap(jpaProperties());
        lef.setPackagesToScan(jpaPackage); // eliminate persistence.xml
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(showSql);
        hibernateJpaVendorAdapter.setGenerateDdl(hbm2ddlAuto);
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        hibernateJpaVendorAdapter.setDatabasePlatform(dialect);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public Log4JdbcCustomFormatter logFormater() {
        Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
        formatter.setLoggingType(LoggingType.SINGLE_LINE);
        formatter.setSqlPrefix("SQL:\r");
        return formatter;
    }
    public JpaDialect customJpaDialect(){
        return new CustomHibernateJpaDialect();
    }
}
