package domain;

import javax.ws.rs.core.UriInfo;

public class OrderJson {
    private Order order;
    private Customer customer;
    private UriInfo uriInfo;

    public OrderJson(Order order, Customer customer, UriInfo uriInfo) {
        this.order = order;
        this.customer = customer;

        this.uriInfo = uriInfo;
    }

    public double getTotalCost(){
        return order.getTotalCost();
    }

    public int getId(){
        return order.getId();
    }

    public int getCustomerId(){
        return customer.getId();
    }

    public String getUri(){
        return uriInfo.getBaseUri()+"customers/"+String.valueOf(customer.getId())+"/orders/"+String.valueOf(order.getId());
    }

}
