package exception;

/**
 * Customized exception. Throw when input file has more than 40 records
 */
public class FileValidityException extends Exception {
    private static final long serialVersionUID = -6828653182084955115L;
    private String message;

    /**
     * Constructor
     * 
     * @param message
     *            message to be passed to user
     */
    public FileValidityException(String message) {
        this.message = message;
    }

    /**
     * Print message out
     */
    public void printMessage() {
        System.out.println(this.message);
    }
}
