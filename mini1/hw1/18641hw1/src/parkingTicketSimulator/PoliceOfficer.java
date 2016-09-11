package parkingTicketSimulator;

/**
 * PoliceOfficer - 
 * police officer who will measure the parking time of a car
 * with its purchased time to decide whether to issue a ticket
 */
public class PoliceOfficer {
    private String name;
    private String badgeNum;

    /**
     * Constructor
     * @param name police name
     * @param badgeNum police badge
     */
    public PoliceOfficer(String name, String badgeNum) {
        this.name = name;
        this.badgeNum = badgeNum;
    }

    /**
     * Getter
     * @return police name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return police badge
     */
    public String getBadgeNum() {
        return badgeNum;
    }

    /**
     * whether parking time exceeds purchased one
     * @param car the car to be measured
     * @param meter the meter
     * @return whether parking time exceeds
     */
    public boolean isTimeExpired(ParkedCar car,ParkingMeter meter){
        if(car.getElapsedParkingTime() <= meter.getPurchasedParkingTime()){
            return false;
        }

        return true;
    }

    /**
     * Police issue a new ticket with all info
     * @param car the car to be fined
     * @param meter the meter
     * @return the ticket
     */
    public ParkingTicket issueTicket(ParkedCar car,ParkingMeter meter){
        int fineMinutes = car.getElapsedParkingTime() - meter.getPurchasedParkingTime();

        return new ParkingTicket(this.name, this.badgeNum, car.getMake(), car.getModel(), car.getColor(),
                car.getLicenseNum(),fineMinutes);
    }
}
