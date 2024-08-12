package org.example;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private static final int BIKE_SPOT_SIZE = 1;
    private static final int CAR_SPOT_SIZE = 2;
    private static final int BUS_SPOT_SIZE = 4;

    private final int totalSpots;
    private final List<Vehicle> parkingSpots;

    public ParkingLot(int totalSpots) {
        this.totalSpots = totalSpots;
        this.parkingSpots = new ArrayList<>(totalSpots);
        for (int i = 0; i < totalSpots; i++) {
            parkingSpots.add(null); // Initialize all spots as empty (null)
        }
    }

    public void displayMaxSpots() {
        System.out.println("Maximum number of parking spots: " + totalSpots);
    }

    public void displayAvailableSpots() {
        int availableSpots = calculateAvailableSpots();
        System.out.println("Available parking spots: " + availableSpots);
    }

    public String parkVehicle(String vehicleType, String vehicleNumber) {
        int requiredSpots = getSpotSize(vehicleType);

        if (requiredSpots == -1) {
            return "Invalid vehicle type.";
        }

        int startSpot = (int) findAvailableSpots(requiredSpots, vehicleType, vehicleNumber);

        if (startSpot == -1) {
            return "Parking full.";
        }

        // Check if there are consecutive spots available
        if (!areConsecutiveSpotsAvailable(startSpot, requiredSpots)) {
            return "Cannot park the " + vehicleType + " because two adjacent spots are not available.";
        }

        // Ensure the required number of consecutive spots are free
        for (int i = startSpot; i < startSpot + requiredSpots; i++) {
            if (parkingSpots.get(i) != null) {
                return "Overlap detected. Cannot park vehicle.";
            }
        }

        // Park the vehicle
        for (int i = startSpot; i < startSpot + requiredSpots; i++) {
            parkingSpots.set(i, new Vehicle(vehicleType, vehicleNumber));
        }

        return "Vehicle parked. Vehicle type: " + vehicleType + ", Vehicle number: " + vehicleNumber + ", Parking spot: " + (startSpot + 1);
    }

    public String removeVehicle(int startingSpot) {
        int internalSpotIndex = startingSpot - 1;

        if (internalSpotIndex < 0 || internalSpotIndex >= totalSpots || parkingSpots.get(internalSpotIndex) == null) {
            return "No vehicle at this spot.";
        }

        Vehicle vehicle = parkingSpots.get(internalSpotIndex);
        int spotSize = getSpotSize(vehicle.getType());

        for (int i = 0; i < spotSize; i++) {
            parkingSpots.set(internalSpotIndex + i, null);
        }

        return "Vehicle removed. Vehicle type: " + vehicle.getType() + ", Vehicle number: " + vehicle.getNumber() + ", Parking spot: " + (startingSpot);
    }

    private Object findAvailableSpots(int requiredSpots, String vehicleType, String vehicleNumber) {
        int consecutiveFreeSpots = 0;

        for (int i = 0; i < totalSpots; i++) {
            if (parkingSpots.get(i) == null) {
                consecutiveFreeSpots++;
                if (consecutiveFreeSpots >= requiredSpots) {
                    return i - requiredSpots + 1;
                } else if (i + 1 < totalSpots && parkingSpots.get(i + 1) != null) {
                    return "Parking full.";
                }
            } else {
                consecutiveFreeSpots = 0;
            }
        }
        return -1;
    }

    private boolean areConsecutiveSpotsAvailable(int startSpot, int requiredSpots) {
        if (startSpot < 0 || startSpot + requiredSpots > totalSpots) {
            return false;
        }

        for (int i = startSpot; i < startSpot + requiredSpots; i++) {
            if (parkingSpots.get(i) != null) {
                return false;
            }
        }

        return true;
    }

    private int getSpotSize(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "bike":
                return BIKE_SPOT_SIZE;
            case "car":
                return CAR_SPOT_SIZE;
            case "bus":
                return BUS_SPOT_SIZE;
            default:
                return -1;
        }
    }

    private int calculateAvailableSpots() {
        int availableSpots = 0;
        int consecutiveFreeSpots = 0;

        for (int i = 0; i < totalSpots; i++) {
            if (parkingSpots.get(i) == null) {
                consecutiveFreeSpots++;
            } else {
                if (consecutiveFreeSpots > 0) {
                    availableSpots += consecutiveFreeSpots;
                }
                consecutiveFreeSpots = 0;
            }
        }

        if (consecutiveFreeSpots > 0) {
            availableSpots += consecutiveFreeSpots;
        }

        return availableSpots;
    }
}