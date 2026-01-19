package org.sv.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.sv.models.FacturaModel;

import java.util.List;

public class ResultController {

    @FXML
    private Accordion accordion;

    public void setFacturas(List<FacturaModel> facturas) {
        accordion.getPanes().clear();

        for (FacturaModel factura: facturas) {
            accordion.getPanes().add(crearFacturaPane(factura));
        }
    }

    private TitledPane crearFacturaPane(FacturaModel factura) {
        String titulo = String.format(
                "UUID: %s | Usada por %d iglesias",
                factura.getUuid(),
                factura.getCantidadIglesias()
        );

        VBox contenido = new VBox(8);
        contenido.getChildren().addAll(
                new Label("RFC Emisor: " + factura.getRfcEmisor()),
                new Label("RFC Receptor: " + factura.getRfcReceptor()),
                new Label("Fecha: " + factura.getFecha()),
                new Label("Total: $" + factura.getTotal()),
                new Label("Iglesias:")
        );

        for (String iglesia: factura.getIglesias()) {
            contenido.getChildren().add(new Label("• " + iglesia));
        }

        return new TitledPane(titulo, contenido);
    }
}
