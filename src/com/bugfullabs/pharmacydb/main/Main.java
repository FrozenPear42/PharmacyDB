package com.bugfullabs.pharmacydb.main;

import com.bugfullabs.pharmacydb.window.ListTransactionWindow;
import com.bugfullabs.pharmacydb.window.ManageEmployeesWindow;
import com.bugfullabs.pharmacydb.window.NewTransactionWindow;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        primaryStage.setTitle("PharmacyDB");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

        DatabaseConnector connector = new DatabaseConnector("jdbc:mysql://localhost:3306/pharmacy", "root", "root");

        Button newTransaction = new Button("New Transaction");
        newTransaction.setOnAction(e -> new NewTransactionWindow(connector));
        newTransaction.setMinSize(150, 100);

        Button listTransactions = new Button("List Transactions");
        listTransactions.setOnAction(e -> new ListTransactionWindow(connector));
        listTransactions.setMinSize(150, 100);


        Button manageMedications = new Button("Manage Medications");
        manageMedications.setOnAction(e -> {

        });
        manageMedications.setMinSize(150, 100);


        Button manageEmployees = new Button("Manage Employees");
        manageEmployees.setOnAction(e -> new ManageEmployeesWindow(connector));
        manageEmployees.setMinSize(150, 100);

        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.add(newTransaction, 0, 0);
        root.add(listTransactions, 1, 0);
        root.add(manageMedications, 0, 1);
        root.add(manageEmployees, 1, 1);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
