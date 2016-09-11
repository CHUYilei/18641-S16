package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import model.Automobile;

/**
 * Util class for database operation
 * Project 1 Unit 6
 */
public class DatabaseIO {
	private String host = "127.0.0.1";
	private int port = 3306;
	private String url = "jdbc:mysql://"+ host + ":" + port;
	private String username = "18641";
	private String password = "p1u6";
	private static Connection connection = null;

	private static Properties updateDbCommandProp = null;
	
	private String dbCommandDir = "dbCommand/";
	
	public DatabaseIO(){
		// connect to database
		try {
			connection = DriverManager.getConnection(url,username, password);
		} catch (SQLException e) {
			System.err.println("Cannot connect to database.");
			e.printStackTrace();
		} 
		
		if(connection != null){
			System.err.println("Connected to database!");
		}
		
		// load commands from text files and properties files
		updateDbCommandProp = new Properties();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(dbCommandDir+"updateDb.properties");
            updateDbCommandProp.load(fin);         
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fin != null){
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }		
	}
	
	/**
	 * Delete database if any
	 * create db and tables using the commands read from txt file
	 */
	public void createDb(){
		// read create db commands from text file
		BufferedReader br = null;
		String line = null;
		Statement statement;
		
        try {
        	statement = connection.createStatement();
            br = new BufferedReader(new FileReader(dbCommandDir+"createDb.txt"));
            
            while((line = br.readLine()) != null){
            	statement.execute(line);
            }
                        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        
        System.out.println("Database created successfully!");		
	}
	
	/**
	 * Add automobile into database
	 * @param auto automobile
	 * @param modelId id
	 */
	public void addAutoInDb(Automobile auto,int modelId){
		PreparedStatement ps = null;
		PreparedStatement psSet = null;
		PreparedStatement psOption = null;
		
		try {
			// add model
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("addModel"));
			
			ps.setInt(1, modelId);
			ps.setString(2, auto.getName());
			ps.setFloat(3, auto.getBasePrice());
			ps.executeUpdate();
			
			// add option set
			psSet = connection.prepareStatement(updateDbCommandProp.getProperty("addOptionSet"));
			psOption = connection.prepareStatement(updateDbCommandProp.getProperty("addOption"));
			
			for(int i=0;i<auto.getOpsetsSize();++i){
				psSet.setInt(1, i); //set id
				psSet.setInt(2,modelId); // model id
				psSet.setString(3, auto.getOpsetNameAt(i)); // set name
				psSet.executeUpdate();
				
				// add options
				for(int j=0;j<auto.getOptionNumForOpsetAt(i);++j){
					psOption.setInt(1, j); //option id
					psOption.setInt(2, i); // set id
					psOption.setInt(3, modelId); // model id
					psOption.setString(4, auto.getOptionName(i, j));
					psOption.setFloat(5, auto.getOptionPrice(i, j));
					psOption.executeUpdate();
				}				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete automobile from database
	 * @param name model name
	 */
	public void deleteAutoInDb(String name){
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("getModelID"));
			ps.setString(1, name);
			
			ResultSet result = ps.executeQuery();
			result.next();
			int modelId = result.getInt(1);
			
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("deleteOption"));
			ps.setInt(1, modelId);
			ps.executeUpdate();
			
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("deleteOptionSet"));
			ps.setInt(1, modelId);
			ps.executeUpdate();
			
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("deleteModel"));
			ps.setInt(1, modelId);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Update option set name
	 * @param modelName model name
	 * @param oldSetName old set name
	 * @param newSetName new set name
	 */
	public void updateOptionSetNameInDb(String modelName, String oldSetName, String newSetName){
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("getModelID"));
			ps.setString(1, modelName);
			
			ResultSet result = ps.executeQuery();
			result.next();
			int modelId = result.getInt(1);
			
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("updateOptionSetName"));
			ps.setString(1, newSetName);
			ps.setInt(2, modelId);
			ps.setString(3, oldSetName);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * Update option price in db
	 * @param modelName model name
	 * @param setName set name
	 * @param optionName option name
	 * @param newPrice new price
	 */
	public void updateOptionPriceInDb(String modelName, String setName, String optionName,float newPrice){
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("getModelID"));
			ps.setString(1, modelName);
			
			ResultSet result = ps.executeQuery();
			result.next();
			int modelId = result.getInt(1);
			
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("getSetID"));
			ps.setInt(1, modelId);
			ps.setString(2, setName);
			result = ps.executeQuery();
			result.next();
			int setId = result.getInt(1);
			
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("updateOptionPrice"));
			ps.setFloat(1,newPrice);
			ps.setInt(2, modelId);
			ps.setInt(3, setId);
			ps.setString(4, optionName);
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * Print all tables in db
	 */
	public static void printDb(){
		System.out.println("========== start printing the database =============== ");
		
		// CarModel table
		System.out.println("========== CarModel table =============== ");
		printCarModelTable();
		
		// CarOptionSet table
		System.out.println("========== CarOptionSet table =============== ");
		printCarOptionSetTable();
		
		// CarOption table
		System.out.println("========== CarOption table =============== ");
		printCarOptionTable();
		
		System.out.println("========== finish printing the database =============== ");
	}
	
	/**
	 * print model table
	 */
	public static void printCarModelTable(){
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("selectAllCarModel"));			
			result = ps.executeQuery();
			
			System.out.format("%5s%14s%27s\n","modelId","make","base price");
			
			while(result.next()){
				System.out.format("%5s%18s%23s\n",result.getString(1),result.getString(2),result.getString(3));
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * print option set table
	 */
	public static void printCarOptionSetTable(){
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("selectAllCarOptionSet"));			
			result = ps.executeQuery();
			
			System.out.format("%5s%17s%27s\n","setId","modelId","setName");
			
			while(result.next()){
				System.out.format("%5s%18s%23s\n",result.getString(1),result.getString(2),result.getString(3));
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * print option table
	 */
	public static void printCarOptionTable(){
		PreparedStatement ps = null;
		ResultSet result = null;
		
		try {
			ps = connection.prepareStatement(updateDbCommandProp.getProperty("selectAllCarOption"));			
			result = ps.executeQuery();
			
			System.out.format("%5s%14s%27s%30s%18s\n","optionId","setId","modelId","optionName","optionPrice");
			
			while(result.next()){
				System.out.format("%5s%18s%23s%36s%15s\n",result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5));
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}		
}
