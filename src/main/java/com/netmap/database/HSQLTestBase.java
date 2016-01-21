package com.netmap.database;

import java.sql.SQLException;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.Server;
import org.hsqldb.server.ServerConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HSQLTestBase {
	private Server server;
	private String user = "sa";
	private String password = "";
	private String url = null;
	// boolean isNetwork = true;
	boolean memMode = true;

	@Before
	public void setUp() throws SQLException {
		if (memMode) {
			url = "jdbc:hsqldb:mem:test;sql.enforce_strict_size=true";
			server = new Server();
			server.setDatabaseName(0, "test");
			server.setDatabasePath(0, url);
			//server.setLogWriter(new PrintWriter(System.out));
			//server.setErrWriter(new PrintWriter(System.out));
			server.setLogWriter(null);
			server.setErrWriter(null);
			server.setTrace(false);
			server.setSilent(true);
			server.start();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		} else {
			url = "jdbc:hsqldb:hsql://localhost:4808/test";
		}
		//Configuration cfg = buildConfiguration();
		// createTables(cfg);
		// HSQLTestBase.setConfiguration(cfg, cfgLock);
	}

	@Test
	public void connectHSQLDB(){
		System.out.println("connectHSQLDB");
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
		int waitCount = 0;
		while (server.getState() != ServerConstants.SERVER_STATE_SHUTDOWN) {
			try {
				waitCount++;
				if (waitCount == 5) {
					throw new IllegalStateException("HSQLDB server could not be stopped");
				}
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}

	}

	private Configuration buildConfiguration() {
		Configuration cfg = new Configuration();
		cfg.setProperty(Environment.USER, user);
		cfg.setProperty(Environment.PASS, password);
		cfg.setProperty(Environment.DIALECT, HSQLDialect.class.getName());
		cfg.setProperty(Environment.URL, url);
		cfg.setProperty(Environment.DRIVER, org.hsqldb.jdbcDriver.class.getName());
		cfg.setProperty(Environment.SHOW_SQL, "false");
		cfg.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
		return cfg;
	}

	//void createTables(Configuration cfg) {
		//TableCreator tc = new TableCreator(cfg);
		//tc.createTables();
	//}
}