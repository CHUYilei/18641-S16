package adapter;

import model.Automobile;
import scale.EditOptions;
import util.Util;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by ychu1 on 16/2/12.
 * Abstract class implements real functionalities of creating and updating automobile objects
 * CRUD operations would have concurrent verison as well
 */
public abstract class ProxyAutomobile implements CreateAuto, UpdateAuto {
    private static LinkedHashMap<String, Automobile> modelSet;

    /**
     * Constructor
     */
    public ProxyAutomobile() {
        modelSet = new LinkedHashMap<String, Automobile>();
    }

    /**
     * Given a text file name, build an instance of Automobile.
     * This method does not have to return the Auto instance.
     *
     * @param filename text file name
     */
    public void buildAuto(String filename) {
        Automobile autoObj = Util.buildAutoObject(filename);
        addModel(autoObj);
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
        }
    }

    /*
     * add an Automobile model with its name into map
     */
    public void addModel(Automobile auto) {
        modelSet.put(auto.getName(), auto);
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
}
