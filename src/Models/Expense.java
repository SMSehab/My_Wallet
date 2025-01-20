package Models;

import java.util.Date;

public class Expense {
    private String name;
    private long amount;
    private Date date; // New attribute
    private String type; // New attribute   
    private static long total = 0;


    public Expense(String description, long amount, Date date, String type ) {
        this.name = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
        total += amount;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.name = description;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public static long getTotal() {
        return total;
    }

    public static void setTotal(long total) {
        Expense.total = total;
    }

	@Override
	public String toString() {
		return name + "," + amount + "," + date + "," + type;
	}


}
