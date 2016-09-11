package server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * Created by Yilei CHU on 16/2/20.
 *
 * The server thread class
 */
public class DefaultSocketServer extends Thread implements SocketServerInterface, SocketServerConstant {
    private Socket sock;    //client socket
    private int iPort;

    // for communication
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;

    private static final int CHUNKSIZE = 1024;

    // temporary file name for holding received file content
    private static final String TEMPFILENAME = "tempFile";

    // operation code
    private static final String QUIT_OPCODE = "0";
    private static final String UPLOAD_OPCODE = "1";
    private static final String CONFIG_OPCODE = "2";

    private BuildCarModelOptions buildCarModelOptions = null;


    /**
     * Constructor
     * @param sock client socket
     */
    public DefaultSocketServer(Socket sock) {
        setPort(iDAYTIME_PORT);

        this.sock = sock;
        this.buildCarModelOptions = new BuildCarModelOptions();
    }

    /**
     * Start running
     */
    public void run() {
        if (openConnection()) {
            handleSession();
        }
    }

    /**
     * Initialize the stream
     * @return
     */
    @Override
    public boolean openConnection() {
        try {
            outStream = new ObjectOutputStream(sock.getOutputStream());
            inStream = new ObjectInputStream(sock.getInputStream());
        } catch (Exception e) {
            if (DEBUG) System.err.println("Unable to obtain stream to/from port" + iPort);
            return false;
        }

        return true;
    }

    /**
     * Receive operation code
     * Conduct corresponding operation
     * RUn forever until being asked to stop
     */
    @Override
    public void handleSession() {
        if (DEBUG) System.out.println("Handling session with port :" + iPort);

        while(true) {
            // receive opcode
            String opcode = null;
            try {
                opcode = (String) inStream.readObject();
            } catch (IOException e) {
                continue;
            } catch (ClassNotFoundException e) {
                continue;
            }

            if (opcode.equals(this.QUIT_OPCODE)) { //server stops handling sessions
                handleQuitRequest();
                return; //TODO
            } else if (opcode.equals(this.UPLOAD_OPCODE)) {
                handleUploadRequest();
            } else if (opcode.equals(this.CONFIG_OPCODE)) {
                handleConfigureRequest();
            } else {
                // wrong opcode, ask for send again
                handleWrongOpcode();
            }
        }
    }

    /**
     * Handle client's quit request
     */
    public void handleQuitRequest(){
        String message = "Session stops. Bye bye!";

        // send message to client to let it know server quits
        sendOutput(message);
    }

    /**
     * Handle wrong opcode sent by client
     */
    public void handleWrongOpcode(){
        String message = "Wrong opcode! Please send QUIT [0], UPLOAD [1] or CONFIG [2]";

        // send message to client to let it know server quits
        sendOutput(message);
    }


    /**
     * Receive file, determine it is properties file or text file
     * Construct automobile object from file and add to linked hashmap
     */
    public void handleUploadRequest(){
        String message = "[INFO] Server ready.";

        // send message to client to let it upload
        sendOutput(message);

        // receive uploading object choice
        String response = (String)receiveResponse();

        System.out.println("[INFO] user file choice "+response);

        // validate the choice
        if(response == null || !(response.equals("P") || response.equals("T"))){
            System.err.println("[INFO] Illegal choice "+response);
            return;
        }

        message = "Server ready. Please start uploading the file";
        sendOutput(message);

        String tempFileName = null;

        if(response.equals("P")){   /* Case 1: receive a properties object */
            Properties prop = (Properties)receiveResponse();
            tempFileName = TEMPFILENAME+".properties";

            // store the properties object to temporary file
            File f = new File(tempFileName);
            OutputStream out = null;
            try {
                out = new FileOutputStream( f );
                prop.store(out,"Server stores properties object");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(response.equals("T")){ /* Case 2: receive a text file */

            // receive the file and store in local temporary file
            byte[] buffer = new byte[CHUNKSIZE];
            OutputStream fileWriter = null;
            BufferedInputStream bufInStream = null;

            try {
                tempFileName = TEMPFILENAME+".txt";
                fileWriter = new FileOutputStream(tempFileName);
                bufInStream = new BufferedInputStream(sock.getInputStream());
            } catch (FileNotFoundException ex) {
                System.out.println("File "+tempFileName +" not found. ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int len = 0;
                while ((len = bufInStream.read(buffer, 0, CHUNKSIZE)) > 0) {
                    fileWriter.write(buffer, 0, len);
                    fileWriter.flush();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Server starts building automobile from file "+tempFileName);

        // build the automobile object from file
        this.buildCarModelOptions.buildAutoFromFile(tempFileName);

        // delete the temporary file
        File f = new File(tempFileName);
        f.delete();

        // reply with build successful message
        message = "[INFO] Server builds automobile successfully";
        sendOutput(message);

        System.out.println("[INFO] Server finish handling upload request");
    }

    /**
     * Provide list of automibile names to client
     * receive user's choice from client
     * send the chosen instance back to client to let it print
     * receive client's ACK
     */
    public void handleConfigureRequest(){
        String message = "Server ready. Please receive automobile list";

        // send message to client to let it upload
        sendOutput(message);

        // prepare name list
        List<String> autoList = this.buildCarModelOptions.getAvailableModelList();

        // send name list to client
        try {
            outStream.writeObject(autoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if list empty, stop receiving
        if(autoList.isEmpty()){
            return;
        }

        // receive user's choice from client
        String chosenName = (String)receiveResponse();

        // extract the auto object from linked hashmap and send it back
        this.buildCarModelOptions.sendChosenModel(chosenName,outStream);

        // send the success message
        message = "[INFO] Server sends chosen automobile successfully";
        sendOutput(message);
    }


    /**
     * Close the session by releasing resources and closing socket
     */
    @Override
    public void closeSession() {
        try {
            inStream = null;
            outStream = null;
            sock.close();
        } catch (IOException e) {
            if (DEBUG) System.err.println("Error closing socket on server");
        }
    }

    /**
     * Send the message back to client
     * @param output message
     */
    public void sendOutput(Object output) {
        try {
            outStream.writeObject(output);
        } catch (IOException e) {
            if (DEBUG) System.out.println("Error writing to outStream in server");
        }
    }

    /**
     * Receive response from client
     * @return response object
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
     * Setter
     * @param iPort
     */
    public void setPort(int iPort) {
        this.iPort = iPort;
    }
}
