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

/**
 * Servlet that responds with a JSON array of all stored fruit names.
 * It fetches the list via RMI task to ensure consistency.
 */
@WebServlet(name = "FruitListServlet", urlPatterns = {"/FruitListServlet"})
public class FruitListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Set<String> fruits = FruitComputeTaskRegistry.sendTask(new ListFruitNames());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            String json = new Gson().toJson(fruits);
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "❌ Failed to fetch fruit list via RMI");
        }
    }
}
