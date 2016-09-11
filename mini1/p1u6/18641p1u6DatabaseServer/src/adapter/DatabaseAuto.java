package adapter;

import model.Automobile;

public interface DatabaseAuto {
	public void addAutoInDb(Automobile auto);
	public void deleteAutoInDb(String autoName);
	public void updateOptionSetNameInDb(String autoName,String oldSetName,String newName);
	public void updateOptionPriceInDb(String autoName, String optionSet,String option,float newPrice);
}
