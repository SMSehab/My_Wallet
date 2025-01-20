package Services;

import Models.Expense;
import Models.Income;
import Models.UserData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDataService {

    private MaintainanceService maintainanceService;

    public UserDataService() {
        this.maintainanceService = new MaintainanceService();
    }

    public UserData getUserData(String userName) {
        UserData userData = new UserData(userName, 0, 0, 0);

        // Fetch all incomes and expenses
        List<Income> incomes = maintainanceService.getIncomes();
        List<Expense> expenses = maintainanceService.getExpenses();

        userData.setIncomes(incomes);
        userData.setExpenses(expenses);

        userData.recalculateMonthlyTotals();

        // Update balance
//        userData.setBalance(calculateBalance(incomes, expenses));

        return userData;
    }

    public long calculateBalance(List<Income> incomes, List<Expense> expenses) {
        long totalIncome = incomes.stream().mapToLong(Income::getAmount).sum();
        long totalExpense = expenses.stream().mapToLong(Expense::getAmount).sum();
        return totalIncome - totalExpense;
    }

    public List<Object[]> getCombinedLog() {
        List<Object[]> combinedLogs = new ArrayList<>();

        // Fetch all incomes 
        for (Income income : maintainanceService.getIncomes()) {
            combinedLogs.add(new Object[]{
                income.getName(),
                income.getAmount(),
                income.getDate(),
                income.getType(),
                "Income"
            });
        }

        // Fetch all expenses 
        for (Expense expense : maintainanceService.getExpenses()) {
            combinedLogs.add(new Object[]{
                expense.getName(),
                expense.getAmount(),
                expense.getDate(),
                expense.getType(),
                "Expense"
            });
        }

        // Sort combined logs by date 
        combinedLogs.sort((log1, log2) -> ((Date) log2[2]).compareTo((Date) log1[2]));

        return combinedLogs;
    }
}
