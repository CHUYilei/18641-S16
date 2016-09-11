package model;

import java.io.Serializable;
import java.util.ArrayList;

import exception.AutoException;
import model.OptionSet.Option;

/**
 * Model class for automotive
 * 
 * OptionSet stored in list
 */
public class Automobile implements Serializable {
    private static final long serialVersionUID = 2590337868401297846L;
    
    private String name;
    private String model;
    
    private ArrayList<OptionSet> opsets;
    private int basePrice;
    private int size;
    
    private String make;
    
    /* for keep tracking user's choices */
    private ArrayList<Option> choice;

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
        this.opsets = new ArrayList<OptionSet>();
        this.choice = new ArrayList<Option>();

        for (int i = 0; i < size; i++) {
            this.opsets.add(new OptionSet());
            this.choice.add(null);
        }
    }

    /* Getters and setters*/
    /**
     * Getter
     * @return make of mobile
     */
    public String getMake() {
        return make;
    }

    /**
     * Setter
     * @param make make of mobile
     */
    public void setMake(String make) {
        this.make = make;
    }
    
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
        return opsets.get(idx);
    }

    /**
     * Find option set in array with that name
     * @param name option set name
     * @return index of that option set, -1 for not exists
     */
    public int findOptionSet(String name) {
        for (int i = 0; i < this.size; i++) {
            if (this.opsets.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }
    
    public String getModel() {
        return model;
    }


    /* Setters */

    /**
     * Setter
     * @param name name of the model
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void setModel(String model) {
        this.model = model;
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
        this.opsets.set(idx, ost);
    }

    /**
     * Delete option set at index
     * @param idx index in option set array 
     */
    public void deleteOptionSetAt(int idx) {
        this.opsets.remove(idx);
        --size;
    }

    /**
     * Update the option set with name
     * @param name option name
     * @param newOpset option set to be updated with
     */
    public void updateOptionSet(String name, OptionSet newOpset) {
        this.opsets.set(findOptionSet(name), newOpset);
    }

    /**
     * Add option at certain option set
     * @param setidx index of option set
     * @param optionidx index of option
     * @param name name of option
     * @param price price of option
     */
    public void addOptionAt(int setidx, int optionidx, String name, int price) {
        this.opsets.get(setidx).addOption(optionidx, name, price);
    }

    /**
     * Add option set in array at index
     * @param idx index of option set
     * @param name name of option set
     * @param size size of option set
     */
    public void addOptionSetAt(int idx, String name, int size) {
        this.opsets.set(idx, new OptionSet(size, name));
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
     * include the choices
     */
    public void print() {
        System.out.println("Name: "+this.name);
        System.out.println("Base price: "+this.basePrice);
        System.out.println("Model: "+this.model);
        System.out.println("Make: "+this.make);
        System.out.println();

        for (OptionSet os : this.opsets) {
            System.out.println("option set: "+os.getName()+" choice: "+os.getOptionChoice());
        }
        
        System.out.println("******** Total price: "+getTotalPrice()+" ********* \n");
    }
    
    /* For tracking curstomer's choices */
    /**
     * Getter
     * @param setName option set name
     * @return choice
     */
    public String getOptionChoice(String setName){
        OptionSet set = this.opsets.get(this.findOptionSet(setName));
        
        return set.getOptionChoice().getName();
    }
    
    /**
     * Getter
     * @param setName option set name
     * @return choice's price
     */
    public int getOptionChoicePrice(String setName){
        OptionSet set = this.opsets.get(this.findOptionSet(setName));
        
        return (int)(set.getOptionChoice().getPrice());
    }
    
    /**
     * Setter
     * @param setName option set name
     * @param optionName make choice for that option set
     */
    public void setOptionChoice(String setName,String optionName){
        int idx = this.findOptionSet(setName);
        
        if(idx >= 0){
            OptionSet set = this.opsets.get(idx);
            set.setOptionChoice(optionName);
            choice.set(idx, set.getOptionChoice());
        }
    }
    
    /**
     * Getter
     * @return total price of the model based on all chocies
     */
    public int getTotalPrice(){
        int total = 0;
        
        for(OptionSet set:this.opsets){
            Option op = set.getOptionChoice();
            
            total += (int)op.getPrice();
        }
        return total+this.basePrice;
    }
    
}
