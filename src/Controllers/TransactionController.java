package Controllers;

import Models.Expense;
import Models.Income;
import Services.MaintainanceService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionController {

    private MaintainanceService maintainanceService;
    private SimpleDateFormat dateFormat;

    public TransactionController() {
        this.maintainanceService = new MaintainanceService();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    // Add Income by raw attributes
    public void addIncome(String name, long amount, String date, String type) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date); // Parse as yyyy-MM-dd
            Income income = new Income(name, amount, parsedDate, type);
            maintainanceService.addIncome(income);
        } catch (Exception e) {
            throw new Exception("Failed to add income. Please check the input format.");
        }
    }

    // Same as Income
    public void addExpense(String name, long amount, String date, String type) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date); 
            Expense expense = new Expense(name, amount, parsedDate, type);
            maintainanceService.addExpense(expense);
        } catch (Exception e) {
            throw new Exception("Failed to add expense. Please check the input format.");
        }
    }




    // Retrieve all incomes
    public List<Income> getAllIncomes() {
        return maintainanceService.getIncomes();
    }

    // Retrieve all expenses
    public List<Expense> getAllExpenses() {
        return maintainanceService.getExpenses();
    }

    // Remove an income 
    public void removeIncome(Income income) {
        maintainanceService.removeIncome(income);
    }


    // Remove an expense 
    public void removeExpense(Expense expense) {
        maintainanceService.removeExpense(expense);
    }
    
//    public List<Object[]> getCombinedLogs() {
//        return maintainanceService.getCombinedLogs();
//    }
}
