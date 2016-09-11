package server;

/**
 * Created by Yilei CHU on 16/2/20.
 */
public interface SocketServerInterface {
    /**
     * Open the socket connection
     * @return whether succeed
     */
    boolean openConnection();

    /**
     * Handle communication with client
     */
    void handleSession();

    /**
     * Close socket connection and release resource
     */
    void closeSession();
}
