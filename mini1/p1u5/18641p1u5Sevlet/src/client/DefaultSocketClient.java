package client;

import model.Automobile;
import util.FileIO;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * Created by Yilei CHU on 16/2/20.
 *
 * The client class
 */
public class DefaultSocketClient extends Thread implements SocketClientConstant,SocketClientInterface{
    private Socket clientSock;
    private String strHost;
    private int iPort;

    // for communication
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;

    // operation code
    private static final String QUIT_OPCODE = "0";
    private static final String UPLOAD_OPCODE = "1";
    private static final String CONFIG_OPCODE = "2";
    private static final String GETLIST_OPCODE = "3";
    private static final String GETAUTO_OPCODE = "4";

    // for interaction with user
    private SelectCarOption selectCarOption;
    private BufferedReader inReader;

    // helper class
    private CarModelOptionsIO carOpIO;
    
    // for servlet use
    private boolean servletMode = false;
    private String servletOpcode = "";
    
    private List<String> modelNameList = null;
    private String selectedName = null;
    private Automobile selectedAuto = null;

    public DefaultSocketClient(String strHost) {
        this.strHost = strHost;
        this.iPort = iDAYTIME_PORT;
        this.selectCarOption = new SelectCarOption();
        this.inReader = new BufferedReader(new InputStreamReader(System.in));
        this.carOpIO = new CarModelOptionsIO();
    }
    
    /**
     * Start
     */
    public void run() {
        if (openConnection()) {
        	if(!servletMode){   // command line mode     	
	            handleSession();
	            closeSession();
        	} else { 			// servlet mode
        		handleServletSession();
        		//closeSession();
        	}
        }
    }

    /**
     * Open the connection, initialize streams
     * @return
     */
    @Override
    public boolean openConnection() {
        try {
            clientSock = new Socket(strHost, iPort);
        } catch (IOException socketError) {
            if (DEBUG) System.err.println("Unable to connect to " + strHost);
            return false;
        }

        try {
            inStream = new ObjectInputStream(clientSock.getInputStream());
            outStream = new ObjectOutputStream(clientSock.getOutputStream());
        } catch (Exception e) {
            if (DEBUG) System.err.println("Unable to obtain stream to/from " + strHost);
            return false;
        }

        return true;
    }

    /**
     * Major handler using opcode
     */
    @Override
    public void handleSession() {
        String serverResponse = null;
        String userInput = null;

        while(true) {
            // prompt menu for an opcode
            printMenu();

            boolean valid = false;
            int opcode = -1;

            try {
                userInput = inReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!valid) {
                opcode = Integer.parseInt(userInput);

                if (opcode >= 0 && opcode <= 2) {
                    valid = true;
                } else {
                    System.out.println("Please enter a valid opcode: 0,1,2");
                }
            }


            switch (opcode) {
                case 0:
                    handleStopServer();
                    return;
                case 1:
                    handleUpload();
                    break;
                case 2:
                    handleConfigure();
                    break;                	
            }
        }
    }

    /**
     * Handle quit
     */
    public void handleStopServer(){
        // send quit opcode to server
        sendOutput(QUIT_OPCODE);

        // receive server's ack message
        System.out.println((String)receiveResponse());
    }

    /**
     * Handle upload properties object or text file to server
     */
    public void handleUpload(){
        // send upload opcode to server
        sendOutput(UPLOAD_OPCODE);

        // receive server's ready message
        System.out.println((String)receiveResponse());

        // ask for user to input a file name
        String path = askForValidFilename();

        System.out.println("User input path "+path);

        // check whether .properties or .txt
        String choice = "INVALID";

        if(path.endsWith(".properties")){
            choice = "P";
        }else {
            choice = "T";
        }

        System.out.println("Choice is "+choice);

        // upload uploading choice: T for text file, P for properties object
        sendOutput(choice);

        // receive server's ACK
        System.out.println((String)receiveResponse());

        // upload corresponding things according to the choice
        if(choice.equals("P")){
            System.out.println("Prepare properties object");

            this.carOpIO.sendPropObjectToServer(path,inStream,outStream);
        }else{
            System.out.println("Start sending out text file");

            this.carOpIO.sendTextFileToServer(path,clientSock,inStream);
        }
    }

