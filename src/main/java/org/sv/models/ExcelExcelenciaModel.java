package org.sv.models;

import java.util.Objects;

public class ExcelExcelenciaModel {
    String uuid;
    String concepto;
    String descripcion;
    double total;
    String iglesia;

    /**
     * Constructor por omision.
     */
    public ExcelExcelenciaModel() {}

    /**
     * Constructor completo para un Excel de ExceleciaHoy.
     * @param uuid ID de la factura.
     * @param concepto Concepto de la factura.
     * @param descripcion Descripcion de la factura.
     * @param total Total de la factura.
     * @param iglesia Iglesia que uso la factura.
     */
    public ExcelExcelenciaModel(String uuid, String concepto, String descripcion, double total, String iglesia) {
        this.uuid = uuid;
        this.concepto = concepto;
        this.descripcion = descripcion;
        this.total = total;
        this.iglesia = iglesia;
    }

    //Getters & Setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getIglesia() {
        return iglesia;
    }

    public void setIglesia(String iglesia) {
        this.iglesia = iglesia;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExcelExcelenciaModel that = (ExcelExcelenciaModel) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUuid());
    }

    @Override
    public String toString() {
        return "ExcelExcelenciaModel{" +
                "uuid='" + uuid + '\'' +
                ", concepto='" + concepto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", total=" + total + '\'' +
                ", iglesia=" + iglesia +
                '}';
    }
}
