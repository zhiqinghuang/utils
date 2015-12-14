package com.netmap.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestBatchCluster {

	public static void main(String[] args) {
		multiThread();
		singleThread();
	}

	public static void singleThread() {
		System.out.println(System.currentTimeMillis());
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			// printColumn();
			testDeleteData();
			testInsertData();
			processQueryAndUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
	}

	public static long processSelectForUpdate() {
		long longQueryTimes = 0;
		try {
			List<String> listUpdatedKey = new ArrayList<String>();
			Connection connection = TestBatchCluster.getConnection();
			longQueryTimes++;
			List<String> listKey = TestBatchCluster.selectForUpdate(connection);
			while (listKey.size() > 0) {
				for (int i = 0; i < listKey.size(); i++) {
					String lstrUpdatedKey = listKey.get(i);
					listUpdatedKey.add(lstrUpdatedKey);
				}
				listKey = TestBatchCluster.selectForUpdate(connection);
				longQueryTimes++;
			}
			System.out.print(listUpdatedKey.size());
			System.out.print(":");
			// System.out.println(listUpdatedKey);
			System.out.println(System.currentTimeMillis());
			System.out.print("query times:");
			System.out.println(longQueryTimes);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return longQueryTimes;
	}

	public static long processQueryAndUpdate() {
		long longQueryTimes = 0;
		try {
			List<String> listUpdatedKey = new ArrayList<String>();
			Connection connection = TestBatchCluster.getConnection();
			longQueryTimes++;
			List<String> listKey = TestBatchCluster.queryData(connection);
			while (listKey.size() > 0) {
				int[] iReturn = TestBatchCluster.testUpdateableUseStatement(connection, listKey);
				if (iReturn != null) {
					for (int i = 0; i < iReturn.length; i++) {
						if (iReturn[i] > 0) {
							String lstrUpdatedKey = listKey.get(i);
							listUpdatedKey.add(lstrUpdatedKey);
						}
					}
				}
				listKey = TestBatchCluster.queryData(connection);
				longQueryTimes++;
			}
			System.out.print(listUpdatedKey.size());
			System.out.print(":");
			// System.out.println(listUpdatedKey);
			System.out.println(System.currentTimeMillis());
			System.out.print("query times:");
			System.out.println(longQueryTimes);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return longQueryTimes;
	}

	public static List<String> getSublist(List<String> list, int subSize) {
		List<String> sublist = new ArrayList<String>();
		while (list.size() > 0) {
			String lstrKey = list.remove(0);
			sublist.add(lstrKey);
			if (sublist.size() == subSize) {
				break;
			}
		}
		return sublist;
	}

	public static void multiThread() {
		System.out.println(System.currentTimeMillis());
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			// printColumn();
			testDeleteData();
			testInsertData();
			ThreadGroup threadGroup = new ThreadGroup("BatchThread");
			ThreadProcess batchThreadProcess0 = new ThreadProcess();
			Thread thread0 = new Thread(threadGroup, batchThreadProcess0, "batchThreadProcess0");
			ThreadProcess batchThreadProcess1 = new ThreadProcess();
			Thread thread1 = new Thread(threadGroup, batchThreadProcess1, "batchThreadProcess1");
			ThreadProcess batchThreadProcess2 = new ThreadProcess();
			Thread thread2 = new Thread(threadGroup, batchThreadProcess2, "batchThreadProcess2");
			thread0.start();
			thread1.start();
			thread2.start();
			thread0.join();
			thread1.join();
			thread2.join();
			long queryTimes0 = batchThreadProcess0.getQueryTimes();
			long queryTimes1 = batchThreadProcess1.getQueryTimes();
			long queryTimes2 = batchThreadProcess2.getQueryTimes();
			long queryTimes = queryTimes0 + queryTimes1 + queryTimes2;
			System.out.println("all queryTimes : " + queryTimes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printColumn() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection connection = getConnection();
		DatabaseMetaData meta = connection.getMetaData();
		ResultSet rsColumns = meta.getColumns(null, "EXIMTRX", "BILL_TEST", null);
		while (rsColumns.next()) {
			System.out.print(rsColumns.getString("TYPE_NAME"));
			System.out.print(":");
			System.out.println(rsColumns.getString("COLUMN_NAME"));
		}
		connection.close();
		connection = null;
	}

	public static Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String strDS = "jdbc:oracle:thin:@10.39.107.24:1521:EEDB";
		String strName = "EXIMTRX";
		String strPWD = "EXIMTRX";
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		Connection connection = DriverManager.getConnection(strDS, strName, strPWD);
		return connection;
	}

	public static List<String> selectForUpdate(Connection connection) {
		List<String> listKey = new ArrayList<String>();
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("SELECT C_MAIN_REF,C_LOCKED_FLAG FROM EXIMTRX.BILL_TEST WHERE C_LOCKED_FLAG = 'F' FOR UPDATE");
			String strQuery = sbQuery.toString();
			connection.setAutoCommit(false);
			Statement stmt = connection.prepareStatement(strQuery);
			stmt.setFetchSize(5);
			stmt.setMaxRows(5);
			ResultSet rs = stmt.executeQuery(strQuery);
			boolean bLoop = rs.next();
			while (bLoop) {
				String lstrKey = rs.getString("C_MAIN_REF");
				listKey.add(lstrKey);
				bLoop = rs.next();
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

			sbQuery = new StringBuffer("UPDATE EXIMTRX.BILL_TEST SET C_LOCKED_FLAG = 'T' WHERE C_LOCKED_FLAG = 'F' AND C_MAIN_REF = '");
			strQuery = sbQuery.toString();

			Statement statement = connection.createStatement();
			int liSize = listKey.size();
			for (int i = 0; i < liSize; i++) {
				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(strQuery);
				sbSQL.append(listKey.get(i));
				sbSQL.append("'");
				statement.addBatch(sbSQL.toString());
			}
			statement.executeBatch();
			connection.commit();
			statement.close();
			statement = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listKey;
	}

	public static List<String> queryData(Connection connection) {
		List<String> listKey = new ArrayList<String>();
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("SELECT C_MAIN_REF,C_LOCKED_FLAG FROM EXIMTRX.BILL_TEST WHERE C_LOCKED_FLAG = 'F'");
			String strQuery = sbQuery.toString();
			Statement stmt = connection.prepareStatement(strQuery);
			stmt.setFetchSize(5);
			stmt.setMaxRows(5);
			ResultSet rs = stmt.executeQuery(strQuery);
			boolean bLoop = rs.next();
			while (bLoop) {
				String lstrKey = rs.getString("C_MAIN_REF");
				listKey.add(lstrKey);
				bLoop = rs.next();
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listKey;
	}

	public static void testInsertData() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		try {
			StringBuffer sbInsert = new StringBuffer();
			sbInsert.append("INSERT INTO EXIMTRX.BILL_TEST (C_MAIN_REF,C_LOCKED_FLAG) VALUES(?,?)");
			PreparedStatement preparedStatement = connection.prepareStatement(sbInsert.toString());
			for (int i = 0; i < 10000; i++) {
				preparedStatement.setString(1, String.valueOf(i));
				preparedStatement.setString(2, "F");
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			preparedStatement.close();
			preparedStatement = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.close();
		connection = null;
	}

	public static void testUpdateableUsePreparedStatement(Connection connection, List<String> listKey) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			StringBuffer sbQuery = new StringBuffer("UPDATE EXIMTRX.BILL_TEST SET C_LOCKED_FLAG = 'T' WHERE C_LOCKED_FLAG = 'F' AND C_MAIN_REF = ?");
			String strQuery = sbQuery.toString();
			PreparedStatement preparedStatement = connection.prepareStatement(strQuery);
			int liSize = listKey.size();
			for (int i = 0; i < liSize; i++) {
				preparedStatement.setString(1, listKey.get(i));
				preparedStatement.addBatch();
			}
			int[] iReturn = preparedStatement.executeBatch();
			System.out.println(iReturn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int[] testUpdateableUseStatement(Connection connection, List<String> listKey) {
		try {
			StringBuffer sbQuery = new StringBuffer("UPDATE EXIMTRX.BILL_TEST SET C_LOCKED_FLAG = 'T' WHERE C_LOCKED_FLAG = 'F' AND C_MAIN_REF = '");
			String strQuery = sbQuery.toString();
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			int liSize = listKey.size();
			for (int i = 0; i < liSize; i++) {
				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(strQuery);
				sbSQL.append(listKey.get(i));
				sbSQL.append("'");
				statement.addBatch(sbSQL.toString());
			}
			int[] iReturn = statement.executeBatch();
			connection.commit();
			statement.close();
			statement = null;
			return iReturn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void testDeleteData() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("DELETE FROM EXIMTRX.BILL_TEST");
			String strQuery = sbQuery.toString();
			PreparedStatement stmt = connection.prepareStatement(strQuery);
			int iReturn = stmt.executeUpdate();
			stmt.close();
			stmt = null;
			System.out.println(iReturn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.close();
		connection = null;
	}
}