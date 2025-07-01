package client;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import server.FruitComputeTaskRegistry;
import server.tasks.ListFruitNames;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/// This Servlet Responds with a JSON array of all stored fruit names, it populates my dropdowns in the web client


@WebServlet(name = "FruitListServlet", urlPatterns = {"/FruitListServlet"})
public class FruitListServlet extends HttpServlet {
/// This handles GET requests sent to /FruitListServlet, it retrieves fruit names via RMI and returns them as a JSON array
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            /// call the RMI registry and execute the ListFruitNames task
            Set<String> fruits = FruitComputeTaskRegistry.sendTask(new ListFruitNames());

            /// Set the content type of the response to JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            /// Get the output stream to send the JSON back to the client
            PrintWriter out = response.getWriter();

            /// Convert the Set of fruit names to JSON format using Gson
            String json = new Gson().toJson(fruits);

            /// Write the JSON to the HTTP response
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();

            /// Send HTTP 500 error to the client with custom message
            response.sendError(500, "❌ Failed to fetch fruit list via RMI");
        }
    }
}
