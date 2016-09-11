package adapter;

import java.util.Iterator;
import java.util.LinkedHashMap;

import model.Automobile;

/**
 * Use LinkedHashMap to hold a group of automobiles
 * @author ychu1
 *
 */
public class ModelSet {
    private LinkedHashMap<String, Automobile> modelSet;    

    /**
     * Constructor
     */
    public ModelSet(){
        this.modelSet = new LinkedHashMap<String, Automobile>();
    }
    
    /*
     * add an Automobile model with its name into map
     */
    public void addModel(Automobile auto)
    {
        this.modelSet.put(auto.getName(), auto);
    }
    
    /*
     * find the Automobile from the given name
     */
    public Automobile getModel(String name)
    {
        if (modelSet.containsKey(name))
        {
            return modelSet.get(name);
        }
            
        System.out.println("Name "+name+" not exists in model set");
        return null;
    }
    
    public Iterator<Automobile> getIterator(){
        return this.modelSet.values().iterator();
    }
}
