package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
		String connectionString = "";
		File file = new File("connection.properties");
		String path = file.getAbsolutePath();
		try (InputStream input = new FileInputStream(path)) {
			Properties prop = new Properties();
			prop.load(input);
			connectionString = prop.getProperty("connectionString");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		@SuppressWarnings("unused")
		MongoClientURI uri = new MongoClientURI(connectionString);
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient("localhost", 27017);
//		MongoClient client = new MongoClient(uri);

		@SuppressWarnings("deprecation")
		DB db = client.getDB("lerndokumentation");

		return db;
	}

	@SuppressWarnings("unused")
	public void connectDB() throws IOException {
		String connectionString = "";
		File file = new File("connection.properties");
		String path = file.getAbsolutePath();
		try (InputStream input = new FileInputStream(path)) {
			Properties prop = new Properties();
			prop.load(input);
			connectionString = prop.getProperty("connectionString");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		@SuppressWarnings("unused")
		MongoClientURI uri = new MongoClientURI(connectionString);
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient("localhost", 27017);
//		MongoClient client = new MongoClient(uri);

		@SuppressWarnings("deprecation")
		DB db = client.getDB("lerndokumentation");

	}

}
