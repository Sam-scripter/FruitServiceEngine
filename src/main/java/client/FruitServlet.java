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
 * Servlet that handles fruit service form requests.
 * It translates the form input into RMI tasks and forwards the result to index.jsp.
 */
@WebServlet(name = "FruitServlet", urlPatterns = {"/FruitServlet"})
public class FruitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");
        String amountStr = request.getParameter("amount");
        String cashier = request.getParameter("cashier");

        String result;

        try {
            switch (action) {
                case "add":
                    if (priceStr != null && !priceStr.isEmpty()) {
                        double price = Double.parseDouble(priceStr);
                        result = FruitComputeTaskRegistry.sendTask(new AddFruitPrice(name, price));
                    } else {
                        result = "⚠️ Price is required to add a fruit.";
                    }
                    break;

                case "update":
                    if (priceStr != null && !priceStr.isEmpty()) {
                        double newPrice = Double.parseDouble(priceStr);
                        result = FruitComputeTaskRegistry.sendTask(new UpdateFruitPrice(name, newPrice));
                    } else {
                        result = "⚠️ New price is required to update.";
                    }
                    break;

                case "delete":
                    result = FruitComputeTaskRegistry.sendTask(new DeleteFruitPrice(name));
                    break;

                case "cost":
                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        double qty = Double.parseDouble(quantityStr);
                        result = FruitComputeTaskRegistry.sendTask(new CalFruitCost(name, qty));
                    } else {
                        result = "⚠️ Quantity is required to calculate cost.";
                    }
                    break;

                case "receipt":
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
                    result = FruitComputeTaskRegistry.sendTask(new ListFruits());
                    break;

                default:
                    result = "⚠️ Invalid task.";
            }

        } catch (Exception e) {
            result = "❌ Error: " + e.getMessage();
            e.printStackTrace(); // optional: log to server logs
        }

        // Attach result and forward back to the form
        request.setAttribute("result", result);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
