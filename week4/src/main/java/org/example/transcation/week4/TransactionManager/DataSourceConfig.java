package org.example.transcation.week4.TransactionManager;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource1() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1")
                .driverClassName("org.h2.Driver")
                .username("sa").build();
    }

    @Bean
    public DataSource dataSource2() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:db2;DB_CLOSE_DELAY=-1")
                .driverClassName("org.h2.Driver")
                .username("sa").build();
    }

    @Bean @Primary
    public PlatformTransactionManager transactionManager1() {
        return new DataSourceTransactionManager(dataSource1());
    }

    @Bean
    public PlatformTransactionManager transactionManager2() {
        return new DataSourceTransactionManager(dataSource2());
    }

    @Bean
    public JdbcTemplate jdbcTemplate1() { return new JdbcTemplate(dataSource1()); }

    @Bean
    public JdbcTemplate jdbcTemplate2() { return new JdbcTemplate(dataSource2()); }
}
