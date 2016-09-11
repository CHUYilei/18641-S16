package util;

import exception.AutoException;
import model.Automobile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Utility class for File I/O
 */
public class FileIO {

    /**
     * Load a .properties file into a properties object
     * @param filename .properties file path
     * @return properties object
     */
    public static Properties buildPropertiesObjectFromFile(String filename){
        Properties prop = new Properties();
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(filename);

            prop.load(fin);

            return prop;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fin != null){
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    /**
     * Build an automobile instance from properties object
     *
     * @param prop properties object
     * @return automobile instance
     */
    public static Automobile buildAutoObjectFromPropertiesObject(Properties prop){
        Automobile auto = new Automobile();
        buildAutoObjectFromPropertiesObject(prop,auto);
        return auto;
    }

    /**
     * Build an automobile instance from properties object
     *
     * @param prop properties object
     * @param auto the model to be built
     */
    public static void buildAutoObjectFromPropertiesObject(Properties prop, Automobile auto){

        String name = prop.getProperty("Name");
        String make = prop.getProperty("Make");
        String model = prop.getProperty("Model");
        int baseprice = Integer.parseInt(prop.getProperty("BasePrice"));

        auto.setName(name);
        auto.setMake(make);
        auto.setModel(model);
        auto.setBasePrice(baseprice);

        /* start setting options */
        String option = "Option";
        String optionValue = "OptionValue";
        String optionPrice = "OptionPrice";

        int opSetNum = 0;
        int valueNum = 97; //ascii for 'a'
        String opsetName = prop.getProperty(option+opSetNum);

        while(opsetName != null){
            // add an option set by its name
            auto.addOptionSet(opsetName,0);

            // add options
            int opIdx = 0;
            String opName = prop.getProperty(optionValue+opSetNum+(char)(valueNum+opIdx));

            while(opName != null){
                int price = Integer.parseInt(prop.getProperty(optionPrice+opSetNum+(char)(valueNum+opIdx)));
                auto.addOption(opSetNum,opName,price);

                opIdx++;
                opName = prop.getProperty(optionValue+opSetNum+(char)(valueNum+opIdx));
            }

            opSetNum++;
            opsetName = prop.getProperty(option+opSetNum);
        }
    }


    /**
     * Build an automotive instance from text file
     *
     * @param filename input filename
     * @return initialized automotive object
     */
    public static Automobile buildAutoObjectFromTxt(String filename) {
        Automobile auto = new Automobile();
        buildAutoObjectFromTxt(filename, auto);
        return auto;
    }

    /**
     * Build an automotive instance from text file
     *
     * @param filename input filename
     * @param model    automotive object to be filled
     * @throws IOException 
     */
    public static void buildAutoObjectFromTxt(String filename, Automobile model) {
        FileReader file = null;
        BufferedReader buff = null;
        
        try {
            file = new FileReader(filename);
            buff = new BufferedReader(file);
            String line;
            int idx;

            //first line: model name
            line = buff.readLine();
            idx = line.indexOf('=');

            try {
                if (idx >= 0 && line.substring(0, idx).equals("Name")) {
                    String modelName = line.substring(idx + 1);
                    model.setName(modelName);
                    line = buff.readLine();
                } else {
                    throw new AutoException("Model name not found", model);
                }
            } catch (AutoException e) {
                e.fix(3);
            }

            // second line: model
            idx = line.indexOf('=');
            model.setModel(line.substring(idx + 1));

            // third line: make
            line = buff.readLine();
            idx = line.indexOf('=');
            model.setMake(line.substring(idx + 1));

            //fourth line: base price
            line = buff.readLine();
            idx = line.indexOf('=');

            try {
                if (idx >= 0 && line.substring(0, idx).equals("BasePrice")) {
                    int basePrice = Integer.parseInt(line.substring(idx + 1));
                    model.setBasePrice(basePrice);
                    line = buff.readLine();
                } else {
                    throw new AutoException("Model base price not found", model);
                }
            } catch (AutoException e) {
                e.fix(1);
            }

            //number of option sets
            idx = line.indexOf(':');
            int opsetNum = Integer.parseInt(line.substring(idx + 1));

            model.initializeOpsets(opsetNum);

            for (int i = 0; i < opsetNum; i++) {
                line = buff.readLine();
                // option set name and size
                idx = line.indexOf(':');
                String setname = line.substring(0, idx);
                int size = Integer.parseInt(line.substring(idx + 1));

                model.addOptionSetAt(i, setname, size);

                for (int j = 0; j < size; j++) {
                    line = buff.readLine();
                    idx = line.indexOf('=');
                    String name = line.substring(0, idx);
                    int price = Integer.parseInt(line.substring(idx + 1));

                    model.addOptionAt(i, j, name, price);
                }
            }
        } catch (FileNotFoundException e) {
            AutoException ae = new AutoException("File " + filename + " Not found", model);
            ae.fix(4);
        } catch (IOException e) {
            System.out.println("I/O error " + e.toString());
        }finally{
            if(buff != null){
                try {
                    buff.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Serialize the Automobile object to file,
     * path specified by serializeOutputPath
     *
     * @param model               the automotive object
     * @param serializeOutputPath output file path
     */
    public static void serializeAuto(Automobile model, String serializeOutputPath) {
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(serializeOutputPath);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(model);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Deserialize the Automobile object to file,
     * path specified by serializeOutputPath
     *
     * @param serializeOutputPath input file storing the object
     */
    public static Automobile deserializeAuto(String serializeOutputPath) {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream(serializeOutputPath);
            ois = new ObjectInputStream(fin);
            Automobile mobile = (Automobile) ois.readObject();
            return mobile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Read an index file which contains automobile file names
     * @param filename index file name
     * @return filename list
     */
    public List<String> getAvailableAutoFileList(String filename){
        BufferedReader br = null;
        List<String> fileList = new ArrayList<String>();
        String line = null;

        try {
            br = new BufferedReader(new FileReader(new File(filename)));
            while ((line = br.readLine()) != null) {
                fileList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileList;
    }

}
