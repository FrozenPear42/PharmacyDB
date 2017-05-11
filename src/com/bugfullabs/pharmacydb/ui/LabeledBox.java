package com.bugfullabs.pharmacydb.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;

public class LabeledBox extends HBox {
    private String mText;
    private Label mLabel;

    public LabeledBox(String text, Node node) {
        super(10);
        setPadding(new Insets(10));
        mText = text;
        mLabel = new Label(mText + ": ");
        mLabel.setStyle("-fx-font-size: 1.2em; -fx-font-weight: bold");
        getChildren().addAll(mLabel, node);
    }

}
