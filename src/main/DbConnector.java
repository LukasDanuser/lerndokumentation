package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DbConnector {

	private static DbConnector instance = new DbConnector();

	private DbConnector() {
	}

	public static DbConnector getInstance() {
		return instance;
	}

	public DB getDB() throws IOException {
		String connectionString1 = "";
		String connectionString2 = "";
		String passwordString = "";
		FileInputStream fis = new FileInputStream(
				"src/connection.properties");
		Properties prop = new Properties();
		prop.load(fis);
		connectionString1 = prop.getProperty("connectionString1");
		connectionString2 = prop.getProperty("connectionString2");
		passwordString = prop.getProperty("password");
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		@SuppressWarnings("unused")
		MongoClientURI uri = new MongoClientURI(connectionString1 + passwordString + connectionString2);
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient("localhost", 27017);
		// MongoClient client = new MongoClient(uri);

		@SuppressWarnings("deprecation")
		DB db = client.getDB("lerndokumentation");
		return db;
	}

	@SuppressWarnings("unused")
	public void connectDB() throws IOException {
		String connectionString1 = "";
		String connectionString2 = "";
		String passwordString = "";
		FileInputStream fis = new FileInputStream(
				"/home/lukasd/eclipse-workspace/lerndokumentation/src/connection.properties");
		Properties prop = new Properties();
		prop.load(fis);
		connectionString1 = prop.getProperty("connectionString1");
		connectionString2 = prop.getProperty("connectionString2");
		passwordString = prop.getProperty("password");
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		@SuppressWarnings("unused")
		MongoClientURI uri = new MongoClientURI(connectionString1 + passwordString + connectionString2);
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient("localhost", 27017);
		// MongoClient client = new MongoClient(uri);

		@SuppressWarnings("deprecation")
		DB db = client.getDB("lerndokumentation");

	}

}
