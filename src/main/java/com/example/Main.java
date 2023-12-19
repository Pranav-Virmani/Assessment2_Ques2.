package com.example;

import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException
    {
        try {
            SpringApplication.run(InsertandParse.class, args);

            String filePath = "C:\\Users\\pranav.virmani\\Desktop\\Assignment2b\\src\\main\\resources\\Accolite.xlsx";
            List<ExcelData> dataList = new InsertandParse().parseExcel(filePath);

            new InsertandParse().insertData(dataList);




            PdfGenerator.generatePdf(dataList, "C:\\Users\\pranav.virmani\\Desktop\\Assignment2b\\src\\main\\resources\\Chart.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
