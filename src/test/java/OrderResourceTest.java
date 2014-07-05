import domain.Customer;
import domain.Order;
import exception.CustomerNotFoundException;
import exception.OrderNotFoundException;
import exceptionHandler.CustomerNotFoundExceptionMapper;
import exceptionHandler.OrderNotFoundExceptionMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.CustomerRepository;
import repository.OrderRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest extends JerseyTest {
    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private CustomerRepository mockCustomerRepository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @Override
    protected Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockOrderRepository).to(OrderRepository.class);
                bind(mockCustomerRepository).to(CustomerRepository.class);
            }
        };
        return new ResourceConfig().register(CustomerResource.class).register(binder).register(OrderNotFoundExceptionMapper.class).register(CustomerNotFoundExceptionMapper.class);
    }

    @Test
    public void should_return_200_when_get_all_orders() throws Exception {
        Order order = new Order(45.00);
        Customer customer = new Customer(1, "test name");
        when(mockCustomerRepository.getCustomer(1)).thenReturn(customer);
        when(mockOrderRepository.getOrders(1)).thenReturn(Arrays.asList(order));
        Response response = target("/customers/1/orders").request().get();

        assertThat(response.getStatus(),is(200));

        List list = response.readEntity(List.class);

        Map orderJson = (Map)list.get(0);

        assertThat(orderJson.get("totalCost"),is(45.00));
        assertThat(orderJson.get("customerId"),is(1));
    }

    @Test
    public void should_get_400_when_customer_not_found() throws Exception {
        when(mockCustomerRepository.getCustomer(1)).thenThrow(CustomerNotFoundException.class);

        Response response = target("/customers/1/orders").request().get();

        assertThat(response.getStatus(),is(400));
    }

    @Test
    public void should_get_201_when_post_orders() throws Exception {
        when(mockCustomerRepository.getCustomer(1)).thenReturn(new Customer("test name"));
        Response response = target("/customers/1/orders").request().post(Entity.form(new Form().param("amount", "45.00")));

        verify(mockOrderRepository).saveOrderOfCustomer(customerArgumentCaptor.capture(),orderArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getValue().getName(),is("test name"));
        assertThat(orderArgumentCaptor.getValue().getTotalCost(),is(45.00));
    }

    @Test
    public void should_return_200_when_get_one_order() throws Exception {
        Customer customer = new Customer(1, "test name");
        Order order = new Order(45.00);
        when(mockCustomerRepository.getCustomer(1)).thenReturn(customer);
        when(mockOrderRepository.getOrderById(1,1)).thenReturn(order);

        Response response = target("/customers/1/orders/1").request().get();

        assertThat(response.getStatus(),is(200));

        Map map = response.readEntity(Map.class);

        assertThat(map.get("totalCost"),is(45.0));
        assertThat(map.get("customerId"),is(1));
    }

    @Test
    public void should_get_400_when_order_not_found() throws Exception {
        when(mockCustomerRepository.getCustomer(1)).thenReturn(new Customer(1,"test name"));
        when(mockOrderRepository.getOrderById(1, 1)).thenThrow(OrderNotFoundException.class);

        Response response = target("/customers/1/orders/1").request().get();

        assertThat(response.getStatus(),is(400));

    }
}
