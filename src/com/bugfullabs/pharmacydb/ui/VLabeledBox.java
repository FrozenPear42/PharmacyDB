package com.bugfullabs.pharmacydb.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VLabeledBox extends VBox {
    private String mText;
    private Label mLabel;

    public VLabeledBox(String text, Node node) {
        super(10);
        setPadding(new Insets(5));
        mText = text;
        mLabel = new Label(mText + ": ");
        mLabel.setStyle("-fx-font-size: 1.1em;");
        getChildren().addAll(mLabel, node);
    }

}
