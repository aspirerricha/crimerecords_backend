import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class App {
    public static void main(String[] args) {
        // Start with the Login UI
        SwingUtilities.invokeLater(() -> {
            LoginUI login = new LoginUI();
            login.setVisible(true);
        });
    }
}

class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public LoginUI() {
        setTitle("Login Module");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(173, 216, 230));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Welcome to Crime Record Management System", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(new Color(0, 0, 128));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(heading, gbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usernameField = new JTextField(18);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        messageLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        mainPanel.add(messageLabel, gbc);

        loginButton.addActionListener(e -> checkLogin());
    }

    private void checkLogin() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.equals("admin") && pass.equals("admin123")) {
            messageLabel.setText("✅ Login Successful!");
            messageLabel.setForeground(new Color(0, 128, 0));

            SwingUtilities.invokeLater(() -> {
                new DashboardUI().setVisible(true);
                dispose();
            });

        } else {
            messageLabel.setText("❌ Invalid Credentials");
            messageLabel.setForeground(Color.RED);
        }
    }
}

class DashboardUI extends JFrame implements ActionListener {
    JButton insertBtn, deleteBtn, updateBtn, displayBtn, searchBtn;

    public DashboardUI() {
        setTitle("Dashboard - Crime Record Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(1, 5, 5, 10));
        panel.setBackground(new Color(250, 249, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(300, 100, 300, 100));

        // Buttons without icons first (to avoid file not found errors)
        insertBtn = createButton("Insert Record");
        deleteBtn = createButton("Delete Record");
        updateBtn = createButton("Update Record");
        displayBtn = createButton("Display Records");
        searchBtn = createButton("Search Record");

        panel.add(insertBtn);
        panel.add(deleteBtn);
        panel.add(updateBtn);
        panel.add(displayBtn);
        panel.add(searchBtn);

        add(panel);

        // Add action listeners
        insertBtn.addActionListener(e -> {
            new CrimeEntryUI().setVisible(true);
        });
        deleteBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        displayBtn.addActionListener(this);
        searchBtn.addActionListener(this);
    }


    void openDisplayFrame() {
        JFrame frame4 = new JFrame("Display All Records");
        frame4.setSize(800, 600);
        frame4.setLocationRelativeTo(null);
        frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(232, 237, 243));

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);

        // ADD BACKEND HERE
        CrimeRecordDAO dao = new CrimeRecordDAO();
        List<CrimeRecord> records = dao.getAllCrimeRecords();

        StringBuilder sb = new StringBuilder();
        if (records.isEmpty()) {
            sb.append("No crime records found in the database.\n");
        } else {
            sb.append("Total Records: ").append(records.size()).append("\n\n");
            for (CrimeRecord record : records) {
                sb.append("Case ID: ").append(record.getCaseId()).append("\n");
                sb.append("Crime Type: ").append(record.getCrimeType()).append("\n");
                sb.append("Location: ").append(record.getLocation()).append("\n");
                sb.append("Date: ").append(record.getDate()).append("\n");
                sb.append("Status: ").append(record.getStatus()).append("\n");
                sb.append("Description: ").append(record.getDescription()).append("\n");
                sb.append("----------------------------------------\n");
            }
        }

        textArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame4.add(panel);
        frame4.setVisible(true);
    }

