package com.thoughtworks.sample.cart.utility;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.thoughtworks.sample.cart.repository.Cart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {
    public void generate(List<Cart> cartItems) throws DocumentException, IOException {

        // Creating the Object of Document
        Document document = new Document(PageSize.A4);
        String filePath = System.getProperty("user.home") + "/Downloads/file1.pdf";
        // Getting instance of PdfWriter
        PdfWriter.getInstance(document, new FileOutputStream(filePath));


        document.open();


        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);


        Paragraph paragraph = new Paragraph("Invoice", fontTitle);


        paragraph.setAlignment(Paragraph.ALIGN_CENTER);


        document.add(paragraph);

        // Creating a table of 5 columns
        PdfPTable table = new PdfPTable(5);

        // Setting width of table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3, 3,3,3});
        table.setSpacingBefore(5);

        // Create Table Cells for table header
        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding
        cell.setBackgroundColor(CMYKColor.MAGENTA);
        cell.setPadding(5);


        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
        // Adding Cell to table
        cell.setPhrase(new Phrase("Product Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Unit Price", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Category", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell);

        Integer totalCost = 0;

        Integer totalCostPerItem ;
        for (Cart item : cartItems) {

            table.addCell(item.getProduct().getProductName());

            table.addCell(String.valueOf(item.getProduct().getUnitPrice()));

            table.addCell(String.valueOf(item.getQuantity()));

            table.addCell(item.getProduct().getCategory());
            totalCostPerItem = item.getQuantity() * item.getProduct().getUnitPrice().intValue();
            totalCost = totalCostPerItem + totalCost;
            table.addCell(String.valueOf(totalCostPerItem));

        }



        document.add(table);

        // Creating Total cost
        Paragraph totalCostParagraph = new Paragraph("Total Cost : Rs " + totalCost, fontTitle);
        totalCostParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(totalCostParagraph);

        // Closing the document
        document.close();
    }
}
