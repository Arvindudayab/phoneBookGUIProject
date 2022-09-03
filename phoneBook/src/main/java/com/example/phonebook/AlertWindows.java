package com.example.phonebook;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;

public class AlertWindows {

    public static void lengthError(String s) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Format Error");
        window.setResizable(true);

        Label message = new Label("Your entry, " + s + " is not a state abbreviation.");
        message.setFont(new Font("Arial", 15));

        Button closeButton = new Button("Ok");
        closeButton.setPrefSize(50,20);
        closeButton.setOnAction(e -> window.close());

        HBox layout = new HBox(20);
        layout.setPadding(new Insets(30, 10, 10, 10));
        layout.getChildren().addAll(message, closeButton);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout, 300, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}
