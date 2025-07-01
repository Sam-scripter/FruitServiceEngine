package server.tasks;

import shared.Task;

/**
 * Task to print a receipt with total cost, amount given, and change due.
 */
public class CalculateCost implements Task<String> {
    private final double totalCost;
    private final double amountGiven;
    private final String cashier;

    public CalculateCost(double totalCost, double amountGiven, String cashier) {
        this.totalCost = totalCost;
        this.amountGiven = amountGiven;
        this.cashier = cashier;
    }

    @Override
    public String execute() {
        double change = amountGiven - totalCost;
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
