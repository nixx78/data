package lv.nixx.samples.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.junit.jupiter.api.Test;

public class ExcelReader {

    @Test
    public void simpleFileReadTest() throws Exception {

        String excelFilePath = "./src/test/resources/simple_table.xls";
        FileInputStream inputStream = new FileInputStream(excelFilePath);

        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);

        for (Row nextRow : firstSheet) {
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                CellAddress address = cell.getAddress();


                CellType cellType = cell.getCellType();
                if (cellType.equals(CellType.STRING)) {
                    System.out.print(address + ":" + cell.getStringCellValue());
                } else if (cellType.equals(CellType.BOOLEAN)) {
                    System.out.print(address + ":" + cell.getBooleanCellValue());
                } else if (cellType.equals(CellType.NUMERIC)) {
                    System.out.print(address + ":" + cell.getNumericCellValue());
                }
                System.out.print(" - ");
            }
            System.out.println();
        }

        workbook.close();
        inputStream.close();
    }

}