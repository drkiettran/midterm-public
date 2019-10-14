package com.drkiettran.midterm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>
 * mysql> describe customer;
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | Field       | Type                 | Null | Key | Default           | Extra                       |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * | customer_id | smallint(5) unsigned | NO   | PRI | NULL              | auto_increment              |
 * | store_id    | tinyint(3) unsigned  | NO   | MUL | NULL              |                             |
 * | first_name  | varchar(45)          | NO   |     | NULL              |                             |
 * | last_name   | varchar(45)          | NO   | MUL | NULL              |                             |
 * | email       | varchar(50)          | YES  |     | NULL              |                             |
 * | address_id  | smallint(5) unsigned | NO   | MUL | NULL              |                             |
 * | active      | tinyint(1)           | NO   |     | 1                 |                             |
 * | create_date | datetime             | NO   |     | NULL              |                             |
 * | last_update | timestamp            | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +-------------+----------------------+------+-----+-------------------+-----------------------------+
 * </code>
 * 
 * @author student
 *
 */
public class Customer {
	private static final String GET_BY_FIRST_LAST_NAME = "select * from customer where first_name=? and last_name=?";
	private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);
	private Connection connection;

	private int id;
	private int storeId;
	private String firstName;
	private String lastName;
	private String email;
	private int addressId;
	private boolean active;
	private Date createDate;
	private Timestamp lastUpdate;

	public Customer(Connection connection) {
		this.connection = connection;
	}

	public void getByFirstLastName(String firstName, String lastName) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(GET_BY_FIRST_LAST_NAME);
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			rs = stmt.executeQuery();
			rs.next();

			this.id = rs.getInt("customer_id");
			this.storeId = rs.getInt("store_id");
			this.firstName = rs.getString("first_name");
			this.lastName = rs.getString("last_name");
			this.email = rs.getString("email");
			this.addressId = rs.getInt("address_id");
			this.active = rs.getInt("active") == 1 ? true : false;
			this.createDate = rs.getDate("create_date");
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

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
