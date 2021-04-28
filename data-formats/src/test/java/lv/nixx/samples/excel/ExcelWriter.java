package lv.nixx.samples.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;

public class ExcelWriter {

    @Test
    public void writeDataToExcelFile() throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        HSSFFont font= workbook.createFont();
        font.setBold(true);
        font.setFontName("Arial");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        Row headersRow = sheet.createRow(0);

        Cell cell0 = headersRow.createCell(0);
        cell0.setCellValue("Name");
        cell0.setCellStyle(cellStyle);

        Cell cell1 = headersRow.createCell(1);
        cell1.setCellValue("Amount");
        cell1.setCellStyle(cellStyle);

        Cell cell2 = headersRow.createCell(2);
        cell2.setCellValue("Count");
        cell2.setCellStyle(cellStyle);

        Cell cell3 = headersRow.createCell(3);
        cell3.setCellValue("Total");
        cell3.setCellStyle(cellStyle);

        for (int i = 1; i <= 5; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue("Name" + i);
            row.createCell(1).setCellValue(i * 100.0);
            row.createCell(2).setCellValue(i);
            int pos = i + 1;
            row.createCell(3).setCellFormula("B" + pos + "+C" + pos);
        }

        Row totalRow = sheet.createRow(6);
        totalRow.setRowStyle(cellStyle);
        Cell cell = totalRow.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total");

        totalRow.createCell(3).setCellFormula("SUM(D2:D6)");

        FileOutputStream outputStream = new FileOutputStream("./src/test/resources/created_table.xls");
        workbook.write(outputStream);
        workbook.close();
    }


}