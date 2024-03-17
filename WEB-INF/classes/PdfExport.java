import java.io.IOException;
import java.util.Iterator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Element;
import java.util.*;


@WebServlet("/PdfExport")
public class PdfExport extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=data_export.pdf");

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = request.getParameter("data");

            if (jsonData != null && !jsonData.isEmpty()) {
                JsonNode data = mapper.readTree(jsonData);

                if (data != null && data.isArray()) {
                    Document document = new Document();
                    PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
                    document.open();

                    PdfPTable table = createPdfTable(data);
                    document.add(table);

                    document.close();
                    writer.close();
                } else {
                    response.getWriter().println("Invalid JSON format: data is not an array");
                }
            } else {
                response.getWriter().println("Invalid JSON data: jsonData is null or empty");
            }

        } catch (DocumentException e) {
            e.printStackTrace();
            response.getWriter().println("Error generating PDF");
        }
    }




private PdfPTable createPdfTable(JsonNode data) throws DocumentException {
    Iterator<JsonNode> elements = data.elements();

    PdfPTable table = null;

    if (elements.hasNext()) {
        JsonNode firstRow = elements.next();
        Iterator<String> fieldNames = firstRow.fieldNames();

        List<String> fieldNameList = new ArrayList<>();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            fieldNameList.add(fieldName);
        }

        table = new PdfPTable(fieldNameList.size() + 1); 
        table.setWidthPercentage(100);

        table.addCell(createHeaderCell("SNO"));

        for (String fieldName : fieldNameList) {
            table.addCell(createHeaderCell(fieldName));
        }

        int sno = 1;
        do {
            table.addCell(createDataCell(String.valueOf(sno++))); 
            JsonNode currentRow = elements.next();
            fieldNames = currentRow.fieldNames();

            while (fieldNames.hasNext()) {
                table.addCell(createDataCell(currentRow.get(fieldNames.next()).asText()));
            }
        } while (elements.hasNext());
    }

    return table ;
}


private PdfPCell createHeaderCell(String header) {
    PdfPCell cell = new PdfPCell(new Phrase(header.toUpperCase(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK)));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
}

private PdfPCell createDataCell(String data) {
    PdfPCell cell = new PdfPCell(new Phrase(data, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
}

}
