package repository;

import domain.Customer;
import domain.Order;
import domain.Payment;

import java.util.List;

public interface PaymentRepository {
    Payment getOrderPayment();

    void savePayment(Customer customer, Order order, Payment payment);

    List<Payment> getPaymentByCustomer(int customerId);
}
