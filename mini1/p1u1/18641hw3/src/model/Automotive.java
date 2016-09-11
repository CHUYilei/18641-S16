package model;

import java.io.Serializable;

/**
 * Model class for automotive
 */
public class Automotive implements Serializable {
    private static final long serialVersionUID = 2590337868401297846L;
    
    private String name;
    private OptionSet opsets[];
    private int basePrice;
    private int size;

    /**
     * Inner class for option sets
     * For example, COLOR option set
     */
    class OptionSet implements Serializable {
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

        /**
         * Getter
         * @return name
         */
        protected String getName() {
            return name;
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



    /* Constructors */

    /**
     * Empty constructor (default)
     */
    public Automotive() {
    }

    /**
     * Constructor
     * @param size number of option sets
     * @param name name of car model
     * @param basePrice base price of this modle
     */
    public Automotive(int size, String name, int basePrice) {
        this.opsets = new OptionSet[size];

        for (int i = 0; i < size; i++) {
            this.opsets[i] = new OptionSet();
        }

        this.name = name;

        this.basePrice = basePrice;
        this.size = size;
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
    public void setOpsetAt(int idx, OptionSet ost) {
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
