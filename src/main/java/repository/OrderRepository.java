package repository;
import domain.Customer;
import domain.Order;
import java.util.List;

public interface OrderRepository {
    List<Order> getOrders(int id);

    void saveOrderOfCustomer(Customer customer, Order order);

    Order getOrderById(int customerId, int orderId);
}
