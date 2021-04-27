package lv.nixx.samples.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;

public class ExcelWriter {

    @Test
    public void writeDataToExcelFile() throws Exception {

        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("Name");
        row1.createCell(1).setCellValue("Amount");

        for (int i = 1; i <= 5; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue("Name" + i);
            row.createCell(1).setCellValue(i * 100.0);
        }

        String excelFilePath = "./src/test/resources/created_table.xls";
        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        workbook.close();


        workbook.close();
    }

}