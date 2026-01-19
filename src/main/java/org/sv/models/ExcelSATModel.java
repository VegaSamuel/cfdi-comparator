package org.sv.models;

import java.util.Objects;

public class ExcelSATModel {
    private String uuid;
    private String estado;
    private String concepto;
    private String rfcEmisor;
    private String rfcReceptor;
    private double total;

    /**
     * Constructor por omision.
     */
    public ExcelSATModel() {}

    /**
     * Constructor completo para una factura del SAT.
     * @param uuid ID de la factura.
     * @param estado Estado de la factura.
     * @param concepto Concepto de la factura.
     * @param rfcEmisor RFC del emisor de la factura.
     * @param rfcReceptor RFC del receptor de la factura.
     * @param total Total de la factura.
     */
    public ExcelSATModel(String uuid, String estado, String concepto, String rfcEmisor, String rfcReceptor, double total) {
        this.uuid = uuid;
        this.estado = estado;
        this.concepto = concepto;
        this.rfcEmisor = rfcEmisor;
        this.rfcReceptor = rfcReceptor;
        this.total = total;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getRfcEmisor() {
        return rfcEmisor;
    }

    public void setRfcEmisor(String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }

    public String getRfcReceptor() {
        return rfcReceptor;
    }

    public void setRfcReceptor(String rfcReceptor) {
        this.rfcReceptor = rfcReceptor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExcelSATModel that = (ExcelSATModel) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUuid());
    }

    @Override
    public String toString() {
        return "ExcelSATModel{" +
                "uuid='" + uuid + '\'' +
                ", estado='" + estado + '\'' +
                ", concepto='" + concepto + '\'' +
                ", rfcEmisor='" + rfcEmisor + '\'' +
                ", rfcReceptor='" + rfcReceptor + '\'' +
                ", total=" + total +
                '}';
    }
}
