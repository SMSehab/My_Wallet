
package Views;

import Models.Income;
import Controllers.TransactionController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeView extends JFrame {
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField amountField;
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> typeComboBox;
    private TransactionController transactionController;
    private JTable incomeTable;
    private DefaultTableModel tableModel;
    private JLabel totalAmountLabel;
    private List<Income> incomeListData;

    public IncomeView(String userName) throws IOException {
        transactionController = new TransactionController();
        setTitle("All Incomes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Date:"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dayComboBox = new JComboBox<>(createRangeArray(1, 31));
        monthComboBox = new JComboBox<>(createRangeArray(1, 12));
        yearComboBox = new JComboBox<>(createRangeArray(2000, 2030));
        datePanel.add(dayComboBox);
        datePanel.add(new JLabel("/"));
        datePanel.add(monthComboBox);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearComboBox);
        formPanel.add(datePanel);

        formPanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Salary", "Business", "Gift", "Investment", "Other"});
        formPanel.add(typeComboBox);

        JPanel formContainer = new JPanel(new BorderLayout(0, 10));
        formContainer.add(formPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Income");
        formContainer.add(addButton, BorderLayout.SOUTH);
        contentPane.add(formContainer, BorderLayout.NORTH);

        // Table Panel
        JPanel tableContainer = new JPanel(new BorderLayout());
        String[] columnNames = {"Name", "Amount", "Date", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0);
        incomeTable = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < incomeTable.getColumnCount(); i++) {
            incomeTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(incomeTable);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(tableContainer, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        totalAmountLabel = new JLabel("Total: 0");
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalPanel.add(totalAmountLabel);
        footerPanel.add(totalPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Dashboard");
        footerPanel.add(backButton, BorderLayout.WEST);

        JButton removeButton = new JButton("Remove");
        footerPanel.add(removeButton, BorderLayout.EAST);

        contentPane.add(footerPanel, BorderLayout.SOUTH);

        loadIncomeData();

        // Add Income 
        addButton.addActionListener(e -> {
            try {
            	String name = nameField.getText();
                long amount = Long.parseLong(amountField.getText());
                String type = (String) typeComboBox.getSelectedItem();
                int day = (int) dayComboBox.getSelectedItem();
                int month = (int) monthComboBox.getSelectedItem();
                int year = (int) yearComboBox.getSelectedItem();

                String date = String.format("%04d-%02d-%02d", year, month, day);

                transactionController.addIncome(name, amount, date, type);

                loadIncomeData();
                
                // Clear the input fields
                nameField.setText("");
                amountField.setText("");
                dayComboBox.setSelectedIndex(0);
                monthComboBox.setSelectedIndex(0);
                yearComboBox.setSelectedIndex(0);
                typeComboBox.setSelectedIndex(0);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please check your data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Remove item
        removeButton.addActionListener(e -> {
            int selectedRow = incomeTable.getSelectedRow();
            if (selectedRow != -1) {

            	Income selectedIncome = incomeListData.remove(selectedRow);

                transactionController.removeIncome(selectedIncome);

                // Reload the table 
                loadIncomeData();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });


        // Back Button
        backButton.addActionListener(e -> {
            dispose();
            new DashboardView(userName).setVisible(true);
        });
    }

    private void loadIncomeData() {
        incomeListData = transactionController.getAllIncomes().stream()
                .sorted((i1, i2) -> i2.getDate().compareTo(i1.getDate()))
                .collect(Collectors.toList());

        tableModel.setRowCount(0);
        long totalAmount = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy"); // Updated format

        for (Income income : incomeListData) {
            tableModel.addRow(new Object[]{
                    income.getName(),
                    income.getAmount(),
                    dateFormat.format(income.getDate()), // Format as yyyy-MM-dd
                    income.getType()
            });
            totalAmount += income.getAmount();
        }

        totalAmountLabel.setText("Total: " + totalAmount);
    }


    private Integer[] createRangeArray(int start, int end) {
        return java.util.stream.IntStream.rangeClosed(start, end).boxed().toArray(Integer[]::new);
    }
}


