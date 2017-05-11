package com.bugfullabs.pharmacydb;

import com.bugfullabs.pharmacydb.model.Contact;
import com.bugfullabs.pharmacydb.model.Employee;
import com.bugfullabs.pharmacydb.model.Medication;
import com.bugfullabs.pharmacydb.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseConnector {
    private static final String SQL_SELECT_EMPLOYEES =
            "SELECT\n" +
                    "  employeeID,\n" +
                    "  name,\n" +
                    "  surname,\n" +
                    "  salary,\n" +
                    "  Contact.contactID,\n" +
                    "  Contact.email,\n" +
                    "  Contact.phoneNumber,\n" +
                    "  Contact.street,\n" +
                    "  Contact.city,\n" +
                    "  Contact.zipCode\n" +
                    "FROM\n" +
                    "  Employee\n" +
                    "  JOIN Contact ON Employee.Contact_contactID = Contact.contactID;";

    private static final String SQL_SELECT_MEDICATIONS = "SELECT * FROM Medication JOIN Stock ON Medication.medicationID = Stock.Medication_medicationID;";
    private static final String SQL_CREATE_DB = "";


    private static Logger LOG = Logger.getAnonymousLogger();
    private Connection mConnction;
    private List<Transaction> mTransactions;

    public DatabaseConnector(String uri, String username, String password) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        mConnction = DriverManager.getConnection(uri, username, password);
        LOG.info("created connection to " + uri);
    }


    public void createDatabase() {
        try {
            Statement statement = mConnction.createStatement();
            statement.execute(SQL_CREATE_DB);
        } catch (Exception e) {
            LOG.info("FUCK...");
        }
    }

    public List<Employee> getEmployees() {
        ArrayList<Employee> list = new ArrayList<>();
        try {
            Statement statement = mConnction.createStatement();
            statement.execute(SQL_SELECT_EMPLOYEES);
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
            LOG.info("FUCK...");
        }
        return null;
    }

    public void updateMedication(Medication medication) {

    }

    public List<Medication> getMedications() {
        ArrayList<Medication> list = new ArrayList<>();
        try {
            Statement statement = mConnction.createStatement();
            statement.execute(SQL_SELECT_MEDICATIONS);
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
            LOG.info("FUCK...");
        }
        return null;
    }

    public int addTransaction(Transaction transaction) {
        try {

            PreparedStatement statement = mConnction.prepareStatement("INSERT INTO Transaction (total, date, paymentMethod) VALUES ( ?, ? , ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, transaction.getMedications().stream().mapToDouble(Medication::getStockPrice).sum());
            statement.setDate(2, transaction.getDate());
            statement.setString(3, transaction.getPaymentMethod());
            statement.executeUpdate();


            ResultSet gen = statement.getGeneratedKeys();
            gen.next();
            int id = gen.getInt(1);

            statement.close();

            PreparedStatement s = mConnction.prepareStatement("INSERT INTO Transaction_has_Medication (Transaction_transactionID, Medication_medicationID, amount) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            transaction.getMedications().forEach(medication -> {
                try {
                    s.clearParameters();
                    s.setInt(1, id);
                    s.setInt(2, medication.getMedicationID());
                    //FIXME: get real value
                    s.setInt(3, 10);
                    s.executeUpdate();

                } catch (Exception e) {
                    LOG.info("Ups...");
                }
            });
            s.close();
            return id;

        } catch (Exception e) {
            LOG.info("FUCK...");
            e.printStackTrace();
        }
        return -1;
    }


    public List<Transaction> getTransactions() {
        return mTransactions;
    }
}
