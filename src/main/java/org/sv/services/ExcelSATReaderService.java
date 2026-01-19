package org.sv.services;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sv.interfaces.IExcelReader;
import org.sv.models.ExcelSATModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelSATReaderService implements IExcelReader<ExcelSATModel> {
    /**
     * Constantes del lugar de las columnas en el reporte de MiAdminXML.
     */
    private static final int COL_UUID = 9;
    private static final int COL_ESTADO = 0;
    private static final int COL_CONCEPTO = 40;
    private static final int COL_RFC_EMISOR = 11;
    private static final int COL_RFC_RECEPTOR = 15;
    private static final int COL_TOTAL = 27;

    /**
     * Valida si el archivo proporcionado tiene un formato correcto conforme a los reportes de MiAdminXML.
     * @param file Archivo de Excel, reporte de MiAdminXML.
     * @return Verdadero si es válido, Falso en caso contrario.
     */
    @Override
    public boolean soporta(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);

            return header.getCell(0).getStringCellValue().equalsIgnoreCase("Estado SAT")
                    && header.getCell(1).getStringCellValue().equalsIgnoreCase("Version");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lee el archivo proporcionado y crea una lista de todas las facturas presentes en el reporte de MiAdminXML.
     * @param file Archivo de Excel, reporte de MiAdminXML.
     * @return Lista de facturas que estan en el reporte.
     */
    @Override
    public List<ExcelSATModel> leer(File file) {
        List<ExcelSATModel> facturas = new ArrayList<>();
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
                    ExcelSATModel factura = new ExcelSATModel(
                            uuid,
                            formatter.formatCellValue(fila.getCell(COL_ESTADO)),
                            formatter.formatCellValue(fila.getCell(COL_CONCEPTO)),
                            formatter.formatCellValue(fila.getCell(COL_RFC_EMISOR)),
                            formatter.formatCellValue(fila.getCell(COL_RFC_RECEPTOR)),
                            Integer.parseInt(formatter.formatCellValue(fila.getCell(COL_TOTAL)))
                    );

                    facturas.add(factura);
                } catch (NumberFormatException nfe) {
                    throw new RuntimeException("Error en fila " + (i + 1) + ": Dato de numeros inválido.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo Excel de MiAdminXML.");
        }

        return facturas;
    }
}
