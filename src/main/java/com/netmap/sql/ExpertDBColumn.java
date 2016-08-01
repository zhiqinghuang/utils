package com.netmap.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ExpertDBColumn {

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String lstrURL = "jdbc:oracle:thin:@10.39.107.72:1521:SBDEVDB";
			String lstrUser = "EXIMTRX";
			String lstrPassword = "EXIMTRX";
			Connection connection = DriverManager.getConnection(lstrURL, lstrUser, lstrPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM EXIMTRX.IAAC_AMZMASTERLAY WHERE IA_C_REF_NO = '111'");
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int liColumnCount = resultSetMetaData.getColumnCount();
			StringBuffer sbSQL = new StringBuffer("SELECT ");
			for (int i = 0; i < liColumnCount; i++) {
				if(i > 0){
					sbSQL.append(",");	
				}
				sbSQL.append(resultSetMetaData.getColumnName(i+1));
			}
			sbSQL.append(" FROM EXIMTRX.IAAC_AMZMASTERLAY");
			System.out.println(sbSQL.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
