package org.example;
 import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for the number of parking spots
        System.out.println("Enter the maximum number of parking spots:");
        int totalSpots;
        try {
            totalSpots = Integer.parseInt(scanner.nextLine().trim());
            if (totalSpots <= 0) {
                System.out.println("Number of spots must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        ParkingLot lot = new ParkingLot(totalSpots);

        // Display the maximum number of parking spots
        lot.displayMaxSpots();

        while (true) {
            System.out.println("\nEnter command (park, remove, display, exit):");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("exit")) {
                System.out.println("Exiting program.");
                break;
            }

            switch (command) {
                case "park":
                    System.out.println("Enter vehicle type (bike, car, bus):");
                    String vehicleType = scanner.nextLine().trim().toLowerCase();

                    System.out.println("Enter vehicle number:");
                    String vehicleNumber = scanner.nextLine().trim();

                    String parkResult = lot.parkVehicle(vehicleType, vehicleNumber);
                    System.out.println(parkResult);
                    break;

                case "remove":
                    System.out.println("Enter the starting spot to remove the vehicle from:");
                    int startingSpot;
                    try {
                        startingSpot = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid spot number.");
                        continue;
                    }

                    String removeResult = lot.removeVehicle(startingSpot);
                    System.out.println(removeResult);
                    break;

                case "display":
                    lot.displayAvailableSpots();
                    break;

                default:
                    System.out.println("Invalid command. Please enter 'park', 'remove', 'display', or 'exit'.");
                    break;
            }
        }

        scanner.close();
    }
}