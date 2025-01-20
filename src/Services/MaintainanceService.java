package Services;

import Models.Expense;
import Models.Income;
import Models.Shopping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaintainanceService {

    private static final String INCOME_FILE = "src/Database/income.txt";
    private static final String EXPENSE_FILE = "src/Database/expenses.txt";
    private static final String SHOPPING_FILE = "src/Database/shopping.txt";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final DatabaseService databaseService;

    public MaintainanceService() {
        this.databaseService = new DatabaseService();
    }

    public void addIncome(Income income) {
        databaseService.writeLine(INCOME_FILE, formatIncomeToString(income));
    }

    public void addExpense(Expense expense) {
        databaseService.writeLine(EXPENSE_FILE, formatExpenseToString(expense));
    }

    public void addShopping(Shopping shopping) {
        databaseService.writeLine(SHOPPING_FILE, shopping.getName() + "," + shopping.getAmount());
    }

    public List<Income> getIncomes() {
        return parseIncomes(databaseService.readAllLines(INCOME_FILE));
    }

    public List<Expense> getExpenses() {
        return parseExpenses(databaseService.readAllLines(EXPENSE_FILE));
    }

    public List<Shopping> getShoppings() {
        return parseShoppings(databaseService.readAllLines(SHOPPING_FILE));
    }

    public void removeIncome(Income income) {
        databaseService.removeLineByValue(INCOME_FILE, formatIncomeToString(income));
    }

    public void removeExpense(Expense expense) {
        databaseService.removeLineByValue(EXPENSE_FILE, formatExpenseToString(expense));
    }

    public void removeShopping(Shopping shopping) {
        databaseService.removeLineByValue(SHOPPING_FILE, shopping.getName() + "," + shopping.getAmount());
    }

    private String formatIncomeToString(Income income) {
        return String.join(",", income.getName(), String.valueOf(income.getAmount()), DATE_FORMAT.format(income.getDate()), income.getType());
    }

    private String formatExpenseToString(Expense expense) {
        return String.join(",", expense.getName(), String.valueOf(expense.getAmount()), DATE_FORMAT.format(expense.getDate()), expense.getType());
    }

    private List<Income> parseIncomes(List<String> lines) {
        return parseGeneric(lines, Income.class);
    }

    private List<Expense> parseExpenses(List<String> lines) {
        return parseGeneric(lines, Expense.class);
    }

    private List<Shopping> parseShoppings(List<String> lines) {
        List<Shopping> shoppings = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                shoppings.add(new Shopping(parts[0], Integer.parseInt(parts[1])));
            }
        }
        return shoppings;
    }

    private <T> List<T> parseGeneric(List<String> lines, Class<T> type) {
        List<T> results = new ArrayList<>();
        
        for (String line : lines) {
            String[] parts = line.split(",");
            
            try {
                if (type == Income.class && parts.length == 4) {
                    // Parse line as Income
                    String name = parts[0];
                    long amount = Long.parseLong(parts[1]);
                    Date date = DATE_FORMAT.parse(parts[2]);
                    String typeDescription = parts[3];
                    results.add(type.cast(new Income(name, amount, date, typeDescription)));
                } 
                
                
                else if (type == Expense.class && parts.length == 4) {
                    // Parse line as Expense
                    String name = parts[0];
                    long amount = Long.parseLong(parts[1]);
                    Date date = DATE_FORMAT.parse(parts[2]);
                    String typeDescription = parts[3];
                    results.add(type.cast(new Expense(name, amount, date, typeDescription)));
                }
                
                
            } catch (ParseException e) {
                System.err.println("Error parsing date for line: " + line);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing number for line: " + line);
            } catch (ClassCastException e) {
                System.err.println("Error casting parsed object for line: " + line);
            }
        }
        
        return results;
    }


  						
}


