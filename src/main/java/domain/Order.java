package domain;

public class Order {
    private double totalCost;
    private int id;

    public Order(double totalCost) {

        this.totalCost = totalCost;
    }

    public Order(int id, double totalCost) {
        this.id = id;
        this.totalCost = totalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getId() {
        return id;
    }
}
