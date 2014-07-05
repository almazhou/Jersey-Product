package resources;

import domain.Customer;
import domain.OrderJson;
import repository.OrderRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResource {
    private OrderRepository orderRepository;
    private Customer customer;
    private UriInfo uriInfo;



    public OrderResource(OrderRepository orderRepository, Customer customer, UriInfo uriInfo) {
        this.orderRepository = orderRepository;
        this.customer = customer;
        this.uriInfo = uriInfo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderJson> getAllOrdersOfCustomer(){
        return orderRepository.getOrders(customer.getId()).stream().map(order -> new OrderJson(order, customer, uriInfo)).collect(Collectors.toList());
    }
}
