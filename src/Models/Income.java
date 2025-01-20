package Models;

import java.util.Date;

public class Income {
    private String name;
    private long amount;
    private Date date; 
    private String type; 

    private static long total = 0;

    public Income(String description, long amount, Date date, String type) {
        this.name = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
        total += amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Income.total = total;
    }
    

    @Override
    public String toString() {
        return name + "," + amount + "," + date + "," + type;
    }
}
