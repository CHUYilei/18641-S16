package adapter;

import model.Automobile;

/**
 * Created by ychu1 on 16/2/7.
 *
 * Interface for building and printing automobile objectss
 */
public interface CreateAuto {

    /**
     *  Given a text file name, build an instance of Automobile.
     *  This method does not have to return the Auto instance.
     *
     * @param filename text file name
     */
    public void buildAuto(String filename);

    /**
     * Search and print the properties of a given Auto model.
     *
     * @param modelname modelname
     */
    public void printAuto(String modelname);

    public Automobile getAutoObject();
}
