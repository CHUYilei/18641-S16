package server;

import model.Automobile;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Yilei CHU on 16/2/20.
 */
public interface AutoServer {
    /**
     * Given a file, .txt or .properties,
     * use CreateAuto to build automobile instance by differentiating between
     * file types
     * @param filename given file path
     */
    public void buildAutoFromFile(String filename);

    /**
     * Get model name list in server's hashmap
     * @return
     */
    public List<String> getAvailableModelList();

    /**
     * Send the model with chosen name to client
     * @param name
     * @param outStream
     */
    public void sendChosenModel(String name,ObjectOutputStream outStream);
}
