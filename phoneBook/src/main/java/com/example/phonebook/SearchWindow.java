package com.example.phonebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;

import java.io.*;

public class SearchWindow {

    static String fileName;
    public static void display() throws IOException {
        // fileName = "/Users/arvindudayabanu/Desktop/textInput/phonebook.txt";
        fileName = "phonebook.txt";

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Search Phonebook");
        window.setResizable(false);

        // top layout
        GridPane topLayout = new GridPane();
        topLayout.setPadding(new Insets(15, 15, 15, 15));
        topLayout.setVgap(10);
        topLayout.setHgap(10);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        GridPane.setConstraints(searchBar, 0, 0);

        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);

        topLayout.getChildren().addAll(searchBar, searchButton);

        // bottom layout
        StackPane bottomLayout = new StackPane();
        bottomLayout.setPadding(new Insets(15, 15, 15, 15));

        // ListView<String> bookOutput = new ListView<>();
        TableView bookOutput = new TableView();
        bookOutput.setEditable(false);
        bookOutput.setPrefHeight(400);
        bookOutput.setPrefWidth(650);

        TableColumn<phoneBookData, String> col1 = new TableColumn<>("First Name");
        col1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col1.setMinWidth(100);

        TableColumn<phoneBookData, String> col2 = new TableColumn<>("Last Name");
        col2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col2.setMinWidth(125);

        TableColumn<phoneBookData, String> col3 = new TableColumn<>("Phone Number");
        col3.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col3.setMinWidth(100);

        TableColumn<phoneBookData, String> col4 = new TableColumn<>("Address");
        col4.setCellValueFactory(new PropertyValueFactory<>("address"));
        col4.setMinWidth(150);

        TableColumn<phoneBookData, String> col5 = new TableColumn<>("City");
        col5.setCellValueFactory(new PropertyValueFactory<>("city"));
        col5.setMinWidth(100);

        TableColumn<phoneBookData, String> col6 = new TableColumn<>("State");
        col6.setCellValueFactory(new PropertyValueFactory<>("state"));
        col6.setMinWidth(50);

        TableColumn<phoneBookData, String> col7 = new TableColumn<>("Zip Code");
        col7.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        col7.setMinWidth(75);


        bookOutput.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);

        // reader start
        BufferedReader lineCounter = new BufferedReader(new FileReader(fileName));

        String line;
        int lineCount = -1;
        while ((line = lineCounter.readLine()) != null) {
            lineCount++;
        }

        lineCounter.close();

        phoneBookData[] pbArr = new phoneBookData[lineCount];

        BufferedReader linerReader = new BufferedReader(new FileReader(fileName));

        String bookLine;
        int entryCount = -1;
        String [] components = null;
        while ((bookLine = linerReader.readLine()) != null) {
            if (entryCount == -1) {
                entryCount++;
                continue;
            }

            components = bookLine.split("/");

            pbArr[entryCount] = new phoneBookData(components[0], components[1], components[2], components[3],
                    components[4], components[5], components[6]);

            entryCount++;
        }

        linerReader.close();
        // reader end

        /*for (int nCount = 0; nCount < lineCount; nCount++) {
            phoneBookData pb = pbArr[nCount];
            bookOutput.getItems().add(pb.firstName + ", " + pb.lastName + ", " + pb.phoneNumber + ", " +
                    pb.address + ", " + pb.city + ", " + pb.state + ", " + pb.zipCode);
        }*/

        ObservableList<phoneBookData> dataList = FXCollections.observableArrayList();

        for (int nCount = 0; nCount < lineCount; nCount++) {
            phoneBookData pb = pbArr[nCount];
            bookOutput.getItems().add(
                    new phoneBookData(pb.firstName, pb.lastName, pb.phoneNumber, pb.address, pb.city,
                            pb.state, pb.zipCode));

            dataList.add(
                    new phoneBookData(pb.firstName, pb.lastName, pb.phoneNumber, pb.address, pb.city,
                            pb.state, pb.zipCode));
        }

        // filtered search
        FilteredList<phoneBookData> filteredList = new FilteredList<>(dataList);

        searchBar.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> searchButton.fire();
            }
        });

        searchButton.setOnAction(e -> filteredList.setPredicate(phoneBookData -> {
            if (searchBar.getText() == null || searchBar.getText().isEmpty()) {
                return true;
            }

            String keyword = searchBar.getText().toLowerCase();

            if (phoneBookData.getFirstName().toLowerCase().indexOf(keyword) != -1) {
                return true;
            }
            else if (phoneBookData.getLastName().toLowerCase().indexOf(keyword) != -1) {
                return true;
            }
            else if (phoneBookData.getAddress().toLowerCase().indexOf(keyword) != -1) {
                return true;
            }
            else if (phoneBookData.getCity().toLowerCase().indexOf(keyword) != -1) {
                return true;
            }
            else if (phoneBookData.getState().toLowerCase().indexOf(keyword) != -1) {
                return true;
            }
            else if (phoneBookData.getZipCode().indexOf(keyword) != -1) {
                return true;
            }
            else {
                return false;
            }
        }));

        SortedList<phoneBookData> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(bookOutput.comparatorProperty());
        bookOutput.setItems(sortedList);

        bottomLayout.getChildren().addAll(bookOutput);

        BorderPane layout = new BorderPane();
        layout.setTop(topLayout);
        layout.setBottom(bottomLayout);

        Scene scene = new Scene(layout, 784, 500);
        window.setScene(scene);
        window.showAndWait();
    }
}
