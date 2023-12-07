package org.example.n2Exe1MySQL.herramienta;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionFloristeria {
	
	private Connection dbConnection = null;
	
	public ConnectionFloristeria () {
		
	
	}
	
	public Connection getConnection () {
		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/t3n2Floristeria", "root", "");
			System.out.println("Connected");
		} catch (Exception ex) {
			System.out.println("There is no connection");
			ex.printStackTrace();
		}
		return dbConnection;
	}
}
