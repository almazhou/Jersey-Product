package resources;

import domain.Customer;
import domain.Order;
import domain.Payment;
import json.PaymentJson;
import repository.PaymentRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

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

    public PaymentResource(Customer customer, UriInfo uriInfo, PaymentRepository paymentRepository) {
        this.customer = customer;
        this.uriInfo = uriInfo;
        this.paymentRepository = paymentRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaymentJson getPaymentOfOrder(){
        Payment orderPayment = paymentRepository.getOrderPayment();
        return new PaymentJson(customer,order,uriInfo,orderPayment);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response savePayment(Form form){
        Payment payment = new Payment(Double.valueOf(form.asMap().getFirst("amount")).doubleValue());
        paymentRepository.savePayment(customer,order, payment);
        String location = "/customers/" + String.valueOf(customer.getId()) + "/payment/" + String.valueOf(payment.getId());
        return Response.created(URI.create(location)).build();
    }


}
