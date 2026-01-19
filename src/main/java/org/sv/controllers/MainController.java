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
import org.sv.services.ExcelExcelenciaReaderService;
import org.sv.services.ExcelSATReaderService;

import java.io.File;
import java.io.IOException;

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

    @FXML
    public void initialize() {
        configurarDragAndDrop(leftPanel, leftLabel, satReader);
        configurarDragAndDrop(rightPanel, rightLabel, excelenciaReader);
    }

    @FXML
    private void configurarDragAndDrop(VBox panel, Label label, IExcelReader<?> reader) {
        panel.setOnDragOver(event -> {
            panel.getStyleClass().add("hover");
            Dragboard db = event.getDragboard();
            if (db.hasFiles() && esXLSX(db.getFiles().getFirst())) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        panel.setOnDragDropped(event -> {
            File file = event.getDragboard().getFiles().getFirst();

            if (!reader.soporta(file)) {
                showTemporaryMessage(label, panel, "Formato inválido, \nIntroduce uno válido.");
                return;
            }

            panel.getStyleClass().add("loaded");
            label.setText(file.getName());
            event.setDropCompleted(true);
        });
    }

    @FXML
    private void onCompare() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/sv/result.fxml")
            );
            Scene scene = new Scene(loader.load());

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

    private boolean esXLSX(File file) {
        return file.getName().toLowerCase().endsWith(".xlsx");
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
