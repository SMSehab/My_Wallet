package Models;

public class Shopping {
    private String name;
    private int amount;
    private static int total = 0;

    public Shopping(String name, int amount) {
        this.name = name;
        this.amount = amount;
        total += amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        Shopping.total = total;
    }

    @Override
    public String toString() {
        return name + "," + Integer.toString(amount);
    }
}
