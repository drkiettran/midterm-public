package com.drkiettran.midterm;

import java.net.UnknownHostException;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create a simple JDBC program to display information about a given customer.
 * Input is first name and last name.
 * 
 * Tables involved: customer, customer_list, address, city, country Output:
 * 
 * You should run the program as follows (the output is to follow):
 * 
 * <code>

kiet@student-VirtualBox:~/dev/week_7/midterm$ java -jar target/midterm-jar-with-dependencies.jar johnnie chisholm

Customer information: 
ID: 571
Store ID: 2
Name: JOHNNIE CHISHOLM
Email: JOHNNIE.CHISHOLM@sakilacustomer.org
Active: yes
Address: 1501 Pangkal Pinang Avenue, Plock, 943, Poland
Address2: 
Phone: 770864062795
Last Update: 2006-02-14 23:57:20.0
Number of payments: 24
Total amount: 121.76

[main] INFO com.drkiettran.midterm.Main - Completed!

 * </code>
 * 
 * Tables involved: customer, customer_list, address, city, country
 * 
 * The application must access to these tables for the sakila database:
 * 
 * <code>
 * - customer: select * from customer where first_name=? and last_name=?;
 * - address: select * from address where address_id=?;
 * - city: select * from city where city_id=?;
 * - country: select * from country where country_id=?;
 * - payment: select count(customer_id), sum(amount) from payment where customer_id=?;
 * </code>
 * 
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static final String SAKILA_DB_URL = "jdbc:mysql://localhost:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String UID = "student";
	private static final String PSW = "password";

	public static void main(String... args) throws UnknownHostException {
		if (args.length < 1) {
			LOGGER.info(
					"**** java -cp ./target/greetings-jdbc-jar-with-dependencies.jar com.drkiettran.jdbc.greetings.Main WILLIS ****");
			return;
		}

		String firstName = args[0];
		String lastName = args[1];

		Connection connection = DbConnection.getConnection(SAKILA_DB_URL, UID, PSW);

		Customer customer = new Customer(connection);
		customer.getByFirstLastName(firstName, lastName);
		System.out.println("");
		StringBuilder sb = new StringBuilder("Customer information: \n");
		sb.append("ID: ").append(customer.getId()).append('\n');
		sb.append("Store ID: ").append(customer.getStoreId()).append('\n');
		sb.append("Name: ").append(customer.getFirstName());
		sb.append(" ").append(customer.getLastName()).append('\n');

		sb.append("Email: ").append(customer.getEmail()).append('\n');
		sb.append("Active: ").append(customer.isActive() ? "yes" : "no").append('\n');

		Address address = new Address(connection);
		address.getById(customer.getAddressId());
		sb.append("Address: ").append(address.getAddress());

		City city = new City(connection);
		city.getById(address.getCityId());
		Country country = new Country(connection);
		country.getById(city.getCountryId());

		sb.append(", ").append(city.getCity());
		sb.append(", ").append(address.getPostalCode());
		sb.append(", ").append(country.getCountry()).append('\n');

		Payment payment = new Payment(connection);
		payment.getPaymentsAndAmountByCustomerId(customer.getId());

		sb.append("Address2: ").append(address.getAddress2()).append('\n');
		sb.append("Phone: ").append(address.getPhone()).append('\n');
		sb.append("Last Update: ").append(customer.getLastUpdate()).append('\n');

		sb.append("Number of payments: ").append(payment.getNumPayments()).append('\n');
		sb.append("Total amount: ").append(payment.getTotalAmount()).append('\n');
		System.out.println(sb.toString());

		DbConnection.closeConnection(connection);
		LOGGER.info("Completed!");
	}
}
