package accountpackage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

// The URI endpoints are defined in this class.
// To deposit, withdraw, or transfer, the client sends a POST request to the appropriate endpoint.

@Path("/account")
public class AccountResource {
    private AccountService accountService = new AccountService();

    // POST a Deposit object in JSON to /account/deposit to deposit money into the specified account
    // Returns a 200 OK response if the deposit was successful, or a 500 Internal Server Error response if there was a problem
    @POST
    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deposit(Deposit deposit) {
        try {
            accountService.deposit(deposit.accountNumber, deposit.amount);
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    // POST a Withdrawal object in JSON to /account/withdraw to withdraw money from the specified account
    // Returns a 200 OK response if the deposit was successful, or a 500 Internal Server Error response if there was a problem

    @POST
    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response withdraw(Withdrawal withdraw) {
        try {
            accountService.withdraw(withdraw.accountNumber, withdraw.amount);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    // POST a Transfer object in JSON to /account/transfer to transfer money between specified accounts
    // Returns a 200 OK response if the deposit was successful, or a 500 Internal Server Error response if there was a problem

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(Transfer transfer) {
        try {
            accountService.transfer(transfer.fromAccount, transfer.toAccount, transfer.amount);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    // GET the balance of the specified account
    // account/balance/{accountNumber} - GET
    // Returns a 200 OK response with the account balance in the form of an Account object as JSON in the response body if the account exists, or a 404 Not Found response if the account does not exist
    @GET
    @Path("/balance/{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewBalance(@PathParam("accountNumber") int accountNumber) {
        try {
            Account ac = accountService.viewBalance(accountNumber);
            if (ac != null) {
                return Response
                        .status(Response.Status.OK)
                        .entity(ac)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error accessing the database").build();
        }
    }
    // GET the balance of all accounts
    // Returns a 200 OK response with the account balances in the form of a List of Account objects as JSON in the response body
    @GET
    @Path("/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> viewBalances() {
        try {
            List<Account> ac = accountService.viewBalances();
            return ac;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Create a new account - it will return the account number in an Account object
    @POST
    @Path("/createaccount")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(Account accountWithNameOnly) {
        Account account;
        try {
            account=accountService.createAccount(accountWithNameOnly.getName());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().entity(account).build();
    }
    // Create all tables in the database
    @GET
    @Path("/createtables")
    public Response resetAccounts() {
        try {
            accountService.createTables();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }


}

