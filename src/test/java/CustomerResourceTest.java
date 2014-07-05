import domain.Customer;
import exception.CustomerNotFoundException;
import exceptionHandler.CustomerNotFoundExceptionMapper;
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
import repository.PaymentRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class CustomerResourceTest extends JerseyTest {
    @Mock
    CustomerRepository mockCustomerRepository;

    @Mock
    OrderRepository mockOrderRepository;

    @Mock
    PaymentRepository mockPaymentRepository;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Override
    protected Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockCustomerRepository).to(CustomerRepository.class);
                bind(mockOrderRepository).to(OrderRepository.class);
                bind(mockPaymentRepository).to(PaymentRepository.class);
            }
        };
        return new ResourceConfig().register(CustomerResource.class).register(binder).register(CustomerNotFoundExceptionMapper.class);
    }

    @Test
    public void should_return_200_when_get_customers_success() throws Exception {
        Customer customer = new Customer(1,"test name");
        when(mockCustomerRepository.getAllCustomers()).thenReturn(Arrays.asList(customer));
        Response response = target("/customers").request().get();

        assertThat(response.getStatus(),is(200));

        List customerList = response.readEntity(List.class);

        Map customerJson = (Map) customerList.get(0);
        assertThat(customerJson.get("name"),is("test name"));
        assertThat(customerJson.get("id"),is(1));
    }

    @Test
    public void should_return_400_when_cannot_find_customer() throws Exception {
        when(mockCustomerRepository.getAllCustomers()).thenThrow(CustomerNotFoundException.class);
        Response response = target("/customers").request().get();

        assertThat(response.getStatus(),is(400));
    }

    @Test
    public void should_return_201_when_post_one_customer() throws Exception {
        Response post = target("/customers").request().post(Entity.form(new Form().param("name", "customer1")));

        assertThat(post.getStatus(),is(201));

        verify(mockCustomerRepository).saveCustomer(customerArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getValue().getName(),is("customer1"));

        assertTrue(post.getHeaderString("location").contains("/customers/"));

    }

    @Test
    public void should_get_200_for_get_1_customer() throws Exception {
        Customer customer = new Customer(1, "test name");
        when(mockCustomerRepository.getCustomer(1)).thenReturn(customer);
        Response response = target("/customers/1").request().get();

        assertThat(response.getStatus(),is(200));

        Map customerJson = response.readEntity(Map.class);

        assertThat(customerJson.get("name"),is("test name"));

        assertThat(customerJson.get("id"),is(1));
    }

    @Test
    public void should_get_400_when_cannot_get_customer() throws Exception {
        when(mockCustomerRepository.getCustomer(1)).thenThrow(CustomerNotFoundException.class);
        Response response = target("/customers/1").request().get();

        assertThat(response.getStatus(),is(400));

    }
}
