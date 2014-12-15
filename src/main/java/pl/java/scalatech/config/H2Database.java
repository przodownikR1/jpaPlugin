package pl.java.scalatech.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@Profile(value="h2")
public class H2Database extends JpaConfig{
    @Bean
    @DependsOn(value = "h2Server")
    DataSource dataSource(Server h2Server) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:tcp://localhost:9092/mem:przodownik;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Bean(name = "h2Server", initMethod = "start", destroyMethod = "stop")
    @DependsOn(value = "h2WebServer")
    public org.h2.tools.Server createTcpServer() throws SQLException {
        return org.h2.tools.Server.createTcpServer("-tcp,-tcpAllowOthers,-tcpPort,9092".split(","));
    }

    @Override
    public DataSource dataSource() throws SQLException {
        DataSource dataSource = dataSource(createTcpServer());
        return dataSource;
    }
}
