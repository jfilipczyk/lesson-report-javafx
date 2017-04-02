package com.jfilipczyk.lessonreport;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class FXMLController implements Initializable {

    public static final String DEFAULT_FILENAME = "report.csv";
    
    @FXML
    private Stage stage;
    
    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TextField calendarUrl;
    
    @FXML
    private Button submit;
    
    @FXML
    private Label successMessage;

    @FXML
    private Label errorMessage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValidationSupport validationSupport = new ValidationSupport();
        Arrays.asList(calendarUrl, dateFrom, dateTo).forEach(c -> {
            validationSupport.registerValidator(c, Validator.createEmptyValidator("Field is required"));
        });
        validationSupport.validationResultProperty().addListener((o, oldValue, newValue) -> {
            boolean hasErrors = !newValue.getMessages().isEmpty();
            submit.disableProperty().set(hasErrors);
        });
    }
    
    @FXML
    void handleSubmit(ActionEvent event) {   
        File file = openSaveFileDialog();
        if (file == null) {
            return;
        }
        startCreateReportTask(
            file.getAbsolutePath(),
            calendarUrl.getText(),
            dateFrom.getValue().atTime(0, 0, 0),
            dateTo.getValue().atTime(23, 59, 59)
        );
    }
    
    private File openSaveFileDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose destination file");
        fileChooser.setInitialFileName(DEFAULT_FILENAME);
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.csv"));
        return fileChooser.showSaveDialog(stage);
    }
    
    private void startCreateReportTask(String filepath, String calendarUrl, LocalDateTime dateFrom, LocalDateTime dateTo) {
        CreateReportTask task = new CreateReportTask(filepath, calendarUrl, dateFrom, dateTo);
        
        errorMessage.textProperty().bind(task.messageProperty());
        errorMessage.visibleProperty().bind(task.stateProperty().isEqualTo(Worker.State.FAILED));
        
        successMessage.textProperty().bind(task.messageProperty());
        successMessage.visibleProperty().bind(task.stateProperty().isEqualTo(Worker.State.SUCCEEDED));
        
        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
    }
}
