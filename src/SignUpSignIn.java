import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SignUpSignIn {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<People> arrayList = new ArrayList<>();
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight(1111, "2023-11-28", "12:00", "Kiev", 45));
        flights.add(new Flight(1112, "2023-11-28", "11:00", "Kiev", 55));
        flights.add(new Flight(1113, "2023-11-28", "13:00", "Kiev", 35));
        flights.add(new Flight(1114, "2023-12-30", "12:00", "Kiev", 25));
        People loggedInUser = null;
        // Assume flights and user data are pre-populated for demonstration purposes

        int choice = 0;

        while (choice != 8) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Sign up");
            System.out.println("2. Sign in");
            System.out.println("3. Online-board (Flights from Kiev in the next 24 hours)");
            System.out.println("4. Show flight info by ID");
            System.out.println("5. Search and book a flight");
            System.out.println("6. Cancel the booking");
            System.out.println("7. My flights");
            System.out.println("8. Exit the program");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter the username you want to sign up:");
                    String username = sc.nextLine();
                    System.out.println("Enter the password:");
                    String password = sc.nextLine();
                    People p = new People(username, password);
                    arrayList.add(p);
                    System.out.println("Sign up successful!");
                    break;

                case 2:
                    System.out.println("Enter the username to sign in:");
                    String username1 = sc.nextLine();
                    System.out.println("Enter the password:");
                    String password1 = sc.nextLine();

                    // Search for the user in the arrayList
                    for (People person : arrayList) {
                        if (username1.equals(person.getName()) && password1.equals(person.getPass())) {
                            loggedInUser = person;
                            break;
                        }
                    }

                    if (loggedInUser != null) {
                        System.out.println("Welcome, " + loggedInUser.getName() + "! You signed in successfully.");
                    } else {
                        System.out.println("Wrong credentials. Sign in failed.");
                    }
                    break;

                case 3:
                    System.out.println("Flights from Kiev in the next 24 hours:");

                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime twentyFourHoursLater = now.plusHours(24);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    for (Flight flight : flights) {
                        if (flight.getDestination().equals("Kiev")) {
                            LocalDateTime flightDateTime = LocalDateTime.parse(flight.getDate() + " " + flight.getTime(), formatter);
                            if (flightDateTime.isAfter(now) && flightDateTime.isBefore(twentyFourHoursLater)) {
                                System.out.println("ID: " + flight.getId() + ", Date: " + flight.getDate() +
                                        ", Time: " + flight.getTime() + ", Destination: " + flight.getDestination() +
                                        ", Free Seats: " + flight.getFreeSeats());
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter the flight ID: ");
                    int flightId = sc.nextInt();
                    sc.nextLine(); // Consume newline

                    Flight foundFlight = null;
                    for (Flight flight : flights) {
                        if (flight.getId() == flightId) {
                            foundFlight = flight;
                            break;
                        }
                    }

                    if (foundFlight != null) {
                        System.out.println("Flight Information:");
                        System.out.println("ID: " + foundFlight.getId());
                        System.out.println("Date: " + foundFlight.getDate());
                        System.out.println("Time: " + foundFlight.getTime());
                        System.out.println("Destination: " + foundFlight.getDestination());
                        System.out.println("Free Seats: " + foundFlight.getFreeSeats());
                    } else {
                        System.out.println("Flight with ID " + flightId + " not found.");
                    }
                    break;
                case 5:
                    System.out.println("Search and book a flight:");
                    System.out.println("Enter destination:");
                    String destination = sc.nextLine();
                    System.out.println("Enter date (yyyy-MM-dd):");
                    String date = sc.nextLine();
                    System.out.println("Enter number of tickets:");
                    int tickets = sc.nextInt();
                    sc.nextLine(); // Consume newline

                    ArrayList<Flight> availableFlights = new ArrayList<>();


                    for (Flight flight : flights) {
                        if (flight.getDestination().equals(destination) && flight.getDate().equals(date) && flight.getFreeSeats() >= tickets) {
                            availableFlights.add(flight);
                        }
                    }

                    if (!availableFlights.isEmpty()) {
                        System.out.println("Available flights:");
                        for (Flight flight : availableFlights) {
                            System.out.println("ID: " + flight.getId() + ", Date: " + flight.getDate() +
                                    ", Time: " + flight.getTime() + ", Destination: " + flight.getDestination() +
                                    ", Free Seats: " + flight.getFreeSeats());
                        }

                        System.out.print("Enter the flight ID to book or enter 0 to cancel: ");
                        int chosenFlightId = sc.nextInt();
                        sc.nextLine(); // Consume newline

                        if (chosenFlightId == 0) {
                            System.out.println("Booking canceled.");
                        } else {
                            Flight selectedFlight = null;
                            for (Flight flight : availableFlights) {
                                if (flight.getId() == chosenFlightId) {
                                    selectedFlight = flight;
                                    break;
                                }
                            }

                            if (selectedFlight != null) {
                                if (selectedFlight.bookSeats(tickets)) {
                                    System.out.println("Booking successful for Flight ID: " + selectedFlight.getId());
                                } else {
                                    System.out.println("Booking failed. Not enough available seats.");
                                }
                            } else {
                                System.out.println("Invalid flight ID.");
                            }
                        }
                    } else {
                        System.out.println("No available flights found for the specified criteria.");
                    }
                    break;


                case 6:
                    System.out.print("Enter the flight ID to cancel booking: ");
                    int cancelFlightId = sc.nextInt();
                    System.out.print("Enter the number of tickets to cancel: ");
                    int cancelTickets = sc.nextInt();
                    sc.nextLine(); // Consume newline

                    Flight cancelFlight = null;
                    for (Flight flight : flights) {
                        if (flight.getId() == cancelFlightId) {
                            cancelFlight = flight;
                            break;
                        }
                    }

                    if (cancelFlight != null) {
                        if (cancelFlight.cancelSeats(cancelTickets)) {
                            System.out.println("Cancellation successful for Flight ID: " + cancelFlight.getId());
                            // Perform additional actions related to successful cancellation
                        } else {
                            System.out.println("Invalid number of tickets to cancel.");
                        }
                    } else {
                        System.out.println("Invalid flight ID.");
                    }
                    break;
                case 7:
                    System.out.println("sivu");


                case 8:
                    System.out.println("Exiting the program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        sc.close();
    }
}