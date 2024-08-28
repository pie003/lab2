/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Double.NaN;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author User
 */
public class Xlsx {
    
    public HashMap<Integer, ArrayList<Object>> readFromFile(int sheetIndex, String filepath) {
        
        HashMap<Integer, ArrayList<Object>> map = new HashMap<>();
        try {
            FileInputStream file = new FileInputStream(new File(filepath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            int columnsCount = sheet.getRow(0).getLastCellNum();
            for (int i = 0; i < columnsCount; i++) {
                ArrayList<Object> columnData = new ArrayList<>();
                for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                    XSSFRow row = sheet.getRow(j);
                    if (row != null) {
                        XSSFCell cell = row.getCell(i);
                        if (cell !=null) {
                            switch (cell.getCellType()) {
                                case NUMERIC -> columnData.add(cell.getNumericCellValue());
                                case STRING -> columnData.add(cell.getStringCellValue());
                            }
                        }
                    }
                }
                map.put(i, columnData);
            }
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    return map;
    }

    public void writeToExcelSheet(String filePath, String[] headers, Object[][] data, String sheetName) {
        try {
            XSSFWorkbook workbook;
            File file = new File(filePath);

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                }
            } else {
                workbook = new XSSFWorkbook();
            }

            XSSFSheet sheet = workbook.createSheet(sheetName);

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            for (int i = 0; i < data.length; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < data[i].length; j++) {
                    XSSFCell cell = row.createCell(j);
                    if (data[i][j] instanceof String string) {
                        cell.setCellValue(string);
                    } else if (data[i][j] instanceof Double value) {
                        if (!Double.isNaN(value)) {
                            cell.setCellValue(value);
                        } else {
                            cell.setCellValue("-");
                        }
                    } else {
                        cell.setBlank();
                    }
                }
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
    


