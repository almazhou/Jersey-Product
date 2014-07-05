package resources;

import domain.Customer;
import domain.Order;
import domain.Payment;
import json.PaymentJson;
import repository.PaymentRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

public class PaymentResource {
    private Customer customer;
    private Order order;
    private UriInfo uriInfo;
    private PaymentRepository paymentRepository;

    public PaymentResource(Customer customer, Order order, UriInfo uriInfo, PaymentRepository paymentRepository) {

        this.customer = customer;
        this.order = order;
        this.uriInfo = uriInfo;
        this.paymentRepository = paymentRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaymentJson getPaymentOfOrder(){
        Payment orderPayment = paymentRepository.getOrderPayment();
        return new PaymentJson(customer,order,uriInfo,orderPayment);
    }
}
