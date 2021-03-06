package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class for option sets
 * For example, COLOR option set
 *
 * options stored in arraylist
 */
public class OptionSet implements Serializable {
    private static final long serialVersionUID = 6383353908676682824L;
    /* instance variables */
    private List<Option> opts;
    private String name;
    private Option currentChoice;

    /**
     * Empty constructor
     */
    protected OptionSet() {
    }

    /**
     * Constructor
     *
     * @param size option set size
     * @param name set name
     */
    protected OptionSet(int size, String name) {
        this.opts = new ArrayList<Option>();
        for (int i = 0; i < size; i++) {
            this.opts.add(new Option());
        }

        this.name = name;
    }

    /**
     * Find option by name
     *
     * @param name option name
     * @return index of that option in set, -1 for not exists
     */
    protected int findOption(String name) {
        for (int i = 0; i < this.opts.size(); i++) {
            if (this.opts.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    protected void updateOptionPrice(int idx, float newPrice) {
        this.opts.get(idx).setPrice(newPrice);
    }

    /**
     * Getter
     *
     * @return size
     */
    protected int getSize() {
        return this.opts.size();
    }

    /**
     * Getter
     *
     * @return name
     */
    protected String getName() {
        return name;
    }

    /**
     * Setter
     *
     * @param newName new name
     */
    protected void setName(String newName) {
        this.name = newName;
    }

    /**
     * Set option at index
     *
     * @param idx index
     * @param opt option
     */
    protected void setOptionAt(int idx, Option opt) {
        this.opts.set(idx, opt);
    }

    /**
     * @param idx
     */
    protected void deleteOptionAt(int idx) {
        this.opts.remove(idx);
    }

    protected void addOption(int idx, String name, int price) {
        this.opts.set(idx, new Option(name, price));
    }

    protected void addOption(String name, int price) {
        this.opts.add( new Option(name, price));
    }

    /**
     * Print properties of OptionSet class to console
     */
    protected void print() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":");
        System.out.println(sb.toString());

        for (Option opt : opts) {
            opt.print();
        }

        System.out.println();
    }

    /**
     * Getter
     * @return current option choice
     */
    protected Option getOptionChoice() {
        return currentChoice;
    }

    /**
     * Setter
     * @param optionName option to choose
     */
    protected void setOptionChoice(String optionName) {
        int idx = findOption(optionName);

        if (idx >= 0) {
            this.currentChoice = this.opts.get(idx);
        }
    }

    protected void setOptionChoice(int idx) {
        if (idx >= 0 && idx < this.opts.size()) {
            this.currentChoice = this.opts.get(idx);
        }else{
            System.out.println("Wrong index for making choice!");
        }
    }


    protected void printOptions(){
        for(int i=0;i<this.opts.size();++i){
            System.out.println("["+i+"] "+this.opts.get(i).name+" "+this.opts.get(i).price);
        }
        System.out.println();
    }

    /**
     * Inner class for option
     * Each belongs to one option set
     */
    class Option implements Serializable {
        private static final long serialVersionUID = -2595546114165646345L;

        private String name;
        private float price;

        /**
         * Empty constructor
         */
        protected Option() {
        }

        /**
         * Constructor
         *
         * @param name  option name
         * @param price option price
         */
        protected Option(String name, float price) {
            this.name = name;
            this.price = price;
        }

        /**
         * Getter
         *
         * @return option name
         */
        protected String getName() {
            return name;
        }

        /**
         * Setter
         *
         * @param name option name
         */
        protected void setName(String name) {
            this.name = name;
        }

        /**
         * getter
         *
         * @return option price
         */
        protected float getPrice() {
            return price;
        }

        /**
         * Setter
         *
         * @param price option price
         */
        protected void setPrice(float price) {
            this.price = price;
        }

        /**
         * Print properties of Option object to console
         */
        protected void print() {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append("=").append(price);
            System.out.println(sb.toString());
        }
    }
}
