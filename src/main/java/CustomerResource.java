import domain.Customer;
import json.CustomerJson;
import repository.CustomerRepository;

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
}
