package driver;

import adapter.BuildAuto;
import adapter.CreateAuto;
import adapter.UpdateAuto;
import model.Automobile;
import util.Util;

/**
 * Project 1 Unit 2 Part B Driver class
 */
public class Driver {
    public static void main(String[] args) {
        BuildAuto builder = new BuildAuto();
        
        /* Build 3 Automobile Object from file,add to model set, and make choices */
        builder.buildAuto("FordZTW.txt");
        Automobile a1 = builder.getAutoObject("Focus Wagon ZTW");
        a1.setOptionChoice("Color", "French Blue Clearcoat Metallic");
        a1.setOptionChoice("Transmission","manual");
        a1.setOptionChoice("Brakes/Traction Control","ABS with Advance Trac");
        a1.setOptionChoice("Side Impact Air Bags","Selected");
        a1.setOptionChoice("Power Moonroof","Selected");
                        
        builder.buildAuto("AudiA6.txt");
        Automobile a2 = builder.getAutoObject("Audi A6");
        a2.setOptionChoice("Color", "Grabber Green Clearcoat Metallic");
        a2.setOptionChoice("Transmission","manual");
        a2.setOptionChoice("Brakes/Traction Control","ABS");
        a2.setOptionChoice("Side Impact Air Bags","None");
                
        builder.buildAuto("BMWX5.txt");
        Automobile a3 = builder.getAutoObject("BMW X5");
        a3.setOptionChoice("Color", "Pitch Black Clearcoat");
        a3.setOptionChoice("Brakes/Traction Control","ABS");
        a3.setOptionChoice("Power Moonroof","None");
       
        /* print all models' info,choices and total price */ 
        builder.printAll();
    }
}
