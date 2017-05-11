package com.bugfullabs.pharmacydb.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;

public class LabeledBox extends HBox {

    public LabeledBox(String text, Node node) {
        super(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(5));
        Label label = new Label(text + ": ");
        label.setStyle("-fx-font-size: 1.1em");
        getChildren().addAll(label, node);
    }

}
