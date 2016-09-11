package driver;

import model.Automotive;
import util.Util;

/**
 * Project 1 Unit 1 Driver class
 * 3 test cases in total
 */
public class Driver {
    public static void main(String[] args) {
        /* Test case 1 */
        System.out.println("Test case 1: FordZTW model");

        //Build Automobile Object from a file.
        Automotive FordZTW = Util.buildAutoObject("FordZTW.txt");

        //Print attributes before serialization
        System.out.println("Attributes before serialization:");
        FordZTW.print();

        //Serialize the object
        Util.serializeAuto(FordZTW, "ford.ser");

        //Deserialize the object and read it into memory.
        Automotive newFordZTW = Util.deserializeAuto("ford.ser");

        //Print new attributes.
        System.out.println("Attributes after deserialization:");
        newFordZTW.print();

        /* Test case 2 */
        System.out.println("Test case 2: Audi A6 model");

        //Build Automobile Object from a file.
        Automotive audi = Util.buildAutoObject("AudiA6.txt");

        //Print attributes before serialization
        System.out.println("Attributes before serialization:");
        audi.print();

        //Serialize the object
        Util.serializeAuto(audi, "audi.ser");

        //Deserialize the object and read it into memory.
        Automotive newaudi = Util.deserializeAuto("audi.ser");

        //Print new attributes.
        System.out.println("Attributes after deserialization:");
        newaudi.print();

        /* Test case 3 */
        System.out.println("Test case 3: BMW X5 model");

        //Build Automobile Object from a file.
        Automotive bmw = Util.buildAutoObject("BMWX5.txt");

        //Print attributes before serialization
        System.out.println("Attributes before serialization:");
        bmw.print();

        //Serialize the object
        Util.serializeAuto(bmw, "bmw.ser");

        //Deserialize the object and read it into memory.
        Automotive newbmw = Util.deserializeAuto("bmw.ser");

        //Print new attributes.
        System.out.println("Attributes after deserialization:");
        newbmw.print();

    }
}
