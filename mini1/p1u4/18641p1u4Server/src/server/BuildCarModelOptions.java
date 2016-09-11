package server;

import adapter.BuildAuto;

import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by Yilei CHU on 16/2/20.
 */
public class BuildCarModelOptions implements AutoServer{
    private BuildAuto builder;

    /**
     * Constructor
     */
    public BuildCarModelOptions(){
        this.builder = new BuildAuto();
    }

    /**
     * Given a file, use CreateAuto interface to build it
     * @param filename given file name
     */
    @Override
    public void buildAutoFromFile(String filename) {
        this.builder.buildAuto(filename);
    }

    /**
     * Get a list of available automobile names stored in server
     * @return
     */
    @Override
    public List<String> getAvailableModelList() {
        return this.builder.getAvailableModelList();
    }

    /**
     * Send the chosen model back to client through stream
     * @param name chosen model name
     * @param outStream object output stream
     */
    public void sendChosenModel(String name,ObjectOutputStream outStream){
        this.builder.sendChosenModel(name,outStream);
    }

}
