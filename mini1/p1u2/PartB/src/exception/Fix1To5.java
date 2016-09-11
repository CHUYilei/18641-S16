package exception;

import model.Automobile;
import util.Util;

import java.util.Scanner;

/**
 * Created by ychu1 on 16/2/7.
 */
public class Fix1To5{
    /**
     * When base price is missing in config file, ask for a new one
     * default value is 1850
     * @param auto automobile
     */
    public void fixBasePriceMissing(Automobile auto){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the base price:");

        try {
            int price = Integer.parseInt(scanner.nextLine());
            auto.setBasePrice(price);
        } catch (NumberFormatException e){
            System.out.println("No valid base price is entered, default 1850 is chosen.");
            auto.setBasePrice(1850);
        }
    }

    /**
     * When model name is missing in config file, ask for a new one
     * default value is Ford
     * @param auto automobile
     */
    public void fixAutoMobileNameMissing(Automobile auto){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the model name:");

        String name = scanner.nextLine();

        if(name.isEmpty()){
            System.out.println("Not model name is entered, default Ford is chosen.");
            auto.setName("Ford");
        }else{
            auto.setName(name);
        }
    }

    /**
     * When option set is not found, no change is made
     * @param auto automobile
     */
    public void fixOptionSetNotFound(Automobile auto){
        System.out.println("Please enter a new option set name. Currently no change is done.");
    }

    /**
     * When filename is incorrect,ask for a new filename and rebuild the automobile
     * @param auto automobile
     */
    public void fixFilenameError(Automobile auto){
        System.out.println("File not found. Please enter a new file name:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        if(path.isEmpty()){
            System.out.println("Not filename is entered, default FordZTW.txt is chosen.");
            path = "FordZTW.txt";
        }

        Util.buildAutoObject(path,auto);
    }

    /**
     * When option is not found, no change is made
     * @param auto automobile
     */
    public void fixOptionNotFound(Automobile auto){
        System.out.println("Please re-choose a option name. Currently no change is done.");
    }

}
