package server.tasks;

import shared.Task;    // Import the generic Task interface for RMI execution

/**
 * Task to generate a receipt based on the total cost, amount paid, and cashier name.
 * Implements Task<String>, so it returns a String when executed remotely.
 */
public class CalculateCost implements Task<String> {

    /// Fields to hold the required data for the receipt
    private final double totalCost;     // Total cost of the items purchased
    private final double amountGiven;   // Amount of money provided by the customer
    private final String cashier;       // Name of the cashier issuing the receipt

    /// Constructor to initialize receipt values.
    public CalculateCost(double totalCost, double amountGiven, String cashier) {
        this.totalCost = totalCost;
        this.amountGiven = amountGiven;
        this.cashier = cashier;
    }

    /**
     * This method is called when the task is executed on the RMI server.
     * It calculates the change and formats the receipt as a multiline string.
     */
    @Override
    public String execute() {
        /// Calculate the change due to the customer
        double change = amountGiven - totalCost;

        /// Return a neatly formatted receipt string
        return String.format("""
                🧾 Receipt:
                -----------------------
                Total Cost : %.2f
                Amount Paid: %.2f
                Change Due : %.2f
                Cashier    : %s
                -----------------------
                """, totalCost, amountGiven, change, cashier);
    }
}
