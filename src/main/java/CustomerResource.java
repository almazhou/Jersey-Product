import domain.Customer;
import json.CustomerJson;
import repository.CustomerRepository;
import repository.OrderRepository;
import repository.PaymentRepository;
import resources.OrderResource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
public class CustomerResource {
    @Inject
    CustomerRepository customerRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    PaymentRepository paymentRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerJson> getAllProductsJson(@Context UriInfo uriInfo){
        List<CustomerJson> customerJsons = customerRepository.getAllCustomers().stream().map(customer -> new CustomerJson(customer, uriInfo)).collect(Collectors.toList());

        return customerJsons;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createCustomer(Form form ,@Context UriInfo uriInfo){
        Customer customer = new Customer(form.asMap().getFirst("name"));
        customerRepository.saveCustomer(customer);
        URI location = URI.create(uriInfo.getBaseUri() + "customers/" + String.valueOf(customer.getId()));
        return Response.created(location).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerJson getCustomerById(@PathParam("id") int id, @Context UriInfo uriInfo){
        Customer customer = customerRepository.getCustomer(id);
        return new CustomerJson(customer,uriInfo);
    }

    @Path("/{id}/orders/")
    public OrderResource getOrderResource(@PathParam("id") int id, @Context UriInfo uriInfo){
        Customer customer = customerRepository.getCustomer(id);
        return new OrderResource(customer, uriInfo, orderRepository,paymentRepository);
    }
}
