package org.sv.services;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sv.interfaces.IExcelReader;
import org.sv.models.ExcelExcelenciaModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelExcelenciaReaderService implements IExcelReader<ExcelExcelenciaModel> {
    private static final int COL_UUID = 11;
    private static final int COL_CONCEPTO = 6;
    private static final int COL_DESCRIPCION = 8;
    private static final int COL_TOTAL = 9;
    private static final int COL_IGLESIA = 0;

    /**
     * Valida si el archivo proporcionado cumple con el formato de un reporte de ExcelenciaHoy.
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
     * @param file Archivo de Excel, reporte de ExcelenciaHoy.
     * @return Una lista de facturas que esten en el reporte.
     */
    @Override
    public List<ExcelExcelenciaModel> leer(File file) {
        List<ExcelExcelenciaModel> facturas = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row fila = sheet.getRow(i);
                if (fila == null) continue;

                String uuid = formatter.formatCellValue(fila.getCell(COL_UUID));
                if (uuid.isBlank()) continue;

                try {
                    ExcelExcelenciaModel factura = new ExcelExcelenciaModel(
                            uuid,
                            formatter.formatCellValue(fila.getCell(COL_CONCEPTO)),
                            formatter.formatCellValue(fila.getCell(COL_DESCRIPCION)),
                            Integer.parseInt(formatter.formatCellValue(fila.getCell(COL_TOTAL))),
                            formatter.formatCellValue(fila.getCell(COL_IGLESIA))
                    );

                    facturas.add(factura);
                } catch (NumberFormatException nfe) {
                    throw new RuntimeException("Error en fila " + (i + 1) + ": Dato de numeros inválido.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo Excel Excelencia");
        }

        return facturas;
    }
}
