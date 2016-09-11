package adapter;

/**
 * Created by ychu1 on 16/2/7.
 *
 * Interface for updating automobile's option set name and option's price
 */
public interface UpdateAuto {

    /**
     * Search the Model for a given OptionSet and sets the name of OptionSet to newName.
     *
     * @param modelname model name
     * @param OptionSetname the option set's name to search
     * @param newName the new name
     */
    public void updateOptionSetName(String modelname, String OptionSetname, String newName);

    /**
     * Search the Model for a given OptionSet and Option name, and sets the price to newPrice.
     * @param modelname model name
     * @param optionSetName option set name
     * @param optionName option name
     * @param newPrice new price
     */
    public void updateOptionPrice(String modelname, String optionSetName, String optionName, float newPrice);
}
