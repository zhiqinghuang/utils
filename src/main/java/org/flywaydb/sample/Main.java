package org.flywaydb.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.flywaydb.core.Flyway;
import org.flywaydb.sample.resolver.SampleResolver;
import org.springframework.jdbc.core.JdbcTemplate;

public class Main {

	public static void main(String[] args) throws Exception {
		//migrate();
		testConnectHsqlDB();
	}

	public static void migrate() {
		Flyway flyway = new Flyway();
		flyway.setDataSource("jdbc:hsqldb:mem:flyway_sample;shutdown=true", "SA", "");
		flyway.setLocations("db/migration", "org.flywaydb.sample.migration");
		flyway.setResolvers(new SampleResolver());
		flyway.migrate();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(flyway.getDataSource());
		List<Map<String, Object>> results = jdbcTemplate.queryForList("select name from test_user");
		for (Map<String, Object> result : results) {
			System.out.println("Name: " + result.get("NAME"));
		}
	}

	public static void testConnectHsqlDB() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:flyway_sample", "sa", "");
			if (c != null) {
				System.out.println("Connected db success!");
				String sql = "select name from test_user";
				Statement st = c.createStatement();
				st.execute(sql);
				//sql = "INSERT INTO TBL_USERS(ID, NAME, BIRTHDAY) VALUES ('1', 'ADMIN', SYSDATE);";
				//st.executeUpdate(sql);
				if (st != null) {
					st.close();
				}
				/*String sql = "CREATE TABLE TBL_USERS(ID INTEGER, NAME VARCHAR, BIRTHDAY DATE);";
				Statement st = c.createStatement();
				st.execute(sql);
				sql = "INSERT INTO TBL_USERS(ID, NAME, BIRTHDAY) VALUES ('1', 'ADMIN', SYSDATE);";
				st.executeUpdate(sql);
				if (st != null) {
					st.close();
				}*/
				c.close();
			}
		} catch (Exception e) {
			System.out.println("ERROR:failed to load HSQLDB JDBC driver.");
			e.printStackTrace();
			return;
		}
	}
}