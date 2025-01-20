package Views;

import Models.User;
import Models.UserData;
import Services.AuthService;
import Services.UserDataService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardView extends JFrame {

    private UserDataService userDataService;
    private JLabel userNameLabel;
    private JLabel balanceLabel;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JTable logTable;
    private DefaultTableModel tableModel;
    
    AuthService authService = new AuthService();
    User currentUser = authService.getCurrentUser();

    public DashboardView(String userName) {
        // Initialize services and user data
        userDataService = new UserDataService();
        UserData userData = userDataService.getUserData(userName);

        // Set frame properties
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel setup
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // Add user data section
        mainPanel.add(createUserDataPanel(userName, userData), BorderLayout.NORTH);

        // Add transaction log table section
        mainPanel.add(createLogTablePanel(), BorderLayout.CENTER);

        // Add footer buttons section
        mainPanel.add(createFooterPanel(userName), BorderLayout.SOUTH);

        // Load initial data
        reloadDashboard();
    }

    private JPanel createUserDataPanel(String userName, UserData userData) {
        JPanel dataPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        dataPanel.add(new JLabel("User:"));
        userNameLabel = new JLabel(currentUser.getUserName());
        dataPanel.add(userNameLabel);

        dataPanel.add(new JLabel("Balance:"));
        balanceLabel = new JLabel(String.valueOf(userDataService.calculateBalance(userData.getIncomes(), userData.getExpenses())));
        dataPanel.add(balanceLabel);

        String currentMonth = new SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime());

        dataPanel.add(new JLabel("Total Income in " + currentMonth + ":"));
        incomeLabel = new JLabel();
        dataPanel.add(incomeLabel);

        dataPanel.add(new JLabel("Total Expense in " + currentMonth + ":"));
        expenseLabel = new JLabel();
        dataPanel.add(expenseLabel);

        return dataPanel;
    }

    private JPanel createLogTablePanel() {
        JPanel logPanel = new JPanel(new BorderLayout(10, 10));

        // Add headline
        Calendar calendar = Calendar.getInstance();
        String currentMonth = new SimpleDateFormat("MMMM").format(calendar.getTime());
        int currentYear = calendar.get(Calendar.YEAR);
        JLabel headline = new JLabel("Transactions in " + currentMonth + ", " + currentYear, SwingConstants.CENTER);
        headline.setFont(new Font("Arial", Font.BOLD, 16));
        logPanel.add(headline, BorderLayout.NORTH);

        // Set up table
        String[] columnNames = {"Name", "Amount", "Date", "Type", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        logTable = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < logTable.getColumnCount(); i++) {
            logTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(logTable);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        return logPanel;
    }

    private JPanel createFooterPanel(String userName) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton shoppingListButton = new JButton("Things to Buy");
        shoppingListButton.addActionListener(e -> {
            new ShoppingView().setVisible(true);
            dispose();
        });
        footerPanel.add(shoppingListButton);

        JButton incomeButton = new JButton("Manage Income");
        incomeButton.addActionListener(e -> {
            try {
                new IncomeView(userName).setVisible(true);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        footerPanel.add(incomeButton);

        JButton expenseButton = new JButton("Manage Expenses");
        expenseButton.addActionListener(e -> {
            new ExpenseView(userName).setVisible(true);
            dispose();
        });
        footerPanel.add(expenseButton);

        JButton addDetailsButton = new JButton("Add New Item");
        addDetailsButton.addActionListener(e -> openAddDetailsWindow());
        footerPanel.add(addDetailsButton);

        return footerPanel;
    }

    private void reloadDashboard() {
        tableModel.setRowCount(0); // Clear table

        // Load and filter logs for the current month
        List<Object[]> logs = userDataService.getCombinedLog().stream()
            .filter(this::isCurrentMonth)
            .collect(Collectors.toList());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
        for (Object[] log : logs) {
            log[2] = dateFormat.format(log[2]); // Format the date
            tableModel.addRow(log);
        }

        // Update user totals
        UserData updatedData = userDataService.getUserData(userNameLabel.getText());
        balanceLabel.setText(String.valueOf(userDataService.calculateBalance(updatedData.getIncomes(), updatedData.getExpenses())));
        incomeLabel.setText(String.valueOf(updatedData.getTotalIncomeThisMonth()));
        expenseLabel.setText(String.valueOf(updatedData.getTotalExpenseThisMonth()));
    }

    private boolean isCurrentMonth(Object[] log) {
        Calendar current = Calendar.getInstance();
        Calendar logDate = Calendar.getInstance();
        logDate.setTime((Date) log[2]);
        return current.get(Calendar.YEAR) == logDate.get(Calendar.YEAR) &&
               current.get(Calendar.MONTH) == logDate.get(Calendar.MONTH);
    }

    private void openAddDetailsWindow() {
        new AddNewItemView(this, this::reloadDashboard).setVisible(true);
    }
}