    void openSearchFrame() {
        JFrame frame3 = new JFrame("Search Record");
        frame3.setSize(600, 400);
        frame3.setLocationRelativeTo(null);
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel r = new JPanel(new GridBagLayout());
        r.setBackground(new Color(232, 237, 243));
        frame3.add(r);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Search Case by Case ID", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(new Color(0, 70, 140));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        r.add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel caseIdLabel = new JLabel("Case ID:");
        caseIdLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        r.add(caseIdLabel, gbc);

        JTextField caseIdField = new JTextField(20);
        caseIdField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        r.add(caseIdField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Arial", Font.BOLD, 20));
        buttonPanel.add(searchBtn);
        r.add(buttonPanel, gbc);

        // ADD BACKEND HERE
        CrimeRecordDAO dao = new CrimeRecordDAO();

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String caseId = caseIdField.getText().trim();
                if (caseId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame3, "Please enter Case ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CrimeRecord record = dao.searchCrimeRecord(caseId);
                if (record != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Record Found:\n\n");
                    sb.append("Case ID: ").append(record.getCaseId()).append("\n");
                    sb.append("Crime Type: ").append(record.getCrimeType()).append("\n");
                    sb.append("Location: ").append(record.getLocation()).append("\n");
                    sb.append("Date: ").append(record.getDate()).append("\n");
                    sb.append("Status: ").append(record.getStatus()).append("\n");
                    sb.append("Description: ").append(record.getDescription()).append("\n");

                    JTextArea resultArea = new JTextArea(sb.toString(), 10, 40);
                    resultArea.setEditable(false);
                    resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
                    JScrollPane scrollPane = new JScrollPane(resultArea);
                    JOptionPane.showMessageDialog(frame3, scrollPane, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame3, "No record found with Case ID: " + caseId, "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        frame3.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteBtn) {
            openDeleteFrame();
        } else if (e.getSource() == updateBtn) {
            openUpdateFrame();
        } else if (e.getSource() == displayBtn) {
            openDisplayFrame(); // Change this
        } else if (e.getSource() == searchBtn) {
            openSearchFrame(); // Change this
        }
    }

    void openDeleteFrame() {
        JFrame frame1 = new JFrame("Delete Record");
        frame1.setSize(600, 400);
        frame1.setLocationRelativeTo(null);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(232, 237, 243));
        frame1.add(p);

        JTextField id;
        JButton Delete;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel heading = new JLabel("Enter Details", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(new Color(0, 70, 140));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        p.add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        gbc.gridy++;
        p.add(new JLabel("Case ID:"), gbc);
        id = new JTextField(24);
        id.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridx = 1;
        p.add(id, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        Delete = new JButton("Delete");
        Delete.setFont(new Font("Arial", Font.BOLD, 20));
        buttonPanel.add(Delete);
        p.add(buttonPanel, gbc);

        // ADD BACKEND HERE
        CrimeRecordDAO dao = new CrimeRecordDAO();

        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String caseId = id.getText().trim();
                if (caseId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame1, "Please enter Case ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dao.deleteCrimeRecord(caseId)) {
                    JOptionPane.showMessageDialog(frame1, "Deleted Successfully!");
                    id.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame1, "Record not found or deletion failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame1.setVisible(true);
    }

    void openUpdateFrame() {
        JFrame frame2 = new JFrame("Update Record");
        frame2.setSize(600, 500);
        frame2.setLocationRelativeTo(null);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel q = new JPanel(new GridBagLayout());
        q.setBackground(new Color(232, 237, 243));
        frame2.add(q);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Update Case Status", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(new Color(0, 70, 140));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        q.add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        gbc.gridy++;
        q.add(new JLabel("Case ID:"), gbc);
        JTextField caseIdField = new JTextField(20);
        caseIdField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        q.add(caseIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel statusLabel = new JLabel("New Status:");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        q.add(statusLabel, gbc);

        String[] statusOptions = {"Open", "Under Investigation", "Case Closed", "Pending"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        q.add(statusCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 20));
        buttonPanel.add(updateBtn);
        q.add(buttonPanel, gbc);

        // ADD BACKEND HERE
        CrimeRecordDAO dao = new CrimeRecordDAO();

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String caseId = caseIdField.getText().trim();
                String newStatus = (String) statusCombo.getSelectedItem();

                if (caseId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame2, "Please enter Case ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dao.updateCrimeRecord(caseId, newStatus)) {
                    JOptionPane.showMessageDialog(frame2, "Updated Successfully!");
                    caseIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame2, "Record not found or update failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame2.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 100));
        return button;
    }
}

class CrimeEntryUI extends JFrame {
    private JTextField caseIdField, crimeTypeField, locationField, dateField;
    private JTextArea descriptionArea;
    private JButton saveBtn, cancelBtn;
    private CrimeRecordDAO crimeDAO;

    public CrimeEntryUI() {
        crimeDAO = new CrimeRecordDAO();
        setTitle("Crime Entry");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(232, 237, 243));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel heading = new JLabel("Enter Crime Details", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(new Color(0, 70, 140));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        mainPanel.add(new JLabel("Case ID:"), gbc);
        caseIdField = new JTextField(24);
        caseIdField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        mainPanel.add(caseIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Crime Type:"), gbc);
        crimeTypeField = new JTextField(24);
        crimeTypeField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        mainPanel.add(crimeTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Location:"), gbc);
        locationField = new JTextField(24);
        locationField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        mainPanel.add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Date:"), gbc);
        dateField = new JTextField(24);
        dateField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        mainPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Description:"), gbc);
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        mainPanel.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        saveBtn.setFont(new Font("Arial", Font.BOLD, 18));
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 18));
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        mainPanel.add(buttonPanel, gbc);

        // Add action listeners
        saveBtn.addActionListener(e -> saveCrimeRecord());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void saveCrimeRecord() {
        String caseId = caseIdField.getText().trim();
        String crimeType = crimeTypeField.getText().trim();
        String location = locationField.getText().trim();
        String dateText = dateField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (caseId.isEmpty() || crimeType.isEmpty() || location.isEmpty() || dateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill Case ID, Crime Type, Location and Date.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date crimeDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            crimeDate = sdf.parse(dateText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.", "Validation", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CrimeRecord record = new CrimeRecord();
        record.setCaseId(caseId);
        record.setCrimeType(crimeType);
        record.setLocation(location);
        record.setDate(crimeDate);
        record.setDescription(description);
        record.setStatus("Open");

        boolean inserted = crimeDAO.insertCrimeRecord(record);
        if (inserted) {
            JOptionPane.showMessageDialog(this, "Saved Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            caseIdField.setText("");
            crimeTypeField.setText("");
            locationField.setText("");
            dateField.setText("");
            descriptionArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Save failed. Please check logs and database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}