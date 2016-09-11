package util;

import model.Automotive;

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
    public static Automotive buildAutoObject(String filename) {
        try {
            FileReader file = new FileReader(filename);
            BufferedReader buff = new BufferedReader(file);
            String line;
            int idx;

            //first line: model name
            line = buff.readLine();
            idx = line.indexOf('=');
            String modelName = line.substring(idx + 1);

            //second line: base price
            line = buff.readLine();
            idx = line.indexOf('=');
            int basePrice = Integer.parseInt(line.substring(idx + 1));

            //third line: number of option sets
            line = buff.readLine();
            idx = line.indexOf(':');
            int opsetNum = Integer.parseInt(line.substring(idx + 1));

            Automotive model = new Automotive(opsetNum, modelName, basePrice);

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

            return model;
        } catch (IOException e) {
            System.out.println("I/O error " + e.toString());
        }

        return null;
    }

    /**
     * Serialize the Automotive object to file,
     * path specified by serializeOutputPath
     *
     * @param model the automotive object
     * @param serializeOutputPath output file path
     */
    public static void serializeAuto(Automotive model, String serializeOutputPath) {
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
     * Deserialize the Automotive object to file,
     * path specified by serializeOutputPath
     * 
     * @param serializeOutputPath input file storing the object
     */
    public static Automotive deserializeAuto(String serializeOutputPath) {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream(serializeOutputPath);
            ois = new ObjectInputStream(fin);
            Automotive mobile = (Automotive) ois.readObject();
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
