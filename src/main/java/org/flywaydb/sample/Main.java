package org.flywaydb.sample;

import java.util.List;
import java.util.Map;

import org.flywaydb.core.Flyway;
import org.flywaydb.sample.resolver.SampleResolver;
import org.springframework.jdbc.core.JdbcTemplate;

public class Main {

    public static void main(String[] args) throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:hsqldb:file:db/flyway_sample;shutdown=true", "SA", "");
        flyway.setLocations("db/migration", "org.flywaydb.sample.migration");
        flyway.setResolvers(new SampleResolver());
        flyway.migrate();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(flyway.getDataSource());
        List<Map<String, Object>> results = jdbcTemplate.queryForList("select name from test_user");
        for (Map<String, Object> result : results) {
            System.out.println("Name: " + result.get("NAME"));
        }
    }
}