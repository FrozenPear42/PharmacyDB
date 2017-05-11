package com.bugfullabs.pharmacydb.main;

import com.bugfullabs.pharmacydb.DatabaseConnector;
import com.bugfullabs.pharmacydb.model.Employee;
import com.bugfullabs.pharmacydb.model.Medication;
import com.bugfullabs.pharmacydb.ui.LabeledBox;
import com.bugfullabs.pharmacydb.ui.VLabeledBox;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.util.logging.Logger;

public class ManageEmployeesWindow {

    private DatabaseConnector mConnector;
    private ObservableList<Employee> mEmployees;
    private TableView<Employee> mEmployeeTableView;

    public ManageEmployeesWindow(DatabaseConnector connector) {
        mConnector = connector;
        mEmployees = FXCollections.observableArrayList(connector.getEmployees());
        mEmployeeTableView = new TableView<>(mEmployees);

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        Stage stage = new Stage();
        stage.setTitle("Manage Employees");
        stage.setScene(new Scene(root, 1000, 500));
        stage.show();


        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        mEmployeeTableView.getColumns().add(nameColumn);

        TableColumn<Employee, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSurname()));
        mEmployeeTableView.getColumns().add(surnameColumn);

        TableColumn<Employee, String> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getSalary()).asString());
        mEmployeeTableView.getColumns().add(salaryColumn);

        TableColumn<Employee, String> emailColumn = new TableColumn<>("email");
        emailColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getContact().getEmail()));
        mEmployeeTableView.getColumns().add(emailColumn);

        TableColumn<Employee, String> phoneColumn = new TableColumn<>("phone");
        phoneColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getContact().getPhoneNumber()));
        mEmployeeTableView.getColumns().add(phoneColumn);

        TableColumn<Employee, String> streetColumn = new TableColumn<>("street");
        streetColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getContact().getStreet()));
        mEmployeeTableView.getColumns().add(streetColumn);

        TableColumn<Employee, String> cityColumn = new TableColumn<>("city");
        cityColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getContact().getCity()));
        mEmployeeTableView.getColumns().add(cityColumn);

        TableColumn<Employee, String> zipCodeColumn = new TableColumn<>("zipCode");
        zipCodeColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getContact().getZipCode()));
        mEmployeeTableView.getColumns().add(zipCodeColumn);

        TableColumn<Employee, Employee> editColumn = new TableColumn<>("Edit");
        editColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        editColumn.setCellFactory(table -> new TableCell<Employee, Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Button button = new Button("Edit");
                    button.setOnAction(e -> editEmployee(item));
                    setAlignment(Pos.CENTER);
                    setGraphic(button);
                } else {
                    setGraphic(null);
                }
            }
        });

        mEmployeeTableView.getColumns().add(editColumn);

        TableColumn<Employee, Employee> removeColumn = new TableColumn<>("X");
        removeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        removeColumn.setCellFactory(table -> new TableCell<Employee, Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Button button = new Button("X");
                    button.setOnAction(e -> deleteEmployee(item));
                    setAlignment(Pos.CENTER);
                    setGraphic(button);
                } else {
                    setGraphic(null);
                }
            }
        });

        mEmployeeTableView.getColumns().add(removeColumn);

        root.getChildren().add(mEmployeeTableView);

        Button addNew = new Button("Add new...");
        addNew.setOnAction(e -> addEmployee());
        Button done = new Button("Done");
        done.setOnAction(e -> stage.close());

        HBox buttonsBox = new HBox(10, addNew, done);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setAlignment(Pos.CENTER);
        root.getChildren().add(buttonsBox);
    }

    private void addEmployee() {
    }

    private void editEmployee(Employee item) {
        new EmployeeEditWindow(item);
    }

    private void deleteEmployee(Employee item) {
        mConnector.deleteEmployee(item);
        mEmployees.remove(item);
    }

    private class EmployeeEditWindow {
        public EmployeeEditWindow(Employee employee) {
            VBox root = new VBox(10);
            root.setAlignment(Pos.TOP_CENTER);
            root.setPadding(new Insets(10));
            Stage stage = new Stage();
            stage.setTitle("Select Medication");
            stage.setScene(new Scene(root, 400, 700));
            stage.show();

            TextField name = new TextField(employee.getName());
            root.getChildren().add(new VLabeledBox("Name", name));

            TextField surname = new TextField(employee.getSurname());
            root.getChildren().add(new VLabeledBox("Surname", surname));

            TextField salary = new TextField(Double.toString(employee.getSalary()));
            root.getChildren().add(new VLabeledBox("Salary", salary));

            TextField email = new TextField(employee.getContact().getEmail());
            root.getChildren().add(new VLabeledBox("Email", email));

            TextField phone = new TextField(employee.getContact().getPhoneNumber());
            root.getChildren().add(new VLabeledBox("Phone", phone));

            TextField street = new TextField(employee.getContact().getStreet());
            root.getChildren().add(new VLabeledBox("Street", street));

            TextField city = new TextField(employee.getContact().getCity());
            root.getChildren().add(new VLabeledBox("City", city));

            TextField zipCode = new TextField(employee.getContact().getZipCode());
            root.getChildren().add(new VLabeledBox("Zip Code", zipCode));

            Button cancel = new Button("Cancel");
            cancel.setOnAction(e -> stage.close());
            Button done = new Button("Done");
            done.setOnAction(e -> {
                employee.setName(name.getText());
                employee.setSurname(surname.getText());
                try {
                    double vSalary = Double.valueOf(salary.getText());
                    employee.setSalary(vSalary);
                } catch (Exception ex) {
                    Alert warn = new Alert(Alert.AlertType.ERROR);
                    warn.setContentText("Invalid salary value!");
                    warn.showAndWait();
                }
                employee.getContact().setEmail(email.getText());
                employee.getContact().setPhoneNumber(phone.getText());
                employee.getContact().setStreet(street.getText());
                employee.getContact().setCity(city.getText());
                employee.getContact().setZipCode(zipCode.getText());
                mEmployeeTableView.refresh();
                mConnector.updateEmployee(employee);
                stage.close();
            });
            HBox buttonsBox = new HBox(10, cancel, done);
            buttonsBox.setPadding(new Insets(10));
            buttonsBox.setAlignment(Pos.CENTER);
            root.getChildren().add(buttonsBox);
        }
    }

}
