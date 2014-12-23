package pl.java.scalatech.config;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
@Slf4j
@Order(10001)
public class Metrics2Config extends MetricsConfigurerAdapter {

    private final MetricRegistry metricRegistry = new MetricRegistry();
    private final HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    @Bean
    @Override
    public MetricRegistry getMetricRegistry() {
        metricRegistry.registerAll(new GarbageCollectorMetricSet());
        metricRegistry.registerAll(new MemoryUsageGaugeSet());
        configureReporters(metricRegistry);
        return metricRegistry;
    }

    @Bean
    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        log.info("+++                                        configureReporters");
        ConsoleReporter.forRegistry(metricRegistry).build().start(15, TimeUnit.SECONDS);

        Slf4jReporter.forRegistry(metricRegistry).outputTo(log).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build()
                .start(15, TimeUnit.MINUTES);

        JmxReporter.forRegistry(metricRegistry).build().start();
    }

}