package application;

public class Ship {
    public int size;
    public boolean intact = true;
    public int[] rowCoordinates; // Declare the arrays
    public int[] colCoordinates; // Declare the arrays

    // Constructor to initialize size and coordinate arrays
    public Ship(int size) {
        this.size = size;
        rowCoordinates = new int[size]; // Initialize the arrays with the specified size
        colCoordinates = new int[size]; // Initialize the arrays with the specified size
    }
}
