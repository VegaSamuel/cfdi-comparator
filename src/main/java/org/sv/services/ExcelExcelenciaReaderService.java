package org.sv.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sv.interfaces.IExcelReader;
import org.sv.models.ExcelExcelenciaModel;
import org.sv.utils.ReaderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelExcelenciaReaderService implements IExcelReader<ExcelExcelenciaModel> {
    private static final int COL_UUID = 11;
    private static final int COL_CONCEPTO = 6;
    private static final int COL_DESCRIPCION = 8;
    private static final int COL_FECHA = 3;
    private static final int COL_TOTAL = 9;
    private static final int COL_IGLESIA = 0;

    /**
     * Valida si el archivo proporcionado cumple con el formato de un reporte de ExcelenciaHoy.
     *
     * @param file Archivo de Excel, reporte de ExcelenciaHoy.
     * @return Verdadero si es válido, Falso en caso contrario.
     */
    @Override
    public boolean soporta(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);

            return header.getCell(0).getStringCellValue().equalsIgnoreCase("ENSD:LocalidadDescripcion")
                    && header.getCell(1).getStringCellValue().equalsIgnoreCase("ENSD:NoLocalidad");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lee el archivo proporcionado y crea una lista de todas las facturas presentes en el reporte de ExcelenciaHoy.
     *
     * @param file Archivo de Excel, reporte de ExcelenciaHoy.
     * @return Una lista de facturas que esten en el reporte.
     */
    @Override
    public List<ExcelExcelenciaModel> leer(File file) {
        List<ExcelExcelenciaModel> facturas = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        ReaderUtils utils = new ReaderUtils();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row fila = sheet.getRow(i);
                if (fila == null) continue;

                String uuid = formatter.formatCellValue(fila.getCell(COL_UUID));
                if (uuid.isBlank()) continue;

                try {
                    ExcelExcelenciaModel factura = new ExcelExcelenciaModel(
                            uuid,
                            formatter.formatCellValue(fila.getCell(COL_CONCEPTO)),
                            formatter.formatCellValue(fila.getCell(COL_DESCRIPCION)),
                            utils.leerFecha(fila.getCell(COL_FECHA)),
                            utils.leerDouble(fila.getCell(COL_TOTAL)),
                            formatter.formatCellValue(fila.getCell(COL_IGLESIA))
                    );

                    facturas.add(factura);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    throw new RuntimeException("Error en fila " + (i + 1) + ": Dato de numeros inválido.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error leyendo Excel Excelencia");
        }

        return facturas;
    }

//    private LocalDate leerFecha(Cell cell) {
//        if (cell == null) return null;
//
//        try {
//            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
//                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            }
//
//            if (cell.getCellType() == CellType.STRING) {
//                String text = cell.getStringCellValue().trim();
//
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                return LocalDate.parse(text, formatter);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
}
