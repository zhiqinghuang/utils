package org.flywaydb.sample.callback;

import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.MigrationInfo;

import java.sql.Connection;
import org.flywaydb.core.internal.util.logging.Log;
import org.flywaydb.core.internal.util.logging.LogFactory;

public class DefaultFlywayCallback implements FlywayCallback {
    private static final Log LOG = LogFactory.getLog(DefaultFlywayCallback.class);

    @Override
	public void beforeClean(Connection dataConnection) {
    	LOG.info("beforeClean");
	}

	@Override
	public void afterClean(Connection dataConnection) {
		LOG.info("afterClean");
	}

	@Override
	public void beforeMigrate(Connection dataConnection) {
		LOG.info("beforeMigrate");
	}

	@Override
	public void afterMigrate(Connection dataConnection) {
		LOG.info("afterMigrate");
	}

	@Override
	public void beforeEachMigrate(Connection dataConnection, MigrationInfo info) {
		LOG.info("beforeEachMigrate");
	}

	@Override
	public void afterEachMigrate(Connection dataConnection, MigrationInfo info) {
		LOG.info("afterEachMigrate");
	}

	@Override
	public void beforeValidate(Connection dataConnection) {
		LOG.info("beforeValidate");
	}

	@Override
	public void afterValidate(Connection dataConnection) {
		LOG.info("afterValidate");
	}

	@Override
	public void beforeBaseline(Connection connection) {
		LOG.info("beforeBaseline");
	}

	@Override
	public void afterBaseline(Connection connection) {
		LOG.info("afterBaseline");
	}

	@Override
	public void beforeInit(Connection dataConnection) {
		LOG.info("beforeInit");
	}

	@Override
	public void afterInit(Connection dataConnection) {
		LOG.info("afterInit");
	}

	@Override
	public void beforeRepair(Connection dataConnection) {
		LOG.info("beforeRepair");
	}

	@Override
	public void afterRepair(Connection dataConnection) {
		LOG.info("afterRepair");
	}

	@Override
	public void beforeInfo(Connection dataConnection) {
		LOG.info("beforeInfo");
	}

	@Override
	public void afterInfo(Connection dataConnection) {
		LOG.info("afterInfo");
	}
}