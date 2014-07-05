package exceptionHandler;

import exception.OrderNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException>{
    @Override
    public Response toResponse(OrderNotFoundException exception) {
        return Response.status(400).build();
    }
}
