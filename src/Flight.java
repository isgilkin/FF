class Flight {
    private int id;
    private String date;
    private String time;
    private String destination;
    private int freeSeats;
    private String bookedBy;

    public Flight(int id, String date, String time, String destination, int freeSeats) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.freeSeats = freeSeats;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDestination() {
        return destination;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public boolean bookSeats(int tickets) {
        if (freeSeats >= tickets) {
            freeSeats -= tickets; // Reduce the count of available seats
            return true; // Booking successful
        }
        return false; // Not enough available seats
    }
    public boolean cancelSeats(int numSeatsToCancel) {
        if (freeSeats >= numSeatsToCancel) {
            freeSeats += numSeatsToCancel; // Increase the count of available seats
            return true; // Cancellation successful
        }
        return false; // Invalid number of seats to cancel
    }

}