package cinema;

import java.util.Scanner;

public class Cinema {

    private static final Scanner scanner = new Scanner(System.in);

    private static int rows;
    private static int seats;
    private static int totalTicketsBought;
    private static int income;

    private static int[][] coordinates;

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        System.out.println();

        Cinema.rows = rows;
        Cinema.seats = seats;

        coordinates = new int[rows][seats];


        loop:
        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    showSeats();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    statistics();
                    break;
                case 0:
                    break loop;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }
    }

    private static void showSeats() {
        System.out.println("Cinema:");
        System.out.print("  ");

        for (int i = 0; i <= rows; i++) {
            if (i > 0) {
                System.out.print(i);
            }
            for (int j = 1; j <= seats; j++) {
                if (i == 0) {
                    System.out.print(j + " ");
                } else {
                    if (coordinates[i - 1][j - 1] > 0) {
                        System.out.print(" B");
                        continue;
                    }
                    System.out.print(" S");

                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void buyTicket() {

        System.out.println("Enter a row number:");
        int rowNum = scanner.nextInt();

        System.out.println("Enter a seat number in that row:");
        int seatNum = scanner.nextInt();

        if ((rowNum) > rows || (seatNum) > seats) {
            System.out.println("\nWrong input!\n");
            buyTicket();
            return;
        }

        if (coordinates[rowNum - 1][seatNum - 1] > 0) {
            System.out.println("\nThat ticket has already been purchased!\n");
            buyTicket();
            return;
        }
        if (coordinates[rowNum - 1][seatNum - 1] == 0) {
            coordinates[rowNum - 1][seatNum - 1] = 1;
        }
        totalTicketsBought++;
        calculateCost(rowNum);
    }

    private static void statistics() {
        System.out.println();
        float tickets = (float) totalTicketsBought;
        float seatsTotal = (float) seats * rows;
        float percentage = (tickets / seatsTotal) * 100;/*(totalTicketsBought/(rows * seats)) * 100;*/

        System.out.printf("Number of purchased tickets: %d\n", totalTicketsBought);
        System.out.printf("Percentage: %.2f", percentage);
        System.out.print("%\n");
        System.out.printf("Current income: $%d\n", income);
        System.out.printf("Total income: $%d\n", calculateTotalIncome());

        System.out.println();
    }

    public static int calculateTotalIncome() {
        int totalSeats = seats * rows;

        int halfTotalSeats = totalSeats / 2;

        if (totalSeats > 60) {
            return ((halfTotalSeats * 10) + (halfTotalSeats * 8));
        } else {
            return totalSeats * 10;
        }
        /*if (totalSeats < 60) {
            return totalSeats * 10;
        } else {

            if (totalSeats % 2 != 0) {
                return (halfTotalSeats * 10) + ((halfTotalSeats + 1) * 8);
            }else{
                return (halfTotalSeats * 10) + (halfTotalSeats * 8);
            }
        }*/
    }

    private static void calculateCost(int row) {
        if ((seats * rows) < 60) {
            System.out.println("Ticket price: $10");
            income += 10;
        } else {
            if (row <= (rows / 2)) {
                System.out.println("Ticket price: $10");
                income += 10;
            } else {
                income += 8;
                System.out.println("Ticket price: $8");
            }
        }
        System.out.println();
    }
}