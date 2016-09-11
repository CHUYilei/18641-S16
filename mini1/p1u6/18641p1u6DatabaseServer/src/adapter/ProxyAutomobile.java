package adapter;

import model.Automobile;
import scale.EditOptions;
import util.DatabaseIO;
import util.FileIO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Created by ychu1 on 16/2/12.
 * Abstract class implements real functionalities of creating and updating automobile objects
 * CRUD operations would have concurrent verison as well
 */
public abstract class ProxyAutomobile {
    private static LinkedHashMap<String, Automobile> modelSet = null;
    private static DatabaseIO dbIO = null;

    /**
     * Constructor
     */
    public ProxyAutomobile() {
    	if(modelSet == null){
            modelSet = new LinkedHashMap<String, Automobile>();
    	}
    	
    	dbIO = new DatabaseIO();
    	dbIO.createDb();
    }

    /**
     * Given a text file name, build an instance of Automobile.
     *
     * This method will view the file type to determine which method to take
     *
     * @param filename text file name
     */
    public void buildAuto(String filename){
        if(filename.endsWith(".properties")){
            buildAutoFromPropertiesFile(filename);
        }else if(filename.endsWith(".txt")){
            buildAutoFromTxt(filename);
        }
    }

    /**
     * Given a text file name, build an instance of Automobile.
     * This method does not have to return the Auto instance.
     *
     * @param filename text file name
     */
    public void buildAutoFromTxt(String filename) {
        Automobile autoObj = FileIO.buildAutoObjectFromTxt(filename);
        addModel(autoObj);
    }

    /**
     * Given a properties object, build an automobile from it and add to linkedhashmap
     * @param prop properties object
     */
    public void buildAutoFromPropertiesObject(Properties prop){
        Automobile autoObj = FileIO.buildAutoObjectFromPropertiesObject(prop);
        addModel(autoObj);
    }

    /**
     * Given a .properties file, build the automobile
     * @param filename .properties file
     */
    public void buildAutoFromPropertiesFile(String filename){
        Properties prop = FileIO.buildPropertiesObjectFromFile(filename);
        buildAutoFromPropertiesObject(prop);
    }

    /**
     * Search and print the properties of a given Auto model.
     *
     * @param modelname model name
     */
    public void printAuto(String modelname) {
        Automobile auto = modelSet.get(modelname);
        if (auto != null) {
            auto.print();
        }
    }
    
    /* Concurrent version of CRUD */
    /**
     * Search the Model for a given OptionSet and set its choice
     *
     * @param modelname     model name
     * @param optionSetname the option set's name to search
     * @param choice        the choice
     */
    public void updateOptionSetChoice(String modelname, String optionSetname, String choice) {
        Automobile auto = modelSet.get(modelname);
        if (auto != null) {
            EditOptions eo = new EditOptions(2);
            eo.setAuto(auto);
            eo.setOptionSetName(optionSetname);
            eo.setOptionSetName(optionSetname);
            eo.setChoice(choice);
            Thread editOptionThread = new Thread(eo);
            editOptionThread.start();
        }
    }

    /**
     * Multithreaded version of updateOptionSetName
     * Search the Model for a given OptionSet and sets the name of OptionSet to newName.
     *
     * @param modelname     model name
     * @param optionSetname the option set's name to search
     * @param newName       the new name
     */
    public void updateOptionSetName(String modelname, String optionSetname, String newName) {
        Automobile auto = modelSet.get(modelname);
        if (auto != null) {
            EditOptions eo = new EditOptions(3);
            eo.setAuto(auto);
            eo.setOptionSetName(optionSetname);
            eo.setNewName(newName);
            Thread editOptionThread = new Thread(eo);
            editOptionThread.start();
            
            // in database
            updateOptionSetNameInDb(modelname,optionSetname,newName);
        }
    }

    /**
     * Multithreaded version of updateOptionPrice
     *
     * @param modelname     model name
     * @param optionSetName option set name
     * @param optionName    option name
     * @param newPrice      new price
     */
    public void updateOptionPrice(String modelname, String optionSetName, String optionName, float newPrice) {
        Automobile auto = modelSet.get(modelname);
        if (auto != null) {
            EditOptions eo = new EditOptions(1);
            eo.setAuto(auto);
            eo.setOptionSetName(optionSetName);
            eo.setOptionName(optionName);
            eo.setPrice(newPrice);
            Thread editOptionThread = new Thread(eo);
            editOptionThread.start();
            
            // in database
            updateOptionPriceInDb(modelname, optionSetName,optionName,newPrice);
        }
    }

    /*
     * add an Automobile model with its name into map
     */
    public void addModel(Automobile auto) {
    	if(modelSet.containsKey(auto.getName())){
    		return;
    	}
    	
        modelSet.put(auto.getName(), auto);
        addAutoInDb(auto);
    }
    
    public void removeModel(String name){
    	if(!modelSet.containsKey(name)){
    		return;
    	}
    	
    	modelSet.remove(name);
    	deleteAutoInDb(name);
    }

    /*
     * find the Automobile from the given name
     */
    public Automobile getModel(String name) {
        if (modelSet.containsKey(name)) {
            return modelSet.get(name);
        }

        System.out.println("Name " + name + " not exists in model set");
        return null;
    }

    /**
     * Print all model's info using iterator
     */
    public void printAll() {
        Iterator<Automobile> iter = modelSet.values().iterator();
        int cnt = 1;

        while (iter.hasNext()) {
            System.out.println("********* Model No." + (cnt++) + ": *********");
            Automobile auto = iter.next();
            auto.print();
            System.out.println();
        }
    }


    /**
     * Given a file, use CreateAuto interface to build it
     * @param filename given file name
     */
    public void buildAutoFromFile(String filename){
        this.buildAuto(filename);
    }

    /**
     * Get a list of model names from linkedhashmap
     * @return the key(name) list
     */
    public List<String> getAvailableModelList(){
        return new ArrayList(modelSet.keySet());
    }

    /**
     * Extract the chosen auto insatnce and send out using outputStream
     * @param name chosen model name
     * @param outStream ObjectOutputStream for server to send to client
     */
    public void sendChosenModel(String name,ObjectOutputStream outStream){
        Automobile auto = getModel(name);

        try {
            outStream.writeObject(auto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Project 1 Unit 6 new database methods
	 */
	public void addAutoInDb(Automobile auto){
		int size = modelSet.size();
		dbIO.addAutoInDb(auto, size);
	}
	
	public void deleteAutoInDb(String autoName){
		dbIO.deleteAutoInDb(autoName);
	}
	
	public void updateOptionSetNameInDb(String autoName,String oldSetName,String newName){
		dbIO.updateOptionSetNameInDb(autoName, oldSetName, newName);
	}
	
	
	public void updateOptionPriceInDb(String autoName, String optionSet,String option,float newPrice){
		dbIO.updateOptionPriceInDb(autoName, optionSet, option, newPrice);
	}
	
	
	

}
