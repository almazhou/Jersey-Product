package json;

import domain.Customer;
import domain.Order;
import domain.Payment;

import javax.ws.rs.core.UriInfo;

public class PaymentJson {
    private Customer customer;
    private Order order;
    private UriInfo uriInfo;
    private Payment orderPayment;

    public PaymentJson(Customer customer, Order order, UriInfo uriInfo, Payment orderPayment) {
        this.customer = customer;
        this.order = order;
        this.uriInfo = uriInfo;
        this.orderPayment = orderPayment;
    }

    public double getAmount(){
        return orderPayment.getAmount();
    }

    public int getOrderId(){
        return order.getId();
    }

    public int getCustomerId(){
        return customer.getId();
    }

    public String getUri(){
       return uriInfo.getBaseUri()+"customers/"+String.valueOf(customer.getId())+"/orders/"+String.valueOf(order.getId())+"/payment";
    }

}
