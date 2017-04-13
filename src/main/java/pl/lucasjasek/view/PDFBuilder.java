package pl.lucasjasek.view;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.lucasjasek.dto.OrderDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class PDFBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
                                    PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // "Zapisz plik"
        //response.setHeader("Content-Disposition", "attachment; filename=\"KinoPlaneta-bilet.pdf\"");

        OrderDTO pdfData;
        pdfData = (OrderDTO) model.get("orderDTO");

        doc.add(new Paragraph("Bilet"));

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{0.9f, 0.9f, 1.5f, 1.5f, 0.5f, 0.6f, 0.6f, 0.8f});
        table.setSpacingBefore(10);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.BLACK);
        font.setSize(12);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(6);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        cell.setPhrase(new Phrase("Imie", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nazwisko", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Film", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Data", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Godzina", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sala", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Miejsca", font));
        table.addCell(cell);

        List list = new List();

        for (int i = 0; i < pdfData.getIds().size(); i++) {
            list.add((pdfData.getIds().get(i).toString()));
            list.add(", ");
        }

        Phrase phrase = new Phrase();
        phrase.add(list);

        table.addCell(pdfData.getName());
        table.addCell(pdfData.getSurname());
        table.addCell(pdfData.getEmail());
        table.addCell(pdfData.getFilm());
        table.addCell(pdfData.getDate());
        table.addCell(pdfData.getTime());
        table.addCell(pdfData.getHall());
        table.addCell(phrase);

        doc.add(table);

        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(pdfData.getUuid(), 1000, 1000, null);
        Image codeQrImage = barcodeQRCode.getImage();
        codeQrImage.setAbsolutePosition(680, 350);
        codeQrImage.scaleAbsolute(100, 100);

        doc.add(codeQrImage);
    }
}