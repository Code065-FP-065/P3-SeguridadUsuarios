package com.code065.alquilervehiculos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class StartupDatasourceLogger implements CommandLineRunner {

    private final DataSource dataSource;

    public StartupDatasourceLogger(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("========================================");
            System.out.println("DATASOURCE URL REAL: " + connection.getMetaData().getURL());
            System.out.println("DATASOURCE USER REAL: " + connection.getMetaData().getUserName());
            System.out.println("========================================");
        }
    }
}