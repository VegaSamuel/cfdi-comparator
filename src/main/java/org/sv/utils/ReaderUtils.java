package org.sv.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ReaderUtils {

    public LocalDate leerFecha(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            if (cell.getCellType() == CellType.STRING) {
                String text = cell.getStringCellValue().trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(text, formatter);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public double leerDouble(Cell cell) {
        if (cell == null) return 0;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            }

            if (cell.getCellType() == CellType.STRING) {
                String text = cell.getStringCellValue().replace("$", "").replace(",", "").trim();
                return Double.parseDouble(text);
            }
        } catch (Exception e) {
            throw new RuntimeException("Numero invalido: " + cell.toString(), e);
        }

        return 0;
    }
}
