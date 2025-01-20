package Views;

import Controllers.ShoppingController;
import Models.Expense;
import Models.Shopping;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ShoppingView extends JFrame {
    private ShoppingController shoppingController;
    private JTable shoppingTable;
    private DefaultTableModel tableModel;
    private JTextField shoppingNameField;
    private JTextField shoppingAmountField;
    private JLabel totalAmountLabel;
    private List<Shopping> shoppingList;

    public ShoppingView() {
        shoppingController = new ShoppingController();
        setTitle("Manage Shopping");
        setSize(800, 600); 

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form to add items
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Item Name:"));
        shoppingNameField = new JTextField(10);
        formPanel.add(shoppingNameField);

        formPanel.add(new JLabel("Price:"));
        shoppingAmountField = new JTextField(10);
        formPanel.add(shoppingAmountField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addShoppingItem());
        formPanel.add(addButton);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Table to display shopping items
        String[] columnNames = {"Name", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        shoppingTable = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        shoppingTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(shoppingTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        totalAmountLabel = new JLabel("Total: 0");
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalPanel.add(totalAmountLabel);
        footerPanel.add(totalPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> goToDashboard());
        footerPanel.add(backButton, BorderLayout.WEST);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
			try {
				removeSelectedItem();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        footerPanel.add(removeButton, BorderLayout.EAST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load shopping list
        refreshShoppingList();
    }

    private void refreshShoppingList() {
        try {
            shoppingList = shoppingController.getAllShopping();
            tableModel.setRowCount(0);
            int totalAmount = 0;

            for (Shopping shopping : shoppingList) {
                tableModel.addRow(new Object[]{shopping.getName(), shopping.getAmount()});
                totalAmount += shopping.getAmount();
            }

            totalAmountLabel.setText("Total: " + totalAmount);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load shopping list.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addShoppingItem() {
        String name = shoppingNameField.getText();
        String amountText = shoppingAmountField.getText();

        if (name.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and price.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int amount = Integer.parseInt(amountText);
            shoppingController.addShopping(name, amount);
            refreshShoppingList();
            shoppingNameField.setText("");
            shoppingAmountField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price. Please enter a numeric value.", "Error", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to add shopping item.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedItem() throws IOException {
        int selectedRow = shoppingTable.getSelectedRow();

        if (selectedRow != -1) {
        	Shopping selectedShopping = shoppingList.remove(selectedRow);
            shoppingController.removeShopping(selectedShopping); 
            refreshShoppingList();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void goToDashboard() {
        dispose();
        new DashboardView("User").setVisible(true); 
    }
}
