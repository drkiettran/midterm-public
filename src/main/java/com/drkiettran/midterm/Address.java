package com.drkiettran.midterm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mysql> describe address; <code>
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | Field       | Type                 | Null | Key | Default           | Extra                       |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | address_id  | smallint(5) unsigned | NO   | PRI | NULL              | auto_increment              |
 * | address     | varchar(50)          | NO   |     | NULL              |                             |
 * | address2    | varchar(50)          | YES  |     | NULL              |                             |
 * | district    | varchar(20)          | NO   |     | NULL              |                             |
 * | city_id     | smallint(5) unsigned | NO   | MUL | NULL              |                             |
 * | postal_code | varchar(10)          | YES  |     | NULL              |                             |
 * | phone       | varchar(20)          | NO   |     | NULL              |                             |
 * | location    | geometry             | NO   | MUL | NULL              |                             |
 * | last_update | timestamp            | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+ 
 * </code>
 * 
 * @author student
 *
 */
public class Address {
	private static final String GET_BY_ID = "select * from address where address_id=?";
	private static final Logger LOGGER = LoggerFactory.getLogger(Address.class);
	private Connection connection;

	private int id;
	private String address;
	private String address2;
	private String district;
	private int cityId;
	private String postalCode;
	private String phone;
	private Timestamp lastUpdate;

	public Address(Connection connection) {
		this.connection = connection;
	}

	public void getById(int addressId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(GET_BY_ID);
			stmt.setInt(1, addressId);
			rs = stmt.executeQuery();
			rs.next();

			this.cityId = rs.getInt("address_id");
			this.address = rs.getString("address");
			this.address2 = rs.getString("address2");
			this.district = rs.getString("district");
			this.cityId = rs.getInt("city_id");
			this.postalCode = rs.getString("postal_code");
			this.phone = rs.getString("phone");
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
