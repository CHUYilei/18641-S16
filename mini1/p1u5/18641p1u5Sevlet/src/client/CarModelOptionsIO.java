package client;

import util.FileIO;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by Yilei CHU on 16/2/21.
 *
 * helper class for client
 */
public class CarModelOptionsIO {
    final static int CHUNKSIZE = 1024;

    /**
     * Load the properties object from file and send to server via out stream
     * then receive server's response to verify success
     *
     * @param filename .properties file
     * @param outStream object output stream
     */
    public void sendPropObjectToServer(String filename,ObjectInputStream inStream, ObjectOutputStream outStream){
        Properties prop = FileIO.buildPropertiesObjectFromFile(filename);

        try {
            outStream.writeObject(prop);
        } catch (IOException e) {
            System.err.println("Error sending properties object to server");
        }

        System.out.println("[INFO] Client finish uploading properties object. Waiting for server ACK");

        String serverACK = null;
        try {
            serverACK = (String) inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(serverACK);
    }

    /**
     * Send the user chosen text file to server using socket stream
     * then receive server's response to verify success
     *
     * @param path text file
     */
    public void sendTextFileToServer(String path,Socket clientSock,ObjectInputStream inStream){
        FileInputStream fin = null;
        BufferedOutputStream bufOut = null;

        try {
            fin = new FileInputStream(new File(path));
            bufOut = new BufferedOutputStream(clientSock.getOutputStream());

            byte[] buf = new byte[CHUNKSIZE];
            int len = 0;

            while ((len = fin.read(buf, 0, CHUNKSIZE)) !=-1) {
                bufOut.write(buf,0,len);
                bufOut.flush();
            }

        } catch (IOException e) {
            System.err.println("Error sending text file to server");
            e.printStackTrace();
        }

        System.out.println("[INFO] Client finish uploading text file. Waiting for server ACK");

        String serverACK = null;
        try {
            serverACK = (String) inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(serverACK);
    }
}