    /**
     * Handle configure interaction
     * (command line)
     * 
     */
    public void handleConfigure(){
        // send config opcode to server
        sendOutput(CONFIG_OPCODE);

        // receive server's ready message
        System.out.println((String)receiveResponse());

        // receive available name list
        List<String> autoList = (List<String>)receiveResponse();

        // if list empty, end this operation return to main menu
        if(autoList.isEmpty()){
            System.out.println("No available automobile. Please upload some first.");
            return;
        }

        // list not empty, display list to user and let user choose one to configure
        String selectedModel = this.selectCarOption.selectModel(autoList);

        // send chosen name to server
        sendOutput(selectedModel);

        // receive the automobile object from server
        Automobile auto = (Automobile)receiveResponse();

        // receive server's ACK
        System.out.println((String)receiveResponse());

        // start configuring by user
        this.selectCarOption.configureModel(auto);

        // print all the choices configured in that automobile
        System.out.println(" Here is the configured automobile.");
        auto.print();
    }
    
    /* ============= helper function for servlet ========= */
    public void handleServletSession(){
    	if(this.servletOpcode.equals(this.GETLIST_OPCODE)){
    		askForModelNameList();
    	}else if(this.servletOpcode.equals(this.GETAUTO_OPCODE)){
    		askForSelectedAuto();
    	}else {
    		System.err.println("Wrong opcode in client: "+this.servletOpcode);
    	}
    }
    
    /**
     * for servlet use
     * send opcode 3 for merely getting name list
     * @return list of model names
     */
    public void askForModelNameList(){
    	// send opcode to server
        sendOutput(GETLIST_OPCODE);

        // receive server's ready message
        System.out.println((String)receiveResponse());

        // receive available name list
        this.modelNameList = (List<String>)receiveResponse();
    }
  
    /**
     * for servlet use
     * send opcode 4 for getting automobile object by name from server
     * @return selected auto object
     */
    public void askForSelectedAuto(){
    	// send opcode to server
        sendOutput(GETAUTO_OPCODE);
    	
        while(this.selectedName == null){
        	try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
    	// send chosen name to server
        sendOutput(this.selectedName);

        // receive the automobile object from server
        this.selectedAuto = (Automobile)receiveResponse();

        // receive server's ACK
        System.out.println((String)receiveResponse());
    }
    
    public void setServletMode(boolean mode){
    	this.servletMode = mode;
    }
    
    public void setServletOpcode(String opcode){
    	this.servletOpcode = opcode;  	
    }
    
    public Automobile getSelectedAuto(){
    	return this.selectedAuto;
    }
    
    public void setSelectedName(String name){
    	this.selectedName = name;
    }
    
    public List<String> getModelNameList(){
    	return this.modelNameList;
    }

    /**
     * Close the socket connection and release resources
     */
    @Override
    public void closeSession() {
        try {
            inStream = null;
            outStream = null;
            clientSock.close();
        } catch (IOException e) {
            if (DEBUG) System.err.println("Error closing socket to " + strHost);
        }
    }

    /**
     * Print the menu out to user
     */
    public void printMenu(){
        System.out.println("========== Menu =============");
        System.out.println("[0] stop the server");
        System.out.println("[1] Upload properties file");
        System.out.println("[2] Configure a car");
        System.out.println("=============================");

    }

    /**
     * Send message to server
     * @param output message
     */
    public void sendOutput(Object output) {
        try {
            outStream.writeObject(output);
        } catch (IOException e) {
            if (DEBUG) System.out.println("Error writing to " + strHost);
        }
    }

    /**
     * Receive response from server
     * @return
     */
    public Object receiveResponse(){
        try {
            return inStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Ask for a valid, readable filename from user
     * which should end with .properties or .txt
     * @return
     */
    public String askForValidFilename(){
        boolean valid = false;
        String path = null;

        while(!valid){
            System.out.println("Please enter a valid path for uploading, for example: NissanGTR.properties");
            try {
                path = inReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!path.endsWith(".properties") && !path.endsWith(".txt")){
                System.out.println("The path should either be .properties or .txt");
                continue;
            }

            File f = new File(path);

            if(!f.exists() || !f.isFile() || !f.canRead()){
                System.out.println("The path is invalid.");
                continue;
            }

            valid = true;
        }

        return path;
    }



}
