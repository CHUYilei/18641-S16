package adapter;

import model.Automobile;
import model.OptionSet;
import util.Util;

/**
 * Created by ychu1 on 16/2/7.
 *
 * Abstract class implements real functionalities of creating and updating automobile objects
 */
public abstract class ProxyAutomobile implements CreateAuto, UpdateAuto {
    private Automobile autoObj;

    /**
     *  Given a text file name, build an instance of Automobile.
     *  This method does not have to return the Auto instance.
     *
     * @param filename text file name
     */
    public void buildAuto(String filename){
        autoObj = Util.buildAutoObject(filename);
    }

    /**
     * Search and print the properties of a given Auto model.
     *
     * @param modelname modelname
     */
    public void printAuto(String modelname){
        autoObj.print();
    }


    /**
     * Search the Model for a given OptionSet and sets the name of OptionSet to newName.
     *
     * @param modelname model name
     * @param optionSetname the option set's name to search
     * @param newName the new name
     */
    public void updateOptionSetName(String modelname, String optionSetname, String newName){
        autoObj.updateOptionSetName(optionSetname,newName);
    }

    /**
     * Search the Model for a given OptionSet and Option name, and sets the price to newPrice.
     * @param modelname model name
     * @param optionSetName option set name
     * @param optionName option name
     * @param newPrice new price
     */
    public void updateOptionPrice(String modelname, String optionSetName, String optionName, float newPrice){
        autoObj.updateOptionPrice(optionSetName, optionName, newPrice);
    }

    /**
     * getter
     * @return automobile object
     */
    public Automobile getAutoObject(){
        return autoObj;
    }
}
