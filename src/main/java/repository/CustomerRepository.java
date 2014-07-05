package repository;

import domain.Customer;

import java.util.List;

public interface CustomerRepository {
    public List<Customer> getAllCustomers();

    void saveCustomer(Customer customer);
}
