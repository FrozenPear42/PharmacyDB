package com.bugfullabs.pharmacydb.main;

import com.bugfullabs.pharmacydb.model.Contact;
import com.bugfullabs.pharmacydb.model.Employee;
import com.bugfullabs.pharmacydb.model.Medication;
import com.bugfullabs.pharmacydb.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseConnector {

    private static Logger LOG = Logger.getAnonymousLogger();
    private Connection mConnection;

    public DatabaseConnector(String uri, String username, String password) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        mConnection = DriverManager.getConnection(uri, username, password);
        LOG.info("created connection to " + uri);
    }

    public List<Employee> getEmployees() {
        ArrayList<Employee> list = new ArrayList<>();
        try {
            Statement statement = mConnection.createStatement();
            statement.execute("SELECT employeeID, name, surname, salary,\n" +
                    "Contact.contactID, Contact.email, Contact.phoneNumber, Contact.street, Contact.city, Contact.zipCode\n" +
                    "FROM Employee JOIN Contact ON Employee.Contact_contactID = Contact.contactID;");
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                int employeeID = result.getInt("employeeID");
                String name = result.getString("name");
                String surname = result.getString("surname");
                double salary = result.getDouble("salary");


                int contactID = result.getInt("Contact.contactID");
                String email = result.getString("Contact.email");
                String phoneNumber = result.getString("Contact.phoneNumber");
                String street = result.getString("Contact.street");
                String city = result.getString("Contact.city");
                String zipCode = result.getString("Contact.zipCode");

                list.add(new Employee(employeeID, name, surname, salary, new Contact(contactID, email, phoneNumber, street, city, zipCode)));
            }
            result.close();
            statement.close();
            return list;
        } catch (Exception e) {
            LOG.info("Something went wrong...");
        }
        return null;
    }

    public List<Medication> getMedications() {
        ArrayList<Medication> list = new ArrayList<>();
        try {
            Statement statement = mConnection.createStatement();
            statement.execute("SELECT * FROM Medication JOIN Stock ON Medication.medicationID = Stock.Medication_medicationID;");
            ResultSet result = statement.getResultSet();
            while (result.next()) {

                int medicationID = result.getInt("medicationID");
                String name = result.getString("name");
                String amount = result.getString("amount");
                String type = result.getString("type");
                boolean prescription = result.getBoolean("prescription");
                double refundation = result.getDouble("refundation");
                String description = result.getString("description");
                double bulkCost = result.getDouble("bulkCost");

                double stockPrice = result.getDouble("Stock.price");
                int stockAmount = result.getInt("Stock.amount");
                String stockLocation = result.getString("Stock.location");
                list.add(new Medication(medicationID, name, amount, type, prescription, refundation, description,
                        bulkCost, stockPrice, stockAmount, stockLocation));


            }
            result.close();
            statement.close();
            return list;
        } catch (Exception e) {
            LOG.info("Something went wrong...");
        }
        return null;
    }

    public int addTransaction(Transaction transaction) {
        try {

            PreparedStatement statement = mConnection.prepareStatement("INSERT INTO Transaction (total, date, paymentMethod) VALUES ( ?, ? , ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, transaction.getTotal());
            statement.setDate(2, transaction.getDate());
            statement.setString(3, transaction.getPaymentMethod());
            statement.executeUpdate();


            ResultSet gen = statement.getGeneratedKeys();
            gen.next();
            int id = gen.getInt(1);

            statement.close();

            PreparedStatement s = mConnection.prepareStatement("INSERT INTO Transaction_has_Medication (Transaction_transactionID, Medication_medicationID, amount) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            transaction.getMedications().forEach(medication -> {
                try {
                    s.clearParameters();
                    s.setInt(1, id);
                    s.setInt(2, medication.getMedicationID());
                    s.setInt(3, transaction.getQuantityOf(medication));
                    s.executeUpdate();

                } catch (Exception e) {
                    LOG.info("Ups...");
                }
            });
            s.close();
            return id;

        } catch (Exception e) {
            LOG.info("Something went wrong...");
            e.printStackTrace();
        }
        return -1;
    }


    public List<Transaction> getTransactions() {
        ArrayList<Transaction> list = new ArrayList<>();
        List<Medication> medications = getMedications();
        int lastID = -1;
        try {
            Statement statement = mConnection.createStatement();
            statement.execute("SELECT * FROM Transaction JOIN Transaction_has_Medication " +
                    "ON Transaction.transactionID = Transaction_has_Medication.Transaction_transactionID" +
                    " ORDER BY transactionID;");

            ResultSet result = statement.getResultSet();
            while (result.next()) {
                int transactionID = result.getInt("medicationID");
//                if(lastID == -1)
//                    result.
                Date date = result.getDate("date");
                int total = result.getInt("total");
                String paymentMethod = result.getString("paymentMethod");
                list.add(new Transaction(transactionID, , date, paymentMethod));
            }
            result.close();
            statement.close();
            return list;


            PreparedStatement medicationStatament = mConnection.prepareStatement("SELECT * FROM Transaction_has_Medication WHERE Transaction_transactionID = ?");


        } catch (Exception e) {
            LOG.info("Something went wrong...");
        }
        return null;
    }

    public void updateEmployee(Employee employee) {
        try {
            PreparedStatement statement = mConnection.prepareStatement("UPDATE Employee SET name=?, surname=?, salary=? WHERE employeeID = ?");
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setDouble(3, employee.getSalary());
            statement.setInt(4, employee.getID());
            statement.executeUpdate();
            statement.close();

            statement = mConnection.prepareStatement("UPDATE Contact SET email=?, phoneNumber=?, street=?, city=?, zipCode=? WHERE contactID=?");
            statement.setString(1, employee.getContact().getEmail());
            statement.setString(2, employee.getContact().getPhoneNumber());
            statement.setString(3, employee.getContact().getStreet());
            statement.setString(4, employee.getContact().getCity());
            statement.setString(5, employee.getContact().getZipCode());
            statement.setInt(6, employee.getContact().getID());
            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            LOG.info("Something went wrong...");
            e.printStackTrace();
        }
    }

    public void deleteEmployee(Employee item) {
        try {
            PreparedStatement statement = mConnection.prepareStatement("DELETE FROM Employee WHERE employeeID = ?;");
            statement.setInt(1, item.getID());
            statement.executeUpdate();
            statement.close();

            statement = mConnection.prepareStatement("DELETE FROM Contact WHERE contactID = ?;");
            statement.setInt(1, item.getContact().getID());
            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            LOG.info("Something went wrong...");
            e.printStackTrace();
        }
    }
}
