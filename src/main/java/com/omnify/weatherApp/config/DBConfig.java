package com.omnify.weatherApp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Properties;

@Configuration
public class DBConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DBConfig.class);
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.driverclassname}")
    private String driverName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.poolName}")
    private String poolName;

    @Value("${spring.datasource.maxPoolSize}")
    private String poolSize;

    @Value("${spring.datasource.minimumIdle}")
    private String minIdle;

    @Value("${spring.datasource.connectionTimeout}")
    private String connTimeout;

    @Value("${spring.datasource.idleTimeout}")
    private String idleTimeout;

    public HikariDataSource weatherDataSource() {
        try {
            Properties dsProps = new Properties();
            dsProps.put("url", dbUrl);
            dsProps.put("user", username);
//            dsProps.put("password", password);

            Properties configProps = new Properties();
            configProps.put("dataSourceClassName", driverName);
            configProps.put("poolName", poolName);
            configProps.put("maximumPoolSize", poolSize);
            configProps.put("minimumIdle", minIdle);
            configProps.put("connectionTimeout", connTimeout);
            configProps.put("idleTimeout", idleTimeout);
            configProps.put("dataSourceProperties", dsProps);

            HikariConfig hc = new HikariConfig(configProps);
            HikariDataSource ds = new HikariDataSource(hc);
            return ds;
        } catch (Exception e) {
            LOG.info(dbUrl);
            LOG.error("Exception in DB config : ", e);
        }
        return null;
    }

    @Bean(name = "weatherJdbcTemplate")
    public JdbcTemplate weatherJdbcTemplate(){
        return new JdbcTemplate(weatherDataSource());
    }
}
