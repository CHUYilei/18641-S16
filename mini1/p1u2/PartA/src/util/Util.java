package util;

import exception.AutoException;
import model.Automobile;

import java.io.*;

/**
 * Utility class for File I/O
 */
public class Util {
    /**
     * Build an automotive instance from properties file
     *
     * @param filename input filename
     * @return initialized automotive object
     */
    public static Automobile buildAutoObject(String filename) {
        Automobile auto = new Automobile();
        buildAutoObject(filename,auto);
        return auto;
    }

    /**
     * Build an automotive instance from properties file
     * @param filename input filename
     * @param model automotive object to be filled
     */
    public static void buildAutoObject(String filename,Automobile model) {
        try {
            FileReader file = new FileReader(filename);
            BufferedReader buff = new BufferedReader(file);
            String line;
            int idx;

            //first line: model name
            line = buff.readLine();
            idx = line.indexOf('=');

            try{
                if(idx >= 0 && line.substring(0,idx).equals("Name")) {
                    String modelName = line.substring(idx + 1);
                    model.setName(modelName);
                    line = buff.readLine();
                }else{
                    throw new AutoException("Model name not found",model);
                }
            }catch (AutoException e){
                e.fix(3);
            }

            //second line: base price
            idx = line.indexOf('=');

            try{
                if(idx >= 0 && line.substring(0,idx).equals("BasePrice")) {
                    int basePrice = Integer.parseInt(line.substring(idx + 1));
                    model.setBasePrice(basePrice);
                    line = buff.readLine();
                }else{
                    throw new AutoException("Model base price not found",model);
                }
            }catch (AutoException e){
                e.fix(1);
            }

            //third line: number of option sets
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
            buff.close();
        } catch (FileNotFoundException e){
            AutoException ae = new AutoException("File "+filename+" Not found",model);
            ae.fix(4);
        } catch (IOException e) {
            System.out.println("I/O error " + e.toString());
        }
    }

    /**
     * Serialize the Automobile object to file,
     * path specified by serializeOutputPath
     *
     * @param model the automotive object
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

}
