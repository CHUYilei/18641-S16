package client;

import model.Automobile;
import model.OptionSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Yilei CHU on 16/2/20.
 *
 * prompt user with available models
 * allow user to select a model and enter its options
 * Display the selected options
 */
public class SelectCarOption {
    private BufferedReader inReader = null;

    /**
     * Constructor
     */
    public SelectCarOption(){
        inReader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Display model lists to user and ask for a model to configure
     */
    public String selectModel(List<String> modelList){
        System.out.println(" Here is a list of available models: ");
        String userInput = null;

        for (int i = 0; i < modelList.size(); i++) {
            System.out.println("["+i+"] "+modelList.get(i));
        }

        // prompt for user's choice
        System.out.println("Please choose a model to configure, enter 0 to "+(modelList.size()-1));

        boolean valid = false;
        int choiceIdx = -1;

        while(!valid){
            try {
                userInput = inReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            choiceIdx = Integer.parseInt(userInput);

            if(choiceIdx >= 0 && choiceIdx < modelList.size()){
                valid = true;
            }else{
                System.out.println("Please enter a valid number between 0 and "+(modelList.size()-1));
            }
        }

        return modelList.get(choiceIdx);
    }

    /**
     * Interact with user to let user choose option for every optionset
     * in this automobile
     * @param auto the configured automobile
     */
    public void configureModel(Automobile auto){
        String userInput = null;

        for(int i= 0 ;i<auto.getOpsetsSize();++i) {
            // print option set content to user
            String opsetName = auto.getOpsetNameAt(i);
            System.out.println("Here are options for " + opsetName);
            auto.printOptionsForOpsetAt(i);

            int optionNum = auto.getOptionNumForOpsetAt(i);

            boolean valid = false;
            int choiceIdx = -1;

            while (!valid) {
                // prompt for user input
                try {
                    userInput = inReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                choiceIdx = Integer.parseInt(userInput);

                if (choiceIdx >= 0 && choiceIdx < optionNum) {
                    valid = true;
                } else {
                    System.out.println("Please enter a valid number between 0 and " + optionNum + "(exclude).");
                }
            }

            auto.setOptionChoice(i,choiceIdx);

            // display the choice to user
            System.out.println("You have selected "+auto.getOptionChoice(opsetName)+" for "+opsetName);
        }
    }


}
