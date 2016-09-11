package adapter;

import java.util.Iterator;

import model.Automobile;
import model.OptionSet;
import util.Util;

/**
 * Created by ychu1 on 16/2/7.
 *
 * Abstract class implements real functionalities of creating and updating automobile objects
 */
public abstract class ProxyAutomobile implements CreateAuto, UpdateAuto {
    private ModelSet modelSet;
    
    /**
     * Constructor
     */
    public ProxyAutomobile(){
        this.modelSet = new ModelSet();
    }

    /**
     * Given a text file name, build an instance of Automobile.
     * This method does not have to return the Auto instance.
     *
     * @param filename text file name
     */
    public void buildAuto(String filename){
        Automobile autoObj = Util.buildAutoObject(filename);
        this.modelSet.addModel(autoObj);
    }

    /**
     * Search and print the properties of a given Auto model.
     *
     * @param modelname modelname
     */
    public void printAuto(String modelname){
        Automobile auto = this.modelSet.getModel(modelname);
        if(auto != null){
            auto.print();
        }
    }


    /**
     * Search the Model for a given OptionSet and sets the name of OptionSet to newName.
     *
     * @param modelname model name
     * @param optionSetname the option set's name to search
     * @param newName the new name
     */
    public void updateOptionSetName(String modelname, String optionSetname, String newName){
        Automobile auto = this.modelSet.getModel(modelname);
        if(auto != null){
            auto.updateOptionSetName(optionSetname,newName);
        }
    }

    /**
     * Search the Model for a given OptionSet and Option name, and sets the price to newPrice.
     * @param modelname model name
     * @param optionSetName option set name
     * @param optionName option name
     * @param newPrice new price
     */
    public void updateOptionPrice(String modelname, String optionSetName, String optionName, float newPrice){
        Automobile auto = this.modelSet.getModel(modelname);
        if(auto != null){
            auto.updateOptionPrice(optionSetName, optionName, newPrice);
        }
    }

    /**
     * getter
     * @return automobile object
     */
    public Automobile getAutoObject(String modelname){
        return this.modelSet.getModel(modelname);
    }
    
    /**
     * Print all model's info using iterator
     */
    public void printAll(){
        Iterator<Automobile> iter = this.modelSet.getIterator();
        int cnt = 1;

        while (iter.hasNext()){
            System.out.println("********* Model No."+(cnt++)+": *********");
            Automobile auto = iter.next();
            auto.print();
            System.out.println();
        }
    }
}
