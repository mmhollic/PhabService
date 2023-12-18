package wholesalerpackage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

// The URI wholesaler endpoints are defined in this class.

@Path("/wholesaler")
public class WholesalerResource {
    private WholesalerService wholesalerService = new WholesalerService();

    // GET this method to return a list of all products in the database as a JSON array of Product objects.
    // If the call is successful, the server returns a 200 OK response with the JSON array in the body.
    // If it is not successful, the server returns a 500 Internal Server Error response with an error message in the body.

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewProducts() {
        try {
            return Response.ok(wholesalerService.viewProducts()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
    }

    // POST an Order object in JSON to /wholesaler/order to place an order.
    // See the Order class in src/main/java/wholesalerpackage/Order.java for the fields in an Order object
    // Not all fields are required when submitting an order.
    // If it is successful, the server returns a 200 OK response with the JSON object of the order in the body.
    // If it is not successful, the server returns a 500 Internal Server Error response with an error message in the body.
    @POST
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(Order order) {
        try {
            order = wholesalerService.placeOrder(order);
            return Response.ok()
                    .entity(order)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
    }

    // GET an order by its id. Note - if the order has been delivered, this may not be apparent from the status using this call
    // Returns a 200 OK response with the JSON object of the order in the body if the order is found
    // Returns a 404 Not Found response if the order is not found
    @GET
    @Path("/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId") int orderId) {
        try {
            Order order=wholesalerService.getOrder(orderId);
            if (order==null){
                return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
            }
            return Response.ok(order).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error"+e.getMessage()).build();
        }
    }

    // GET this method to check the delivery status of an order.
    // Returns a 200 OK response with the status of the order in the body (PENDING or DELIVERED) if the order is found
    // Returns a 404 Not Found response if the order is not found
    @GET
    @Path("/delivery/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkDeliveryStatus(@PathParam("orderId") int orderId) {
        try {
            Order order = wholesalerService.checkDeliveryStatus(orderId);
            if (order == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
            }
            return Response.ok(order.getStatus()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error"+e.getMessage()).build();

        }
    }
}

