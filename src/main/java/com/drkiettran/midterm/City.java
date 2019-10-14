package com.drkiettran.midterm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <code>
 * mysql> describe city;
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | Field       | Type                 | Null | Key | Default           | Extra                       |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | city_id     | smallint(5) unsigned | NO   | PRI | NULL              | auto_increment              |
 * | city        | varchar(50)          | NO   |     | NULL              |                             |
 * | country_id  | smallint(5) unsigned | NO   | MUL | NULL              |                             |
 * | last_update | timestamp            | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * </code>
 * 
 * @author student
 *
 */
public class City {
	private static final String GET_BY_ID = "select * from city where city_id=?";
	private static final Logger LOGGER = LoggerFactory.getLogger(City.class);
	private Connection connection;

	private int id;
	private String city;
	private int countryId;
	private Timestamp lastUpdate;

	public City(Connection connection) {
		this.connection = connection;
	}

	public void getById(int cityId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(GET_BY_ID);
			stmt.setInt(1, cityId);
			rs = stmt.executeQuery();
			rs.next();

			this.id = rs.getInt("city_id");
			this.city = rs.getString("city");
			this.countryId = rs.getInt("country_id");
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
