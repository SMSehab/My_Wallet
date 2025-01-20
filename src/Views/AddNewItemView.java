package Views;

import Models.Income;
import Models.Expense;
import Services.MaintainanceService;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class AddNewItemView extends JDialog {

    private MaintainanceService maintainanceService;
    private Runnable onDetailsAdded;

    public AddNewItemView(JFrame parent, Runnable onDetailsAdded) {
        super(parent, "Add Details", true);
        this.maintainanceService = new MaintainanceService();
        this.onDetailsAdded = onDetailsAdded;

        setSize(450, 300);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.add(new JLabel("Category:"));
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Income", "Expense"});
        formPanel.add(categoryComboBox);

        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Amount:"));
        JTextField amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Type:"));
        JComboBox<String> typeComboBox = new JComboBox<>(getIncomeTypes());
        formPanel.add(typeComboBox);

        categoryComboBox.addActionListener(e -> {
            if ("Income".equals(categoryComboBox.getSelectedItem())) {
                typeComboBox.setModel(new DefaultComboBoxModel<>(getIncomeTypes()));
            } else {
                typeComboBox.setModel(new DefaultComboBoxModel<>(getExpenseTypes()));
            }
        });

        formPanel.add(new JLabel("Date:"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<Integer> dayComboBox = new JComboBox<>(createRangeArray(1, 31));
        JComboBox<Integer> monthComboBox = new JComboBox<>(createRangeArray(1, 12));
        JComboBox<Integer> yearComboBox = new JComboBox<>(createRangeArray(2000, 2030));
        datePanel.add(dayComboBox);
        datePanel.add(new JLabel("/"));
        datePanel.add(monthComboBox);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearComboBox);
        formPanel.add(datePanel);

        add(formPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String category = (String) categoryComboBox.getSelectedItem();
            String name = nameField.getText();
            long amount;

            try {
                amount = Long.parseLong(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String type = (String) typeComboBox.getSelectedItem();
            int day = (int) dayComboBox.getSelectedItem();
            int month = (int) monthComboBox.getSelectedItem() - 1; 
            int year = (int) yearComboBox.getSelectedItem();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Date date = calendar.getTime();

            try {
                if ("Income".equals(category)) {
                    maintainanceService.addIncome(new Income(name, amount, date, type ));
                } else if ("Expense".equals(category)) {
                    maintainanceService.addExpense(new Expense(name, amount, date, type));
                }
                JOptionPane.showMessageDialog(this, category + " added successfully!");
                onDetailsAdded.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to add " + category + ". Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String[] getIncomeTypes() {
        return new String[]{"Salary", "Business", "Investment", "Gift", "Other"};
    }

    private String[] getExpenseTypes() {
        return new String[]{"Food", "Transport", "Health", "Education", "Entertainment", "Other"};
    }

    private Integer[] createRangeArray(int start, int end) {
        Integer[] range = new Integer[end - start + 1];
        for (int i = start; i <= end; i++) {
            range[i - start] = i;
        }
        return range;
    }
}



