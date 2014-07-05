package json;

import domain.Customer;

import javax.ws.rs.core.UriInfo;

public class CustomerJson {
    private Customer customer;
    private UriInfo uriInfo;

    public CustomerJson(Customer customer, UriInfo uriInfo) {
        this.customer = customer;
        this.uriInfo = uriInfo;
    }

    public String getName(){
        return customer.getName();
    }

    public int getId(){
        return customer.getId();
    }

    public String getUri(){
        return uriInfo.getBaseUri()+"/customers/"+String.valueOf(customer.getId());
    }

}
