package accountpackage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


@Path("/account")
public class AccountResource {
    private AccountService accountService = new AccountService();

    @POST
    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deposit(Deposit deposit) {
        try {
            accountService.deposit(deposit.accountNumber, deposit.amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.ok().build();
    }

    @POST
    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response withdraw(Withdraw withdraw) {
        try {
            accountService.withdraw(withdraw.accountNumber, withdraw.amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.ok().build();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(Transfer transfer) {
        try {
            accountService.transfer(transfer.fromAccount, transfer.toAccount, transfer.amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.ok().build();
    }

    @GET
    @Path("/balance/{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewBalance(@PathParam("accountNumber") String accountNumber) {
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
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error accessing the database").build();
        }
    }
    @GET
    @Path("/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> viewBalances() {
        try {
            List<Account> ac = accountService.viewBalances();
            return ac;/*if (ac != null) {
                return Response
                        .status(Response.Status.OK)
                        .entity(ac)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Accounts not found").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error accessing the database").build();
        */
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @POST
    @Path("/resetAccounts")
    public Response resetAccounts() {
        try {
            accountService.resetAccounts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.ok().build();
    }
}

class Deposit {
    public String accountNumber;
    public BigDecimal amount;
}
class Withdraw{
    public String accountNumber;
    public BigDecimal amount;
}
class Transfer{
    public String fromAccount;
    public String toAccount;
    public BigDecimal amount;
}