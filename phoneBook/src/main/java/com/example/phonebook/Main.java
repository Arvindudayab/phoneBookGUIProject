package com.example.phonebook;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;

import java.util.*;

import java.io.*;

public class Main extends Application {

    Stage window;
    Scene scene;
    String fileName;
    FileWriter fw;
    BufferedWriter writer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        // fileName = "/Users/arvindudayabanu/Desktop/textInput/phonebook.txt";
        fileName = "phonebook.txt";
        File myFile = new File(fileName);

        window = stage;
        window.setTitle("Phonebook Data");
        window.setOnCloseRequest(e -> {
            e.consume();
            try {
                writer.close();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            window.close();
        });

        if (myFile.exists()) {
            fw = new FileWriter(fileName, true);
            writer = new BufferedWriter(fw);
        }
        else {
            fw = new FileWriter(fileName);
            writer = new BufferedWriter(fw);
            writer.write("First Name/Last Name/Phone Number/Address/City/State/Zip Code");
            writer.newLine();
        }

        GridPane leftLayout = new GridPane();
        leftLayout.setPadding(new Insets(30, 10, 10, 35));
        leftLayout.setHgap(10);
        leftLayout.setVgap(50);

        // first name
        Label fNameLabel = new Label("First Name: ");
        GridPane.setConstraints(fNameLabel, 0, 0);

        TextField fNameInput = new TextField();
        fNameInput.setPromptText("Example: Arvind");
        GridPane.setConstraints(fNameInput, 1, 0);

        // last name
        Label lNameLabel = new Label("Last Name: ");
        GridPane.setConstraints(lNameLabel, 0, 1);

        TextField lNameInput = new TextField();
        lNameInput.setPromptText("Example: Udayabanu");
        GridPane.setConstraints(lNameInput, 1, 1);

        // phone number
        Label phoneNumberLabel = new Label("Phone Number: ");
        GridPane.setConstraints(phoneNumberLabel, 0, 2);

        TextField phoneNumberInput = new TextField();
        phoneNumberInput.setPromptText("Example: 5088738623");
        GridPane.setConstraints(phoneNumberInput, 1, 2);

        // address
        Label addressLabel = new Label("Address: ");
        GridPane.setConstraints(addressLabel, 0, 3);

        TextField addressInput = new TextField();
        addressInput.setPromptText("Example: 44 Neptune Dr.");
        GridPane.setConstraints(addressInput, 1, 3);

        leftLayout.getChildren().addAll(
                fNameLabel, fNameInput, lNameLabel, lNameInput, phoneNumberLabel, phoneNumberInput,
                addressLabel, addressInput);

        GridPane rightLayout = new GridPane();
        rightLayout.setPadding(new Insets(30, 75, 10, 10));
        rightLayout.setHgap(10);
        rightLayout.setVgap(50);

        // city
        Label cityLabel = new Label("City: ");
        GridPane.setConstraints(cityLabel, 0, 0);

        TextField cityInput = new TextField();
        cityInput.setPromptText("Example: Shrewsbury");
        GridPane.setConstraints(cityInput, 1, 0);

        // state
        Label stateLabel = new Label("State: ");
        GridPane.setConstraints(stateLabel, 0, 1);

        TextField stateInput = new TextField();
        stateInput.setPromptText("Example: MA");
        GridPane.setConstraints(stateInput, 1, 1);

        // zip
        Label zipLabel = new Label("Zip Code: ");
        GridPane.setConstraints(zipLabel, 0, 2);

        TextField zipInput = new TextField();
        zipInput.setPromptText("Example: 01545");
        GridPane.setConstraints(zipInput, 1, 2);

        LinkedList<TextField> inputBars = new LinkedList<>();
        inputBars.add(fNameInput);
        inputBars.add(lNameInput);
        inputBars.add(phoneNumberInput);
        inputBars.add(addressInput);
        inputBars.add(cityInput);
        inputBars.add(stateInput);
        inputBars.add(zipInput);

        // buttons
        HBox buttonSpace = new HBox();
        GridPane.setConstraints(buttonSpace, 1, 3);
        buttonSpace.setSpacing(25);

        Button saveButton = new Button("Save");
        saveButton.setDisable(true);

        Button searchButton = new Button("Search Phonebook");
        searchButton.setDisable(fileIsEmpty(fileName));

        buttonSpace.getChildren().addAll(saveButton, searchButton);

        // textfield data recognition
        for (TextField filledField : inputBars) {
            filledField.textProperty().addListener((v, oldValue, newValue) -> {
                boolean bFieldEmpty = false;
                int j = 0;
                for (TextField textField : inputBars) {
                    String fieldData = textField.getText();
                    if (fieldData.isEmpty()) {
                        bFieldEmpty = true;
                    }

                    if ((j == 2 || j == 6) && (fieldData.length() > 0)) {
                        if (!(checkIfInt(textField.getText()))) {
                            Platform.runLater(() -> {
                                textField.clear();
                            });
                        }
                    }
                    if ((j == 0 || j == 1 || j == 4 || j == 5) && (fieldData.length() > 0)) {
                        if (!(checkIfChar(textField.getText()))) {
                            Platform.runLater(() -> {
                                textField.clear();
                            });
                        }
                    }

                    j++;
                }
                saveButton.setDisable(bFieldEmpty);
            });
        }

        // button functions
        saveButton.setOnAction(e -> {
            saveData(inputBars);
            searchButton.setDisable(false);
        });

        searchButton.setOnAction(e -> {
            try {
                writer.flush();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                SearchWindow.display();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        rightLayout.getChildren().addAll(
                cityLabel, cityInput, stateLabel, stateInput, zipLabel, zipInput, buttonSpace);


        BorderPane layout = new BorderPane();
        layout.setLeft(leftLayout);
        layout.setRight(rightLayout);

        Scene scene = new Scene(layout, 800, 450);
        window.setScene(scene);
        window.show();
    }

    private void saveData(LinkedList<TextField> textList) {
        try {
            int i = 1;
            for (TextField textField : textList) {
                writer.write(textField.getText());
                if (i < 7) {
                    writer.write("/");
                }
                textField.setText("");
                i++;
            }
            writer.newLine();
            writer.flush();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean checkIfInt(String input) {
        try {
            double number = Double.parseDouble(input);
            return true;
        }
        catch (NumberFormatException ne) {
            Alert intAlert = new Alert(Alert.AlertType.ERROR);
            intAlert.setTitle("Format Error");
            intAlert.setContentText("Your entry, " + input + " is not in numeric format.");
            intAlert.showAndWait();
            return false;
        }
    }

    private boolean checkIfChar(String input) {
        try {
            double number = Double.parseDouble(input);
            Alert charAlert = new Alert(Alert.AlertType.ERROR);
            charAlert.setTitle("Format Error");
            charAlert.setContentText("Your entry " + input + " is not in alphabetic format.");
            charAlert.showAndWait();
            return false;
        }
        catch (NumberFormatException ne) {
            return true;
        }
    }

    private boolean fileIsEmpty(String aFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(aFile));
        if (reader.readLine() == null) {
            return true;
        }
        else {
            return false;
        }
    }
}