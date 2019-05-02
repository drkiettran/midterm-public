package com.drkiettran.jdbc.greetings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnection {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.info("Unable to locate JDBC driver {}", JDBC_DRIVER);
		}
	}

	public static Connection getConnection(String url, String user, String password) {
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (SQLException e) {
			LOGGER.info("Unable to get connection {}", e);
		}
		return null;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Unable to close connection {}", e);
			}
		}
	}
}
