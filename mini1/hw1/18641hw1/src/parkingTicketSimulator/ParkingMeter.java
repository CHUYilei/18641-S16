package parkingTicketSimulator;

/**
 * ParkingMeter-
 * responsible for parking time purchasing
 */
public class ParkingMeter {
    private int purchasedParkingTime;

    public ParkingMeter(int purchasedParkingTime) {
        this.purchasedParkingTime = purchasedParkingTime;
    }

    public int getPurchasedParkingTime() {
        return purchasedParkingTime;
    }
}
