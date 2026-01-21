package org.sv.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.sv.interfaces.IExcelReader;
import org.sv.models.ExcelExcelenciaModel;
import org.sv.models.ExcelSATModel;
import org.sv.models.FacturaModel;
import org.sv.services.ExcelComparadorService;
import org.sv.services.ExcelExcelenciaReaderService;
import org.sv.services.ExcelSATReaderService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class MainController {
    @FXML
    private VBox leftPanel;

    @FXML
    private VBox rightPanel;

    @FXML
    private Label leftLabel;

    @FXML
    private Label rightLabel;

    @FXML
    private Button compareButton;

    private final IExcelReader<ExcelSATModel> satReader = new ExcelSATReaderService();
    private final IExcelReader<ExcelExcelenciaModel> excelenciaReader = new ExcelExcelenciaReaderService();
    private final ExcelComparadorService comparador = new ExcelComparadorService();

    private File leftFile;
    private File rightFile;

    @FXML
    public void initialize() {
        configurarDragAndDrop(leftPanel, leftLabel, satReader, file -> leftFile = file);
        configurarDragAndDrop(rightPanel, rightLabel, excelenciaReader, file -> rightFile = file);
    }

    /**
     * Configura paneles Drag & Drop dependiendo de como el usuario interactue con ellos.
     * @param panel Panel al que configurar.
     * @param label Etiqueta dentro del panel para modificar.
     * @param reader Lector de Excel correspondiente.
     * @param fileSetter Objeto para definir el archivo correspondiente.
     */
    @FXML
    private void configurarDragAndDrop(VBox panel, Label label, IExcelReader<?> reader, Consumer<File> fileSetter) {
        // Cuando se arrastra algo encima del panel
        panel.setOnDragOver(event -> {
            panel.getStyleClass().add("hover");
            // Obtiene lo que esta dentro del panel
            Dragboard db = event.getDragboard();
            // Checa si tiene el archivo y si es excel
            if (db.hasFiles() && esXLSX(db.getFiles().getFirst())) {
                // Acepta la transferencia del archivo.
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        // Cuando el archivo
        panel.setOnDragDropped(event -> {
            File droppedFile = event.getDragboard().getFiles().getFirst();

            if (!reader.soporta(droppedFile)) {
                showTemporaryMessage(label, panel, "Formato inválido, \nIntroduce uno válido.");
                return;
            }

            panel.getStyleClass().add("loaded");
            label.setText(droppedFile.getName());
            fileSetter.accept(droppedFile);
            event.setDropCompleted(true);
        });
    }

    @FXML
    private void onCompare() {
        if (estanAmbosXLSX()) {
            List<ExcelSATModel> sat = satReader.leer(leftFile);
            List<ExcelExcelenciaModel> excelencia = excelenciaReader.leer(rightFile);

            List<FacturaModel> resultado = comparador.obtenerFacturasTodas(sat, excelencia);

            abrirVentanaResultados(resultado);
        }
    }

    private boolean esXLSX(File file) {
        return file.getName().toLowerCase().endsWith(".xlsx");
    }

    private boolean estanAmbosXLSX() {
        if (leftFile == null && rightFile == null) {
            return false;
        } else if (rightFile == null) {
            return false;
        } else if (leftFile == null) {
            return false;
        } else {
            return true;
        }
    }

    private void abrirVentanaResultados(List<FacturaModel> facturas) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/sv/result.fxml")
            );
            Scene scene = new Scene(loader.load());

            ResultController result = loader.getController();
            result.setFacturas(facturas);

            Stage stage = new Stage();
            stage.setTitle("Resultado de comparación");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showTemporaryMessage(Label label, VBox panel, String message) {
        String originalText = label.getText();

        label.setText(message);
        panel.getStyleClass().add("drop-panel-error");

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            label.setText(originalText);
            panel.getStyleClass().remove("drop-panel-error");
        });
        pause.play();
    }
}
