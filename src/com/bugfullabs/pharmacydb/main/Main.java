package com.bugfullabs.pharmacydb.main;

import com.bugfullabs.pharmacydb.DatabaseConnector;
import com.bugfullabs.pharmacydb.model.Medication;
import com.bugfullabs.pharmacydb.model.Transaction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("PharmacyDB");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        DatabaseConnector connector = new DatabaseConnector("jdbc:mysql://localhost:3306/pharmacy", "root", "root");

        connector.getEmployees().forEach(employee ->{
            System.out.println(employee.getName() + " " + employee.getSurname());
        });

        connector.getMedications().forEach(medication -> {
            System.out.println(medication.getName());
        });

        List<Medication> medications = connector.getMedications().subList(0, 5);

        connector.addTransaction(new Transaction(medications, new Date(new java.util.Date().getTime()), "VISA"));
        connector.addTransaction(new Transaction(medications, new Date(new java.util.Date().getTime()), "MC"));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
