package exception;

import model.Automobile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ychu1 on 16/2/7.
 * <p>
 * Exception class which would fix exceptions generated during automobile's creation and update
 */
public class AutoException extends Exception implements FixAuto {
    private final int basePriceMissingErrno = 1;
    private final int optionSetNotFoundErrno = 2;
    private final int modelNameMissingErrno = 3;
    private final int fileNameErrorErrno = 4;
    private final int optionNotFoundErrno = 5;
    private final String logFileName = "log.txt";
    private int errorNo;
    private String errorMsg;
    private Fix1To5 fix1To5;
    private Automobile auto;

    /**
     * Constructor
     */
    public AutoException(String errorMsg, Automobile auto) {
        this.errorMsg = errorMsg;
        this.fix1To5 = new Fix1To5();
        this.auto = auto;
    }

    /**
     * Fix the exception and log down
     *
     * @param errno errno
     */
    public void fix(int errno) {
        this.errorNo = errno;

        this.printErrorInfo();

        switch (errno) {
            case basePriceMissingErrno:
                fix1To5.fixBasePriceMissing(auto);
                break;
            case modelNameMissingErrno:
                fix1To5.fixAutoMobileNameMissing(auto);
                break;
            case optionSetNotFoundErrno:
                fix1To5.fixOptionSetNotFound(auto);
                break;
            case fileNameErrorErrno:
                fix1To5.fixFilenameError(auto);
                break;
            case optionNotFoundErrno:
                fix1To5.fixOptionNotFound(auto);
        }

        log();
    }

    /**
     * Print out errrno and error message
     */
    public void printErrorInfo() {
        System.out.println("[Exception " + this.errorNo + " ]: " + this.errorMsg);
    }

    /**
     * Getter
     *
     * @return errorno
     */
    public int getErrorNo() {
        return errorNo;
    }

    /**
     * Setter
     *
     * @param errorNo errorno
     */
    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * Getter
     *
     * @return error message
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Setter
     *
     * @param errorMsg error message
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Log exception into log file with timestamp
     */
    public void log() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(
                    new File(logFileName),
                    true)); //append
            Date date = new Date();
            writer.print("Errno: " + errorNo + " message: " + errorMsg + " Time: " + new Timestamp(date.getTime()) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
