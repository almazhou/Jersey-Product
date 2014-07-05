package resources;

import domain.Customer;
import domain.Order;
import domain.OrderJson;
import repository.OrderRepository;

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
}
