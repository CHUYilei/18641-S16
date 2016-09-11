package parkingTicketSimulator;

/**
 * Driver class for parking ticket simulation
 */
public class Driver {

    /**
     * The simulation of parking ticket
     * @param args command line arguments
     */
    public static void main(String[] args) {
        PoliceOfficer officer = new PoliceOfficer("officer","12345678");
        ParkedCar car = new ParkedCar("Audi","A6","white","PA123CDEF");

        /* Test 1: vehicle within parking time purchased */
        System.out.println("Test 1: vehicle within parking time purchased");
        ParkingMeter meter1 = new ParkingMeter(36);
        
        // less than 1 hour
        car.setElapsedParkingTime(29);
        System.out.println("Elapsed parking time: "+car.getElapsedParkingTime());
        System.out.println("Purchased parking time: "+meter1.getPurchasedParkingTime());
        System.out.println("Should issue ticket: "+officer.isTimeExpired(car,meter1));
        System.out.println();

        /* Test 2: vehicle out of parking time purchased */
        System.out.println("Test 2: vehicle out of parking time purchased");
        
        ParkingMeter meter2 = new ParkingMeter(52);
        
        // more than 1 hour
        car.setElapsedParkingTime(93);
        System.out.println("Elapsed parking time: "+car.getElapsedParkingTime());
        System.out.println("Purchased parking time: "+meter2.getPurchasedParkingTime());
        System.out.println("Should issue ticket: "+officer.isTimeExpired(car,meter2));

        ParkingTicket ticket1 = officer.issueTicket(car,meter2);
        System.out.println("Car make in ticket: "+ticket1.getCarMake());
        System.out.println("Car model in ticket: "+ticket1.getCarModel());
        System.out.println("Car color in ticket: "+ticket1.getCarColor());
        System.out.println("Car license in ticket: "+ticket1.getCarLicenseNum());
        System.out.println("Name of police issuing ticket: "+ticket1.getOfficerName());
        System.out.println("Badge of police issuing ticket: "+ticket1.getOfficerBadge());
        System.out.println("Fine in dollar: "+ticket1.getFine());
        System.out.println();

        /* Test 3: vehicle just in parking time purchased */
        System.out.println("Test 3: vehicle just in parking time purchased");

        ParkingMeter meter3 = new ParkingMeter(80);
        car.setElapsedParkingTime(80);
        System.out.println("Elapsed parking time: "+car.getElapsedParkingTime());
        System.out.println("Purchased parking time: "+meter3.getPurchasedParkingTime());
        System.out.println("Should issue ticket: "+officer.isTimeExpired(car,meter3));
    }
}
