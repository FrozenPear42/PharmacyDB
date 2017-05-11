package com.bugfullabs.pharmacydb.main;

import com.bugfullabs.pharmacydb.DatabaseConnector;
import com.bugfullabs.pharmacydb.model.Transaction;
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

public class ListTransactionWindow {

    private DatabaseConnector mConnector;

    public ListTransactionWindow(DatabaseConnector connector) {
        mConnector = connector;

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        Stage stage = new Stage();
        stage.setTitle("List Transactions");
        stage.setScene(new Scene(root, 600, 500));
        stage.show();

        ObservableList<Transaction> transactions = FXCollections.observableArrayList(connector.getTransactions());

        TableView<Transaction> transactionTableView = new TableView<>(transactions);

        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");

        Button done = new Button("Done");
        root.getChildren().add(done);

    }
}
