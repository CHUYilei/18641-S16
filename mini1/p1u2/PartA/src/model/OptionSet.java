package model;

import exception.AutoException;

import java.io.Serializable;

/**
 * class for option sets
 * For example, COLOR option set
 */
public class OptionSet implements Serializable {
    private static final long serialVersionUID = 6383353908676682824L;

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
         * @param name option name
         * @param price option price
         */
        protected Option(String name, float price) {
            this.name = name;
            this.price = price;
        }

        /**
         * Getter
         * @return option name
         */
        protected String getName() {
            return name;
        }

        /**
         * Setter
         * @param name option name
         */
        protected void setName(String name) {
            this.name = name;
        }

        /**
         * getter
         * @return option price
         */
        protected float getPrice() {
            return price;
        }

        /**
         * Setter
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

    /* instance variables */
    private Option opts[];
    private String name;

    private int size;

    /**
     * Empty constructor
     */
    protected OptionSet() {
    }

    /**
     * Constructor
     * @param size option set size
     * @param name set name
     */
    protected OptionSet(int size, String name) {
        this.opts = new Option[size];
        for (int i = 0; i < size; i++) {
            this.opts[i] = new Option();
        }

        this.name = name;
        this.size = size;
    }

    /**
     * Find option by name
     * @param name option name
     * @return index of that option in set, -1 for not exists
     */
    protected int findOption(String name) {
        for (int i = 0; i < this.size; i++) {
            if (this.opts[i].getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    protected void updateOptionPrice(int idx, float newPrice){
        this.opts[idx].setPrice(newPrice);
    }

    /**
     * Getter
     * @return size
     */
    protected int getSize() {
        return size;
    }

    /**
     * Getter
     * @return name
     */
    protected String getName() {
        return name;
    }

    /**
     * Setter
     * @param newName new name
     */
    protected void setName(String newName){
        this.name = newName;
    }

    /**
     * Set option at index
     * @param idx index
     * @param opt option
     */
    protected void setOptionAt(int idx, Option opt) {
        this.opts[idx] = opt;
    }

    /**
     *
     * @param idx
     */
    protected void deleteOptionAt(int idx) {
        Option temp = opts[idx - 1];
        opts[size - 1] = opts[idx];
        opts[idx] = temp;
        --size;
    }

    protected void addOption(int idx, String name, int price) {
        this.opts[idx] = new Option(name, price);
    }

    /**
     * Print properties of OptionSet class to console
     */
    protected void print() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":").append(size);
        System.out.println(sb.toString());

        for (Option opt : opts) {
            opt.print();
        }

        System.out.println();
    }



}
