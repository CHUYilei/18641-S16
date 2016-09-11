package driver;

import adapter.BuildAuto;
import adapter.CreateAuto;
import adapter.UpdateAuto;
import model.Automobile;
import util.Util;

/**
 * Project 1 Unit 2 Driver class
 */
public class Driver {
    public static void main(String[] args) {
        /* Test case 1 */
        System.out.println("Test case 1: create and print an Auto instance through CreateAuto interface");

        // test create
        System.out.println("\n****** Able to create an Auto instance through CreateAuto interface ****** ");
        CreateAuto testCreateAuto = new BuildAuto();

        // Test 3 exceptions: FilenameError,BasePriceMissing,ModelNameMissing
        System.out.println("\n****** Test FilenameError Exception: please enter FordZTW.txt after exception prompt ****");
        System.out.println("\n****** Test BasePriceMissing Exception: please enter 1850 after exception prompt ****");
        System.out.println("\n****** Test ModelNameMissing Exception: please enter Ford after exception prompt ****\n");

        testCreateAuto.buildAuto("FordZT.txt");

        Automobile FordZTW = testCreateAuto.getAutoObject();

        //Print attributes before serialization
        System.out.println("\n****** Able to print an Auto instance through CreateAuto interface ******");
        System.out.println("\nAttributes of the newly created object:");
        testCreateAuto.printAuto(FordZTW.getName());

        /* Test case 2 */
        System.out.println("Test case 2: update the Auto instance through UpdateAuto interface");

        UpdateAuto testUpdateAuto = new BuildAuto();

        // Update Automobile Object
        String oldOpsetName = "Color";
        String newOpsetName = "Colors";

        try {
            testUpdateAuto.updateOptionSetName(FordZTW.getName(), oldOpsetName, newOpsetName);
            testUpdateAuto.updateOptionPrice(FordZTW.getName(), newOpsetName, "Infra-Red Clearcoat", 12.0f);
            testCreateAuto.printAuto(FordZTW.getName());
        } catch (NullPointerException e){
            System.out.println("\n****** NullPointerException: Not able to update an Auto instance through UpdateAuto interface ******");
        }

        // Test optionset not found exception
        System.out.println("\n****** Test OptionSetNotFound Exception ****");
        FordZTW.updateOptionSetName("Colors","Hahaha");

        // Test option not found exception
        System.out.println("\n****** Test OptionNotFound Exception ****");
        FordZTW.updateOptionPrice("Color","any color",15.0f);

        System.out.println("\nCurrent auto's attributes");
        testCreateAuto.printAuto(FordZTW.getName());
    }
}
