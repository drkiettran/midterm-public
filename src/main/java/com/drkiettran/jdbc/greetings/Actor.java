package com.drkiettran.jdbc.greetings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor {
	private static final String GET_ACTOR_BY_LAST_NAME = "select actor_id, first_name, last_name from actor where last_name = ?";
	private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);
	private Connection connection;
	private String lastName = "";
	private String firstName = "";
	private int id = -1;
	private String expectedLastName;
	private boolean noRecord = false;
	private int count;

	public Actor(Connection connection, String expectedLastName) {
		this.connection = connection;
		getActorByLastName(expectedLastName);
	}

	public void getActorByLastName(String expectedLastName) {
		this.expectedLastName = expectedLastName;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(GET_ACTOR_BY_LAST_NAME);
			stmt.setString(1, expectedLastName);
			rs = stmt.executeQuery();

			count = 0;
			// take the first find.
			if (rs.next()) {
				this.id = rs.getInt(1);
				this.firstName = rs.getString(2);
				this.lastName = rs.getString(3);
				count++;
			} else {
				noRecord = true;
			}

			while (rs.next()) {
				count++;
			}

			LOGGER.info("records found: {}", count);

		} catch (SQLException e) {
			LOGGER.info("ERROR: {}", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				LOGGER.info("ERROR: {}", e);
			}
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean noRecord() {
		return noRecord;
	}

	public void setNoRecord(boolean noRecord) {
		this.noRecord = noRecord;
	}

	public String getExpectedLastName() {
		return expectedLastName;
	}

}
