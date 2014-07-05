package exceptionHandler;

import exception.CustomerNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException>{
    @Override
    public Response toResponse(CustomerNotFoundException exception) {
        return Response.status(400).build();
    }
}
