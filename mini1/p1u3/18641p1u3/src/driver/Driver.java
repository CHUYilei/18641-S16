package driver;

import adapter.BuildAuto;
import model.Automobile;

/**
 * Project 1 Unit 3 Driver class
 */
public class Driver {
    public static void main(String[] args) {
        BuildAuto builder = new BuildAuto();
        
        /* Build Automobile Object from file,add to model set, and make choices */
        builder.buildAuto("FordZTW.txt");
        Automobile a1 = builder.getModel("Focus Wagon ZTW");
        a1.setOptionChoice("Color", "French Blue Clearcoat Metallic");
        a1.setOptionChoice("Transmission", "manual");
        a1.setOptionChoice("Brakes/Traction Control", "ABS with Advance Trac");
        a1.setOptionChoice("Side Impact Air Bags", "Selected");
        a1.setOptionChoice("Power Moonroof", "Selected");

        /* Test concurrency using 2 threads*/
        System.out.println("************** Test Concurrency (with synchronized)*****************");
        System.out.println("Test using multiple threads (thread creation inside ProxyAutomobile)\n");

        System.out.println("************** Test 1:edit option price (multi-threading) *****************");
        builder.updateOptionPrice("Focus Wagon ZTW", "Color", "French Blue Clearcoat Metallic", 4);
        builder.updateOptionPrice("Focus Wagon ZTW", "Color", "French Blue Clearcoat Metallic", 9);
        builder.updateOptionPrice("Focus Wagon ZTW", "Color", "CD Silver Clearcoat Metallic", -2);

        System.out.println("************** Test 2:edit option set choice (multi-threading) *****************");
        builder.updateOptionSetChoice("Focus Wagon ZTW", "Transmission", "automatic");
        builder.updateOptionSetChoice("Focus Wagon ZTW", "Transmission", "manual");

        System.out.println("************** Test 3:edit option set name (multi-threading) *****************");
        builder.updateOptionSetName("Focus Wagon ZTW", "Side Impact Air Bags", "Air Bags");
        builder.updateOptionSetName("Focus Wagon ZTW", "Air Bags", "Side Impact Air Bags");

        System.out.println();
        builder.printAll();
    }
}
