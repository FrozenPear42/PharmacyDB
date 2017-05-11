package com.bugfullabs.pharmacydb.main;

import com.bugfullabs.pharmacydb.DatabaseConnector;
import com.bugfullabs.pharmacydb.model.Medication;
import com.bugfullabs.pharmacydb.model.Transaction;
import com.bugfullabs.pharmacydb.ui.LabeledBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewTransactionWindow {

    private DatabaseConnector mConnector;

    public NewTransactionWindow(DatabaseConnector connector) {
        mConnector = connector;

        ObservableList<Medication> medicationsList = FXCollections.observableArrayList();
        HashMap<Medication, Integer> quantityMap = new HashMap<>();

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        Stage stage = new Stage();
        stage.setTitle("New Transaction");
        stage.setScene(new Scene(root, 600, 500));
        stage.show();

        Date date = new Date();
        Label dateLabel = new Label(new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss").format(date));
        root.getChildren().add(new LabeledBox("Date", dateLabel));

        ToggleGroup paymentMethod = new ToggleGroup();
        RadioButton visa = new RadioButton("VISA");
        RadioButton masterCard = new RadioButton("MasterCard");
        RadioButton cash = new RadioButton("Cash");
        visa.setToggleGroup(paymentMethod);
        masterCard.setToggleGroup(paymentMethod);
        cash.setToggleGroup(paymentMethod);
        visa.setSelected(true);
        VBox paymentMethodBox = new VBox(10, visa, masterCard, cash);
        root.getChildren().add(new LabeledBox("Payment method", paymentMethodBox));

        TableView<Medication> medicationTableView = new TableView<>(medicationsList);

        TableColumn<Medication, Medication> removeColumn = new TableColumn<>("X");
        removeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        removeColumn.setCellFactory(table -> new TableCell<Medication, Medication>() {
            @Override
            protected void updateItem(Medication item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Button button = new Button("X");
                    button.setOnAction(e -> medicationsList.remove(item));
                    setAlignment(Pos.CENTER);
                    setGraphic(button);
                } else {
                    setGraphic(null);
                }
            }
        });
        medicationTableView.getColumns().add(removeColumn);

        TableColumn<Medication, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(quantityMap.get(p.getValue()).toString()));
        medicationTableView.getColumns().add(quantityColumn);

        TableColumn<Medication, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getName()));
        medicationTableView.getColumns().add(nameColumn);

        TableColumn<Medication, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getAmount()));
        medicationTableView.getColumns().add(amountColumn);

        TableColumn<Medication, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getType()));
        medicationTableView.getColumns().add(typeColumn);

        root.getChildren().add(medicationTableView);

        Button addMedication = new Button("Add Medication");
        addMedication.setOnAction(e -> doSelectMedication((medication, quantity) -> {
            if (medication != null) {
                if (quantityMap.putIfAbsent(medication, quantity) != null)
                    quantityMap.replace(medication, quantity + quantityMap.get(medication));

                medicationsList.add(medication);
            }
        }));
        root.getChildren().add(addMedication);

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> stage.close());
        Button done = new Button("Done");
        done.setOnAction(e -> {
            String payment = "VISA";
            if (masterCard.isSelected())
                payment = "MC";
            else if (cash.isSelected())
                payment = "cash";

            if (medicationsList.isEmpty()) {
                Alert info = new Alert(Alert.AlertType.ERROR);
                info.setHeaderText(null);
                info.setContentText("Transaction is empty!");
                info.showAndWait();
                return;
            }

            int id = mConnector.addTransaction(new Transaction(medicationsList, quantityMap, new java.sql.Date(date.getTime()), payment));
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText(null);
            info.setContentText(String.format("Added transaction with ID: %d", id));
            info.showAndWait();
            stage.close();
        });
        HBox buttonsBox = new HBox(10, cancel, done);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setAlignment(Pos.CENTER);
        root.getChildren().add(buttonsBox);

    }

    private void doSelectMedication(MedicationSelectedListener listener) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        Stage stage = new Stage();
        stage.setTitle("Select Medication");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();

        ObservableList<Medication> medicationList = FXCollections.observableList(mConnector.getMedications());

        TableView<Medication> medicationTableView = new TableView<>(medicationList);

        TableColumn<Medication, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getName()));
        medicationTableView.getColumns().add(nameColumn);

        TableColumn<Medication, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getType()));
        medicationTableView.getColumns().add(typeColumn);

        TableColumn<Medication, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getAmount()));
        medicationTableView.getColumns().add(amountColumn);

        TableColumn<Medication, String> stockColumn = new TableColumn<>("In stock");
        stockColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(Double.toString(p.getValue().getStockAmount())));
        medicationTableView.getColumns().add(stockColumn);

        TableColumn<Medication, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(Double.toString(p.getValue().getStockPrice())));
        medicationTableView.getColumns().add(priceColumn);

        root.getChildren().add(medicationTableView);

        TextField quantity = new TextField("1");

        root.getChildren().add(new LabeledBox("Quantity", quantity));

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> stage.close());
        Button done = new Button("Done");
        done.setOnAction(e -> {
            Medication selectedMedication = medicationTableView.getSelectionModel().getSelectedItem();
            int q = Integer.parseInt(quantity.getText());
            if (selectedMedication == null)
                return;
            listener.onMedicationSelected(selectedMedication, q);
            stage.close();
        });
        HBox buttonsBox = new HBox(10, cancel, done);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setAlignment(Pos.CENTER);
        root.getChildren().add(buttonsBox);
    }

    interface MedicationSelectedListener {
        void onMedicationSelected(Medication medication, int quantity);
    }


}
