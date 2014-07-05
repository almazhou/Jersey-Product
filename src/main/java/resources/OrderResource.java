package resources;

import domain.Customer;
import domain.Order;
import domain.OrderJson;
import repository.OrderRepository;
import repository.PaymentRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResource {
    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private Customer customer;
    private UriInfo uriInfo;



    public OrderResource(Customer customer, UriInfo uriInfo, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.customer = customer;
        this.uriInfo = uriInfo;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderJson> getAllOrdersOfCustomer(){
        return orderRepository.getOrders(customer.getId()).stream().map(order -> new OrderJson(order, customer, uriInfo)).collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response saveOrderOfCustomer(Form form){
        Double orderCost = Double.valueOf(form.asMap().getFirst("amount"));
        Order order = new Order(orderCost);
        orderRepository.saveOrderOfCustomer(customer, order);

        URI location = URI.create(new OrderJson(order, customer, uriInfo).getUri());
        return Response.created(location).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OrderJson getAllOrderOfCustomer(@PathParam("id")int id){
        Order order = orderRepository.getOrderById(customer.getId(), id);
        return new OrderJson(order,customer,uriInfo);
    }

    @Path("/{id}/payment")
    public PaymentResource getPaymentOfOrder(@PathParam("id") int id){
        Order order = orderRepository.getOrderById(customer.getId(), id);
        return new PaymentResource(customer,order,uriInfo,paymentRepository);
    }
}
