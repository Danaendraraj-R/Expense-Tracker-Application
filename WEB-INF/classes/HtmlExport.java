import java.io.IOException;
import java.util.Iterator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/HtmlExport")
public class HtmlExport extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setHeader("Content-Disposition", "attachment; filename=data_export.html");

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = request.getParameter("data");

            if (jsonData != null && !jsonData.isEmpty()) {
                JsonNode data = mapper.readTree(jsonData);

                if (data != null && data.isArray()) {
                    response.getWriter().println("<html><head><title>Data Export</title></head><body>");
                    response.getWriter().println("<h2>Data Export</h2>");
                    response.getWriter().println("<table border='1'>");

                    Iterator<JsonNode> elements = data.elements();
                    if (elements.hasNext()) {
                        JsonNode firstRow = elements.next();
                        Iterator<String> fieldNames = firstRow.fieldNames();
                        response.getWriter().println("<tr>");
                        while (fieldNames.hasNext()) {
                            response.getWriter().println("<th>" + fieldNames.next() + "</th>");
                        }
                        response.getWriter().println("</tr>");
                        do {
                            JsonNode currentRow = elements.next();
                            fieldNames = currentRow.fieldNames();
                            response.getWriter().println("<tr>");
                            while (fieldNames.hasNext()) {
                                response.getWriter().println("<td>" + currentRow.get(fieldNames.next()).asText() + "</td>");
                            }
                            response.getWriter().println("</tr>");
                        } while (elements.hasNext());
                    }

                    response.getWriter().println("</table></body></html>");
                } else {
                    response.getWriter().println("Invalid JSON format: data is not an array");
                }
            } else {
                response.getWriter().println("Invalid JSON data: jsonData is null or empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error generating HTML");
        }
    }
}
