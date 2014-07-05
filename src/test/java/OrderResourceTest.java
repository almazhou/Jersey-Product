import domain.Customer;
import domain.Order;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.CustomerRepository;
import repository.OrderRepository;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest extends JerseyTest {
    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private CustomerRepository mockCustomerRepository;

    @Override
    protected Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockOrderRepository).to(OrderRepository.class);
                bind(mockCustomerRepository).to(CustomerRepository.class);
            }
        };
        return new ResourceConfig().register(CustomerResource.class).register(binder);
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
}
