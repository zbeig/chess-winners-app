package com.beigz.chesswinners;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;
import com.beigz.chesswinners.util.ExcelReader;
import com.beigz.chesswinners.util.ExcelWriter;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class EventController {

    @FXML
    private Button acceptBtn;

    private Text actionStatus;

    private Hyperlink userGuideLink;

    private Hyperlink outputFilePathLink;

    private File selectedFile;

    private TextFlow flow;

    private HostServices hostServices;

    private String evaluationMode = AppConstants.DEFAULT_MODE;

    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }


    @FXML
    private void handleButtonAction(ActionEvent event) {
        String clickedBtnId = ((Control) event.getSource()).getId();

        if (clickedBtnId.equalsIgnoreCase("acceptBtn")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(AppConstants.WINDOW_TITLE);
            alert.setHeaderText("Thank you for accepting the Terms & Conditions, you can use the software now!");
            alert.setContentText(null);
            alert.showAndWait();

            try {
                Stage mainStage = new Stage();
                mainStage.setTitle(AppConstants.WINDOW_TITLE);
                mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/app-icon.png")));

                // Window label
                Label label = new Label("Chess Winners");
                label.setTextFill(Color.DARKBLUE);
                label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
                HBox labelHb = new HBox();
                labelHb.setAlignment(Pos.CENTER);
                labelHb.getChildren().add(label);


                // Buttons
                Button btn1 = new Button("Select a file to process...");
                btn1.setFont(Font.font("Calibri", FontWeight.BOLD, 14));

                btn1.setOnAction(new SingleFcButtonListener());
                HBox buttonHb1 = new HBox(10);
                buttonHb1.setAlignment(Pos.CENTER);
                buttonHb1.getChildren().addAll(btn1);

                Button btn2 = new Button("Generate Winners List");
                btn2.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
                btn2.setDefaultButton(true);
                btn2.setOnAction(new SubmitFileListener());
                HBox buttonHb2 = new HBox(10);
                buttonHb2.setAlignment(Pos.CENTER);
                buttonHb2.getChildren().addAll(btn2);

                // Status message text
                actionStatus = new Text();
                actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));

                // hyperlink
                userGuideLink = new Hyperlink("Program Usage Guide");
                userGuideLink.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
                userGuideLink.setBorder(Border.EMPTY);
                userGuideLink.setPadding(new Insets(4, 0, 4, 0));

                userGuideLink.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        hostServices.showDocument("README.txt");
                    }
                });

                flow = new TextFlow();

                // Group
                ToggleGroup group = new ToggleGroup();

                group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                    @Override
                    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                        // Has selection.
                        if (group.getSelectedToggle() != null) {
                            RadioButton button = (RadioButton) group.getSelectedToggle();
                            evaluationMode = button.getText();
                        }
                    }
                });

                // Radio 1: Mode 1
                RadioButton btnMode1 = new RadioButton("Mode 1");
                btnMode1.setToggleGroup(group);
                btnMode1.setSelected(true);
                btnMode1.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
                btnMode1.setTooltip(new Tooltip("Rating and Age category has\nnormal upper and lower bounds"));

                // Radio 2: Mode 2.
                RadioButton btnMode2 = new RadioButton("Mode 2");
                btnMode2.setToggleGroup(group);
                btnMode2.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
                btnMode2.setTooltip(new Tooltip("Rating and Age category has\nnormal upper bound but\nlower bound is considered as 0"));

                HBox hbox = new HBox();
                hbox.setPadding(new Insets(10));
                hbox.setSpacing(5);
                hbox.getChildren().addAll(btnMode1, btnMode2);
                hbox.setAlignment(Pos.CENTER);

                // Vbox
                VBox vbox = new VBox(30);
                vbox.setPadding(new Insets(25, 25, 25, 25));
                vbox.getChildren().addAll(labelHb, buttonHb1, hbox, buttonHb2, actionStatus, flow, userGuideLink);

                // Scene
                Scene scene = new Scene(vbox, 550, 420); // w x h
                mainStage.setScene(scene);
                mainStage.show();

                // get a handle to the stage
                Stage primaryStage = (Stage) acceptBtn.getScene().getWindow();
                // do what you have to do
                primaryStage.close();
            } catch (Exception e) {
                showError(e);
            }
        } else {
            Platform.exit();
        }
    }

    private void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            actionStatus.setFill(Color.DARKBLUE);
            actionStatus.setText("Excel File selected : " + selectedFile.getName());
        } else {
            flow.getChildren().clear();
            actionStatus.setFill(Color.DARKBLUE);
            actionStatus.setText("Excel file selection cancelled");
        }
    }

    private void processSelectedFile() {

        try {
            if (selectedFile != null && selectedFile.exists()) {

                ExcelReader reader = new ExcelReader(selectedFile.getAbsolutePath());
                ExcelWriter writer = new ExcelWriter();
                WinningListProcessor processor = new WinningListProcessor(evaluationMode);
                List<CategoryPrize> categoryPrizes = reader.readCategoryAndPrizes();
                List<Player> players = reader.readFinalRankList();
                processor.processWinnersList(categoryPrizes, players);
                String outputFilePath = writer.write2Excel(players, categoryPrizes);

                int index = outputFilePath.lastIndexOf("\\");
                String fileName = outputFilePath.substring(index + 1);

                if (!outputFilePath.isEmpty()) {
                    flow.getChildren().clear();
                    outputFilePathLink = buildOutputFilePathLink(outputFilePath);
                    Text text = new Text(fileName + " successfully generated ");
                    text.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
                    flow.getChildren().addAll(text, outputFilePathLink);
                }

            } else {
                flow.getChildren().clear();
                actionStatus.setFill(Color.FIREBRICK);
                actionStatus.setText("No Excel File selected or File does not exist, please try again.");
            }
        } catch (InvalidFormatException e) {
            showError(e);
        } catch (IOException e) {
            showError(e);
        } catch (Exception e) {
            showError(e);
        }
    }

    private Hyperlink buildOutputFilePathLink(String outputFilePath) {
        Hyperlink link = new Hyperlink("here");
        link.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
        link.setBorder(Border.EMPTY);
        link.setPadding(new Insets(4, 0, 4, 0));
        link.setOnAction(event -> {
            hostServices.showDocument(outputFilePath);
        });

        return link;
    }

    private void showError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error alert");
        if (e.getMessage().contains("being used")) {
            alert.setHeaderText("Input file is open, please close the file and then try again");
        } else if (e.getMessage().contains("sort")) {
            alert.setHeaderText("There is no sheet with name as 'sort' in the uploaded excel file");
        } else if (e.getMessage().contains("Ranklist")) {
            alert.setHeaderText("There is no sheet with name as 'Ranklist' in the uploaded excel file");
        } else {
            alert.setHeaderText(e.getMessage());
        }

        VBox dialogPaneContent = new VBox();

        Label label = new Label("Stack Trace:");

        String stackTrace = this.getStackTrace(e);
        TextArea textArea = new TextArea();
        textArea.setText(stackTrace);

        dialogPaneContent.getChildren().addAll(label, textArea);

        // Set content for Dialog Pane
        alert.getDialogPane().setContent(dialogPaneContent);

        alert.showAndWait();
    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String s = sw.toString();
        return s;
    }

    private class SingleFcButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            showSingleFileChooser();
        }
    }

    private class SubmitFileListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            processSelectedFile();
        }
    }

}
