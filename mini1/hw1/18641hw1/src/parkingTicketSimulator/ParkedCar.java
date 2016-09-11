package parkingTicketSimulator;

/**
 * Representation of car
 * with all car attributes
 */
public class ParkedCar {
    private String make;
    private String model;
    private String color;
    private String licenseNum;
    private int elapsedParkingTime; //already park for how long

    /**
     * Constructor for car
     * @param make car make
     * @param model car model
     * @param color car color
     * @param licenseNum car's license number
     */
    public ParkedCar(String make, String model, String color, String licenseNum) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.licenseNum = licenseNum;
        this.elapsedParkingTime = 0;
    }

    /**
     * Getter
     * @return car make
     */
    public String getMake() {
        return make;
    }

    /**
     * Getter
     * @return car model
     */
    public String getModel() {
        return model;
    }

    /**
     * Getter
     * @return car color
     */
    public String getColor() {
        return color;
    }

    /**
     * Getter
     * @return car license number
     */
    public String getLicenseNum() {
        return licenseNum;
    }

    /**
     * Getter
     * @return already park for how long
     */
    public int getElapsedParkingTime() {
        return elapsedParkingTime;
    }

    /**
     * Setter
     * @param make car make
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Setter
     * @param model car model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Setter
     * @param color car color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Setter
     * @param licenseNum car license
     */
    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    /**
     * Setter 
     * @param elapsedParkingTime already park for how long
     */
    public void setElapsedParkingTime(int elapsedParkingTime) {
        this.elapsedParkingTime = elapsedParkingTime;
    }
}
