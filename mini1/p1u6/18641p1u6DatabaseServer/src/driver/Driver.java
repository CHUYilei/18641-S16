package driver;

import adapter.BuildAuto;
import util.DatabaseIO;

/**
 * Project 1 Unit 4 Driver class for Server
 */
public class Driver {
    public static void main(String[] args) {
    	// create database and tables
    	BuildAuto builder = new BuildAuto();

    	// add models into database
    	System.out.println("\n===================== (1) test adding car model ===================\n");
    	builder.buildAuto("AudiA6.txt");
    	builder.buildAuto("FordFocus.properties");
    	   	
    	DatabaseIO.printDb();
    	
    	System.out.println("\n===================== (2) test updating option set name ===================\n");
    	builder.updateOptionSetName("Audi A6", "Color", "Car color");
    	builder.updateOptionPrice("Ford Focus", "Color", "Liquid Grey Clearcoat Metallic", 666);
    	DatabaseIO.printDb();
    	
    	System.out.println("\n===================== (3) test deleting car model ===================");
    	builder.removeModel("Audi A6");
    	builder.removeModel("Ford Focus");
    	DatabaseIO.printDb();
    }
}
