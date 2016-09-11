package scale;

import model.Automobile;

/**
 * Runnable class for editing automobile's option using thread
 * Author: ychu1
 * Date: Feb 12,2016
 */
public class EditOptions implements Runnable {

    private static final int editOptionPriceCode = 1;
    private static final int editOptionSetChoiceCode = 2;
    private static final int editOptionSetNameCode = 3;

    private Automobile auto;
    private String optionSetName;
    private String optionName;
    private String choice;
    private String newName;
    private float price;
    private int opcode;

    /**
     * Constructor
     *
     * @param opcode opcode indicates operation to perform
     */
    public EditOptions(int opcode) {
        super();
        this.opcode = opcode;
    }

    /**
     * Setter
     * @param auto Automobile object to modify
     */
    public void setAuto(Automobile auto) {
        this.auto = auto;
    }


    /**
     * Setter
     * @param optionSetName option set name
     */
    public void setOptionSetName(String optionSetName) {
        this.optionSetName = optionSetName;
    }

    /**
     * Setter
     * @param optionName option
     */
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    /**
     * Setter
     * @param choice choice in an option set
     */
    public void setChoice(String choice) {
        this.choice = choice;
    }

    /**
     * Setter
     * @param newName new name for option set
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }

    /**
     * Setter
     * @param price new price for an option
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * do operation according to opcode
     */
    @Override
    public void run() {
        switch (opcode) {
            case editOptionPriceCode:
                auto.updateOptionPrice(optionSetName, optionName, price);
                break;
            case editOptionSetChoiceCode:
                auto.setOptionChoice(optionSetName, choice);
                break;
            case editOptionSetNameCode:
                auto.updateOptionSetName(optionSetName, newName);
                break;
        }
    }

}
