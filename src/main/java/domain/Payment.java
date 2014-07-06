package domain;

public class Payment {
    private double amount;
    private int id;
    private int orderId;

    public Payment(double amount) {
        this.amount = amount;
    }

    public Payment(double amount, int orderId) {

        this.amount = amount;
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }
}
