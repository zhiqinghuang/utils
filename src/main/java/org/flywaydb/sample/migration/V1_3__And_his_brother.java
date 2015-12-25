package org.flywaydb.sample.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class V1_3__And_his_brother implements JdbcMigration {
    public void migrate(Connection connection) throws Exception {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO test_user (name) VALUES ('Jdbcix')");
        try {
            statement.execute();
        } finally {
            statement.close();
        }
    }
}