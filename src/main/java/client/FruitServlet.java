package client;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import server.FruitComputeTaskRegistry;
import server.tasks.*;

import java.io.IOException;

/**
 * This servlet handles POST form submissions from the web UI.
 * It translates form data into RMI tasks and forwards the result back to the user.
 */
@WebServlet(name = "FruitServlet", urlPatterns = {"/FruitServlet"})
public class FruitServlet extends HttpServlet {

    /// Handles POST requests submitted by the form in index.jsp

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /// Retrieve form inputs from the request
        String action = request.getParameter("action");         // Type of task (add, update, etc.)
        String name = request.getParameter("name");             // Name of the fruit
        String priceStr = request.getParameter("price");        // Price or total cost
        String quantityStr = request.getParameter("quantity");  // Quantity for cost calculation
        String amountStr = request.getParameter("amount");      // Amount given (used in receipt)
        String cashier = request.getParameter("cashier");       // Cashier name

        String result;  // To store the response/result of task execution

        try {
            /// Determine the task to perform based on "action"
            switch (action) {
                case "add":
                    /// Ensure price is provided
                    if (priceStr != null && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);    // Convert price to double
                        /// Create AddFruitPrice task and send it to the RMI server
                        result = FruitComputeTaskRegistry.sendTask(new AddFruitPrice(name, price));
                    } else {
                        result = "⚠️ Price is required to add a fruit.";
                    }
                    break;

                case "update":
                    /// Ensure new price is provided
                    if (priceStr != null && !priceStr.isEmpty()) {
                        double newPrice = Double.parseDouble(priceStr);
                        result = FruitComputeTaskRegistry.sendTask(new UpdateFruitPrice(name, newPrice));
                    } else {
                        result = "⚠️ New price is required to update.";
                    }
                    break;

                case "delete":
                    /// Create and send DeleteFruitPrice task
                    result = FruitComputeTaskRegistry.sendTask(new DeleteFruitPrice(name));
                    break;

                case "cost":
                    /// Ensure quantity is provided
                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        double qty = Double.parseDouble(quantityStr);
                        result = FruitComputeTaskRegistry.sendTask(new CalFruitCost(name, qty));
                    } else {
                        result = "⚠️ Quantity is required to calculate cost.";
                    }
                    break;

                case "receipt":
                    /// Ensure both total cost and amount given are provided
                    if (priceStr != null && amountStr != null &&
                            !priceStr.isEmpty() && !amountStr.isEmpty()) {
                        double totalCost = Double.parseDouble(priceStr);
                        double amountGiven = Double.parseDouble(amountStr);
                        result = FruitComputeTaskRegistry.sendTask(new CalculateCost(totalCost, amountGiven, cashier));
                    } else {
                        result = "⚠️ Total cost and amount given are required.";
                    }
                    break;

                case "list":
                    /// Fetch all fruits as a displayable list
                    result = FruitComputeTaskRegistry.sendTask(new ListFruits());
                    break;

                default:
                    /// Handle unrecognized actions
                    result = "⚠️ Invalid task.";
            }

        } catch (Exception e) {
            /// Handle unexpected exceptions
            result = "❌ Error: " + e.getMessage();  // Display error message to user
            e.printStackTrace(); // log to server logs
        }

        /// Attach result and forward back to the form
        request.setAttribute("result", result);

        /// Forward the request and result back to index.jsp for display
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
