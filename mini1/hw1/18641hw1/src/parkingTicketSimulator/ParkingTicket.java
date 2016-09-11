package parkingTicketSimulator;

/**
 * ParkingTicket - 
 * issued by police
 * for cars whose parking time exceeds its purchased one
 */
public class ParkingTicket {
    private String officerName;
    private String officerBadge;

    private String carMake;
    private String carModel;
    private String carColor;
    private String carLicenseNum;

    private int fine;   // amount of money for fine

    /**
     * Constructor
     * @param officerName police name
     * @param officerBadge police badge
     * @param carMake car make
     * @param carModel car model
     * @param carColor car color
     * @param carLicenseNum car license
     * @param fineMinute amount of minutes to count into fine
     */
    public ParkingTicket(String officerName, String officerBadge, String carMake, String carModel, String carColor,
            String carLicenseNum,int fineMinute) {
        this.officerName = officerName;
        this.officerBadge = officerBadge;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carColor = carColor;
        this.carLicenseNum = carLicenseNum;
        this.fine = calculateFine(fineMinute);
    }

    /**
     * Getter
     * @return police name
     */
    public String getOfficerName() {
        return officerName;
    }

    /**
     * Getter
     * @return police badge
     */
    public String getOfficerBadge() {
        return officerBadge;
    }

    /**
     * Getter
     * @return the car's make on the fine
     */
    public String getCarMake() {
        return carMake;
    }

    /**
     * Getter
     * @return the car's model on the fine
     */
    public String getCarModel() {
        return carModel;
    }

    /**
     * Getter
     * @return the car's color on the fine
     */
    public String getCarColor() {
        return carColor;
    }

    /**
     * Getter
     * @return the car's license number on the fine
     */
    public String getCarLicenseNum() {
        return carLicenseNum;
    }

    /**
     * Getter
     * @return the amount of money on the fine
     */
    public int getFine() {
        return fine;
    }

    /**
     * Calculate fine amount from exceeding minutes
     * @param minute number of minutes exceeded (<1 hour will be counted as 1 hour)
     * @return amount of fine money
     */
    private int calculateFine(int minute){
        if(minute == 0)
            return 0;

        // less than 1h will be counted as an hour
        int hour = (int)Math.ceil(minute/(double)60);

        return 25+10*(hour-1);
    }
}
