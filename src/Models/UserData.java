package Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserData {
    private String name;
    private long totalIncomeThisMonth;
    private long totalExpenseThisMonth;
    private long balance;

    private List<Income> incomes;
    private List<Expense> expenses;

    public UserData(String name, long totalIncomeThisMonth, long totalExpenseThisMonth, long balance) {
        this.name = name;
        this.totalIncomeThisMonth = totalIncomeThisMonth;
        this.totalExpenseThisMonth = totalExpenseThisMonth;
        this.balance = balance;
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public long getTotalIncomeThisMonth() {
        return totalIncomeThisMonth;
    }

    public long getTotalExpenseThisMonth() {
        return totalExpenseThisMonth;
    }

    public long getBalance() {
        return balance;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    // Filter incomes for current month
    public List<Income> getCurrentMonthIncomes() {
        Calendar current = Calendar.getInstance();
        List<Income> currentMonthIncomes = new ArrayList<>();

        for (Income income : incomes) {
            Calendar incomeDate = Calendar.getInstance();
            incomeDate.setTime(income.getDate());
            if (incomeDate.get(Calendar.YEAR) == current.get(Calendar.YEAR) &&
                incomeDate.get(Calendar.MONTH) == current.get(Calendar.MONTH)) {
                currentMonthIncomes.add(income);
            }
        }
        return currentMonthIncomes;
    }

    // Filter expenses for current month
    public List<Expense> getCurrentMonthExpenses() {
        Calendar current = Calendar.getInstance();
        List<Expense> currentMonthExpenses = new ArrayList<>();

        for (Expense expense : expenses) {
            Calendar expenseDate = Calendar.getInstance();
            expenseDate.setTime(expense.getDate());
            if (expenseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR) &&
                expenseDate.get(Calendar.MONTH) == current.get(Calendar.MONTH)) {
                currentMonthExpenses.add(expense);
            }
        }
        return currentMonthExpenses;
    }

    // Recalculate totals for current month
    public void recalculateMonthlyTotals() {
        this.totalIncomeThisMonth = getCurrentMonthIncomes().stream().mapToLong(Income::getAmount).sum();
        this.totalExpenseThisMonth = getCurrentMonthExpenses().stream().mapToLong(Expense::getAmount).sum();
    }
}
