package com.drkiettran.midterm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>
 * mysql> describe country;
 * 
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | Field       | Type                 | Null | Key | Default           | Extra                       |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | country_id  | smallint(5) unsigned | NO   | PRI | NULL              | auto_increment              |
 * | country     | varchar(50)          | NO   |     | NULL              |                             |
 * | last_update | timestamp            | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * </code>
 * 
 * @author student
 *
 */
public class Country {
	private static final String GET_BY_ID = "select * from country where country_id=?";
	private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);
	private Connection connection;

	private int id;
	private String country;
	private Timestamp lastUpdate;

	public Country(Connection connection) {
		this.connection = connection;
	}

	public void getById(int countryId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(GET_BY_ID);
			stmt.setInt(1, countryId);
			rs = stmt.executeQuery();
			rs.next();

			this.id = rs.getInt("country_id");
			this.country = rs.getString("country");
			this.lastUpdate = rs.getTimestamp("last_update");

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
