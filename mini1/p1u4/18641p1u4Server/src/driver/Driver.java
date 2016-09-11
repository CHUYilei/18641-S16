package driver;

import adapter.BuildAuto;
import model.Automobile;
import server.DefaultSocketServer;
import server.SocketServerConstant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project 1 Unit 4 Driver class for Server
 */
public class Driver {
    public static void main(String[] args) {
        ServerSocket serverSock = null;

        try {
            serverSock = new ServerSocket(SocketServerConstant.iDAYTIME_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // start accepting client socket
        try {
            while(true){
                Socket clientSock = serverSock.accept();
                System.out.println("Accept a client");
                DefaultSocketServer executor = new DefaultSocketServer(clientSock);
                executor.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
