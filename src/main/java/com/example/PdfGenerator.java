package com.example;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
//import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PdfGenerator {
    public static void generatePdf(List<ExcelData> data, String outputPath) throws IOException {
        try (OutputStream fos = new FileOutputStream(outputPath);
             PdfWriter writer = new PdfWriter(fos);
             PdfDocument pdfDocument = new PdfDocument(writer);
             Document document = new Document(pdfDocument)) {
            int width =500; // set width of the image
            int height = 500; // set height of the image

            JFreeChart chart1 = ChartGenerator.createBarChart(data);
            // Convert JFreeChart to BufferedImage
            BufferedImage bufferedImage1 = chart1.createBufferedImage(width, height);
            // Convert BufferedImage to iTextPDF Image
            Image itextImage1 = new Image(ImageDataFactory.create(bufferedImage1, null));


            JFreeChart chart2 = ChartGenerator.top3SkillsPieChart();
            // Convert JFreeChart to BufferedImage
            BufferedImage bufferedImage2 = chart2.createBufferedImage(width, height);
            // Convert BufferedImage to iTextPDF Image
            Image itextImage2 = new Image(ImageDataFactory.create(bufferedImage2, null));


            JFreeChart chart3 = ChartGenerator.top3SkillsPeakTimePieChart();
            // Convert JFreeChart to BufferedImage
            BufferedImage bufferedImage3 = chart3.createBufferedImage(width, height);
            // Convert BufferedImage to iTextPDF Image
            Image itextImage3 = new Image(ImageDataFactory.create(bufferedImage3, null));


            JFreeChart chart4 = ChartGenerator.maxInterviewBarChart();
            // Convert JFreeChart to BufferedImage
            BufferedImage bufferedImage4 = chart4.createBufferedImage(width, height);
            // Convert BufferedImage to iTextPDF Image
            Image itextImage4 = new Image(ImageDataFactory.create(bufferedImage4, null));


            JFreeChart chart5 = ChartGenerator.minInterviewBarChart();
            // Convert JFreeChart to BufferedImage
            BufferedImage bufferedImage5 = chart5.createBufferedImage(width, height);
            // Convert BufferedImage to iTextPDF Image
            Image itextImage5 = new Image(ImageDataFactory.create(bufferedImage5, null));

            document.add(itextImage1);
            document.add(itextImage2);
            document.add(itextImage3);
            document.add(itextImage4);
            document.add(itextImage5);
        }
    }
}