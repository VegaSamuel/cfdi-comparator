package org.sv.services;

import org.sv.models.ExcelExcelenciaModel;
import org.sv.models.ExcelSATModel;
import org.sv.models.FacturaModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelComparadorService {

    public List<FacturaModel> obtenerFacturasTodas(List<ExcelSATModel> facturasSAT, List<ExcelExcelenciaModel> facturasExcelencia) {
        Map<String, FacturaModel> mapa = new HashMap<>();

        for (ExcelSATModel sat : facturasSAT) {
            String uuid = sat.getUuid().toLowerCase();

            FacturaModel factura = new FacturaModel(
                    uuid,
                    "",
                    sat.getRfcEmisor(),
                    sat.getRfcReceptor(),
                    LocalDate.now(),
                    sat.getTotal()
            );

            mapa.put(uuid, factura);
        }

        for (ExcelExcelenciaModel excelencia : facturasExcelencia) {
            String uuid = excelencia.getUuid().toLowerCase();

            FacturaModel factura = mapa.get(uuid);
            if (factura != null) {
                factura.setConcepto(excelencia.getConcepto());
                factura.setFecha(excelencia.getFecha());
                factura.addIglesiaALista(excelencia.getIglesia());
            }
        }

        return new ArrayList<>(mapa.values());
    }
}
