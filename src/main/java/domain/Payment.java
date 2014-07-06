package domain;

public class Payment {
    private double amount;
    private int id;

    public Payment(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }
}
