package com.bugfullabs.pharmacydb.window;

import com.bugfullabs.pharmacydb.main.DatabaseConnector;
import com.bugfullabs.pharmacydb.model.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ListTransactionWindow {

    private DatabaseConnector mConnector;

    public ListTransactionWindow(DatabaseConnector connector) {
        mConnector = connector;

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        Stage stage = new Stage();
        stage.setTitle("List Transactions");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();

        ObservableList<Transaction> transactions = FXCollections.observableArrayList(connector.getTransactions());

        TableView<Transaction> transactionTableView = new TableView<>(transactions);

        TableColumn<Transaction, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(p -> new SimpleStringProperty(Integer.toString(p.getValue().getTransactionID())));
        transactionTableView.getColumns().add(idColumn);

        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(p -> new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd")
                .format(p.getValue().getDate())));
        transactionTableView.getColumns().add(dateColumn);

        TableColumn<Transaction, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(p -> new SimpleStringProperty(new DecimalFormat("#.00").format(p.getValue().getTotal())));
        transactionTableView.getColumns().add(totalColumn);

        TableColumn<Transaction, String> paymentColumn = new TableColumn<>("Payment");
        paymentColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPaymentMethod()));
        transactionTableView.getColumns().add(paymentColumn);

        TableColumn<Transaction, String> medicationsColumn = new TableColumn<>("Medications");
        medicationsColumn.setCellValueFactory(p -> {
            StringBuilder str = new StringBuilder();
            p.getValue().getMedicationsQuantity().forEach((medication, quantity) ->
                    str.append(quantity).append("x").append(medication.getName()).append(", "));
            return new SimpleStringProperty(str.toString());
        });
        transactionTableView.getColumns().add(medicationsColumn);

        root.getChildren().add(transactionTableView);

        Button done = new Button("Done");
        done.setOnAction(e -> stage.close());
        root.getChildren().add(done);

    }
}
