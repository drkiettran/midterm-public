package com.drkiettran.jdbc.greetings;

import java.net.UnknownHostException;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDBC example using sakila database that comes with mysql database. You need
 * to create the database and import them accordingly.
 * 
 * You can download the database here if you are not able to locate them from
 * your install.
 * 
 * https://github.com/datacharmer/test_db/tree/master/sakila
 * 
 * This last_name exists: LOLLOBRIGIDA
 * 
 * @author ktran
 *
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static final String SAKILA_DB_URL = "jdbc:mysql://localhost:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String UID = "student";
	private static final String PSW = "password";

	public static void main(String... args) throws UnknownHostException {
		if (args.length < 1) {
			LOGGER.info("**** java -cp ./target/greetings-jdbc-jar-with-dependencies.jar com.drkiettran.jdbc.greetings.Main WILLIS ****");
			return;
		}

		LOGGER.info("text: {}", args[0]);
		Connection connection = DbConnection.getConnection(SAKILA_DB_URL, UID, PSW);
		Actor actor = new Actor(connection, args[0]);
		String fullName = String.format("%s %s", actor.getFirstName(), actor.getLastName());
		LOGGER.info("found {}", fullName);
		DbConnection.closeConnection(connection);

		Greetings greetings = new Greetings();
		LOGGER.info(greetings.hello(fullName));
	}
}
