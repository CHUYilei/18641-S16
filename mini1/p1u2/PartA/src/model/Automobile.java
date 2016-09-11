package model;

import java.io.Serializable;
import exception.AutoException;

/**
 * Model class for automotive
 */
public class Automobile implements Serializable {
    private static final long serialVersionUID = 2590337868401297846L;
    
    private String name;
    private OptionSet opsets[];
    private int basePrice;
    private int size;


    /* Constructors */

    /**
     * Empty constructor (default)
     */
    public Automobile() {
    }

    /**
     * Constructor
     * @param size number of option sets
     * @param name name of car model
     * @param basePrice base price of this modle
     */
    public Automobile(int size, String name, int basePrice) {
        this.initializeOpsets(size);

        this.name = name;

        this.basePrice = basePrice;
        this.size = size;
    }

    /**
     * Initialize the option set array
     * @param size array size
     */
    public void initializeOpsets(int size){
        this.size = size;
        this.opsets = new OptionSet[size];

        for (int i = 0; i < size; i++) {
            this.opsets[i] = new OptionSet();
        }
    }

    /* Getters */

    /**
     * Getter 
     * @return model name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter 
     * @return base price for this model
     */
    public int getBasePrice() {
        return basePrice;
    }

    /**
     * Getter
     * @param idx index in the option set array
     * @return option set
     */
    public OptionSet getOpsetAt(int idx) {
        return opsets[idx];
    }

    /**
     * Find option set in array with that name
     * @param name option set name
     * @return index of that option set, -1 for not exists
     */
    public int findOptionSet(String name) {
        for (int i = 0; i < this.size; i++) {
            if (this.opsets[i].getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    /* Setters */

    /**
     * Setter
     * @param name name of the model
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter
     * @param basePrice base price of the model
     */
    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Setter
     * @param idx index in option set array
     * @param ost option set to put into the array
     */
    public void setOptionsetAt(int idx, OptionSet ost) {
        this.opsets[idx] = ost;
    }

    /**
     * Delete option set at index
     * @param idx index in option set array 
     */
    public void deleteOptionSetAt(int idx) {
        OptionSet temp = opsets[size - 1];
        opsets[size - 1] = opsets[idx];
        opsets[idx] = temp;
        --size;
    }

    /**
     * Update the option set with name
     * @param name option name
     * @param newOpset option set to be updated with
     */
    public void updateOptionSet(String name, OptionSet newOpset) {
        this.opsets[findOptionSet(name)] = newOpset;
    }

    /**
     * Add option at certain option set
     * @param setidx index of option set
     * @param optionidx index of option
     * @param name name of option
     * @param price price of option
     */
    public void addOptionAt(int setidx, int optionidx, String name, int price) {
        this.opsets[setidx].addOption(optionidx, name, price);
    }

    /**
     * Add option set in array at index
     * @param idx index of option set
     * @param name name of option set
     * @param size size of option set
     */
    public void addOptionSetAt(int idx, String name, int size) {
        this.opsets[idx] = new OptionSet(size, name);
    }


    /**
     * Find and then update an option set's name
     * @param opsetName option set's name
     * @param newName option set's new name
     */
    public void updateOptionSetName(String opsetName, String newName){
        int setIdx = findOptionSet(opsetName);

        try{
            if(setIdx < 0){
                throw new AutoException("Option set "+opsetName+" not found.",this);
            }
        } catch (AutoException e){
            e.fix(2);
            return;
        }

        OptionSet set = this.getOpsetAt(setIdx);

        set.setName(newName);
    }

    /**
     * Update option's price
     * @param opsetName set name
     * @param optionName option name
     * @param newPrice new option price
     */
    public void updateOptionPrice(String opsetName, String optionName, float newPrice){
        int setIdx = findOptionSet(opsetName);

        try{
            if(setIdx < 0){
                throw new AutoException("Option set "+opsetName+" not found.",this);
            }
        } catch (AutoException e){
            e.fix(2);
            return;
        }

        OptionSet set = this.getOpsetAt(setIdx);

        int opIdx = set.findOption(optionName);

        try {
            if (opIdx < 0) {
                throw new AutoException("Option " + optionName + " not found.", this);
            }
        } catch (AutoException e){
            e.fix(5);
            return;
        }

        set.updateOptionPrice(opIdx,newPrice);
    }

    /**
     * Print all properties of a model
     */
    public void print() {
        System.out.println(this.name);
        System.out.println(this.basePrice);
        System.out.println();

        for (OptionSet os : this.opsets) {
            os.print();
        }
    }
}
