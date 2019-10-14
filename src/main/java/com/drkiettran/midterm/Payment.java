package com.drkiettran.midterm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <code>
 * mysql> describe payment;
 * +--------------+----------------------+------+-----+-------------------+-----------------------------+
 * | Field        | Type                 | Null | Key | Default           | Extra                       |
 * +--------------+----------------------+------+-----+-------------------+-----------------------------+
 * | payment_id   | smallint(5) unsigned | NO   | PRI | NULL              | auto_increment              |
 * | customer_id  | smallint(5) unsigned | NO   | MUL | NULL              |                             |
 * | staff_id     | tinyint(3) unsigned  | NO   | MUL | NULL              |                             |
 * | rental_id    | int(11)              | YES  | MUL | NULL              |                             |
 * | amount       | decimal(5,2)         | NO   |     | NULL              |                             |
 * | payment_date | datetime             | NO   |     | NULL              |                             |
 * | last_update  | timestamp            | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +--------------+----------------------+------+-----+-------------------+-----------------------------+
 * </code>
 * 
 * 
 * @author student
 *
 */
public class Payment {
	private static final String GET_NUMBER_PAYMENTS_AND_SUM_BY_CUSTOMER_ID = "select count(customer_id), sum(amount) from payment where customer_id=?";
	private static final Logger LOGGER = LoggerFactory.getLogger(Payment.class);
	private Connection connection;

	private int numPayments;
	private double totalAmount;

	public Payment(Connection connection) {
		this.connection = connection;
	}

	public void getPaymentsAndAmountByCustomerId(int customerId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(GET_NUMBER_PAYMENTS_AND_SUM_BY_CUSTOMER_ID);
			stmt.setInt(1, customerId);
			rs = stmt.executeQuery();
			rs.next();

			this.numPayments = rs.getInt(1);
			this.totalAmount = rs.getDouble(2);

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

	public int getNumPayments() {
		return numPayments;
	}

	public void setNumPayments(int numPayments) {
		this.numPayments = numPayments;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
