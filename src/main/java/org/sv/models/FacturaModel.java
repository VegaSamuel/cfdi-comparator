package org.sv.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacturaModel {
    private String uuid;
    private String concepto;
    private String rfcEmisor;
    private String rfcReceptor;
    private LocalDate fecha;
    private double total;
    private List<String> iglesias;

    /**
     * Constructor por omision.
     */
    public FacturaModel() {}

    /**
     * Constructor completo para una factura.
     * @param uuid ID de la factura.
     * @param concepto Concepto de la factura.
     * @param rfcEmisor RFC del emisor.
     * @param rfcReceptor RFC del receptor.
     * @param fecha Fecha en que se realizó la factura.
     * @param total Total de la factura.
     * @param iglesias Lista de iglesias que la usaron.
     */
    public FacturaModel(String uuid, String concepto, String rfcEmisor, String rfcReceptor, LocalDate fecha, double total, List<String> iglesias) {
        this.uuid = uuid;
        this.concepto = concepto;
        this.rfcEmisor = rfcEmisor;
        this.rfcReceptor = rfcReceptor;
        this.fecha = fecha;
        this.total = total;
        this.iglesias = iglesias;
    }

    /**
     * Constructor para facturas sin lista de iglesias que la usaron.
     * @param uuid ID de la factura.
     * @param concepto Concepto de la factura.
     * @param rfcEmisor RFC del emisor.
     * @param rfcReceptor RFC del receptor.
     * @param fecha Fecha en que se realizó la factura.
     * @param total Total de la factura.
     */
    public FacturaModel(String uuid, String concepto, String rfcEmisor, String rfcReceptor, LocalDate fecha, double total) {
        this.uuid = uuid;
        this.concepto = concepto;
        this.rfcEmisor = rfcEmisor;
        this.rfcReceptor = rfcReceptor;
        this.fecha = fecha;
        this.total = total;
        this.iglesias = new ArrayList<>();
    }

    /**
     * Regresa la cantidad de iglesias que usaron la factura.
     * @return Cantidad de iglesias que usaron la factura.
     */
    public int getCantidadIglesias() {
        return iglesias.size();
    }

    public void addIglesiaALista(String iglesia) {
        this.iglesias.add(iglesia);
    }

    // Getters & Setters
    public String getUuid() {
        return uuid;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<String> getIglesias() {
        return iglesias;
    }

    public void setIglesias(List<String> iglesias) {
        this.iglesias = iglesias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FacturaModel that = (FacturaModel) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUuid());
    }

    @Override
    public String toString() {
        return "FacturaModel{" +
                "uuid='" + uuid + '\'' +
                ", concepto='" + concepto + '\'' +
                ", rfcEmisor='" + rfcEmisor + '\'' +
                ", rfcReceptor='" + rfcReceptor + '\'' +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                ", iglesias=" + iglesias +
                '}';
    }
}
