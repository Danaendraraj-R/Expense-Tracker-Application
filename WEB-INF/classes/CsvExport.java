import java.io.IOException;
import java.util.Iterator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/CsvExport")
public class CsvExport extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=data.csv");

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = request.getParameter("data");

            if (jsonData != null && !jsonData.isEmpty()) {
                JsonNode data = mapper.readTree(jsonData);

                if (data != null && data.isArray()) {
                    Iterator<JsonNode> elements = data.elements();

                    if (elements.hasNext()) {
                        JsonNode firstRow = elements.next();

                        Iterator<String> fieldNames = firstRow.fieldNames();
                        while (fieldNames.hasNext()) {
                            response.getWriter().write(fieldNames.next());
                            if (fieldNames.hasNext()) {
                                response.getWriter().write(",");
                            }
                        }
                        response.getWriter().println();

                        do {
                            JsonNode currentRow = elements.next();
                            fieldNames = currentRow.fieldNames();

                            while (fieldNames.hasNext()) {
                                response.getWriter().write(currentRow.get(fieldNames.next()).asText());
                                if (fieldNames.hasNext()) {
                                    response.getWriter().write(",");
                                }
                            }
                            response.getWriter().println();
                        } while (elements.hasNext());
                    }
                } else {
                    response.getWriter().println("Invalid JSON format: data is not an array");
                }
            } else {
                response.getWriter().println("Invalid JSON data: jsonData is null or empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error generating CSV");
        }
    }
}
