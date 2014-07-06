package repository;

import domain.Customer;
import domain.Order;
import domain.Payment;

public interface PaymentRepository {
    Payment getOrderPayment();

    void savePayment(Customer customer, Order order, Payment payment);
}
