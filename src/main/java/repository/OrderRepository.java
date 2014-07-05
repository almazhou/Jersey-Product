package repository;
import domain.Order;
import java.util.List;

public interface OrderRepository {
    List<Order> getOrders(int id);
}
