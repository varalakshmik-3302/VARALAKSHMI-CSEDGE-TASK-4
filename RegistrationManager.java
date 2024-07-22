import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RegistrationManager extends Frame implements ActionListener {

    // Components for registration form
    private Label nameLabel, emailLabel, genderLabel, cityLabel;
    private TextField nameField, emailField;
    private CheckboxGroup genderGroup;
    private Checkbox maleCheckbox, femaleCheckbox;
    private Choice cityChoice;
    private Button registerButton, displayButton, exportButton;

    // File to store registration data
    private static final String FILE_PATH = "registrants.txt";

    public RegistrationManager() {
        // Frame settings
        setTitle("Registration Manager");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Registration form components
        nameLabel = new Label("Name:");
        add(nameLabel);
        nameField = new TextField(20);
        add(nameField);

        emailLabel = new Label("Email:");
        add(emailLabel);
        emailField = new TextField(20);
        add(emailField);

        genderLabel = new Label("Gender:");
        add(genderLabel);
        genderGroup = new CheckboxGroup();
        maleCheckbox = new Checkbox("Male", genderGroup, false);
        femaleCheckbox = new Checkbox("Female", genderGroup, false);
        add(maleCheckbox);
        add(femaleCheckbox);

        cityLabel = new Label("City:");
        add(cityLabel);
        cityChoice = new Choice();
        cityChoice.add("New York");
        cityChoice.add("Los Angeles");
        cityChoice.add("Chicago");
        cityChoice.add("Houston");
        cityChoice.add("Other");
        add(cityChoice);

        registerButton = new Button("Register");
        registerButton.addActionListener(this);
        add(registerButton);

        displayButton = new Button("Display Registrants");
        displayButton.addActionListener(this);
        add(displayButton);

        exportButton = new Button("Export Registrants");
        exportButton.addActionListener(this);
        add(exportButton);

        setVisible(true);
    }

    // ActionListener implementation for button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUser();
        } else if (e.getSource() == displayButton) {
            displayRegistrants();
        } else if (e.getSource() == exportButton) {
            exportRegistrants();
        }
    }

    // Method to register a new user
    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String gender = maleCheckbox.getState() ? "Male" : "Female";
        String city = cityChoice.getSelectedItem();

        // Append new registration to file
        try (FileWriter writer = new FileWriter(FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("Name: " + name);
            out.println("Email: " + email);
            out.println("Gender: " + gender);
            out.println("City: " + city);
            out.println();

            clearFields();

            System.out.println("User registered successfully!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to display all registrants in console
    private void displayRegistrants() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            System.out.println("Registrants:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to export registrants to a text file
    private void exportRegistrants() {
        // Copy registrants.txt to a chosen directory
        File sourceFile = new File(FILE_PATH);
        File destFile = new File("registrants_export.txt");
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            System.out.println("Registrants exported successfully to registrants_export.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to clear registration form fields
    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        genderGroup.setSelectedCheckbox(null);
        cityChoice.select(0);
    }

    public static void main(String[] args) {
        new RegistrationManager();
    }
}