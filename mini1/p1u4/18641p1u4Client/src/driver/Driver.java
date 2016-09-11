package driver;

import client.DefaultSocketClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Project 1 Unit 4 Driver class for Client
 */
public class Driver {
    public static void main(String[] args) {
        String strLocalHost = "";
        try{
            strLocalHost = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e){
            System.err.println ("Unable to find local host");
        }

        DefaultSocketClient client = new DefaultSocketClient(strLocalHost);
        client.start();
    }
}
