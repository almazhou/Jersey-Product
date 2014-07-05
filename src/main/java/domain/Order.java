package domain;

public class Order {
    private double totalCost;
    private int id;

    public Order(double totalCost) {

        this.totalCost = totalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getId() {
        return id;
    }
}
