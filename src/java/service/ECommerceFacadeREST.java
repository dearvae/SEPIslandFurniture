package service;

import Entity.Countryentity;
import Entity.Lineitementity;
import Entity.Member;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("commerce")
public class ECommerceFacadeREST {

    @Context
    private UriInfo context;

    public ECommerceFacadeREST() {

    }

    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("createEcommerceTransactionRecord")
    @Produces({"application/json"})
    public Response createEcommerceTransactionRecord(@QueryParam("memberID") Long memberID, @QueryParam("amountPaid") double amountPaid,
            @QueryParam("countryID") Long countryID) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date); //2016/11/16 12:08:43
        String currency = getCurrency(countryID);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/islandfurniture-it07?user=root&password=12345");
            String stmt = "INSERT INTO salesrecordentity (AMOUNTDUE, AMOUNTPAID, AMOUNTPAIDUSINGPOINTS,CREATEDDATE,CURRENCY,LOYALTYPOINTSDEDUCTED,POSNAME,MEMBER_ID,STORE_ID)VALUES (?,?, 0,?,?,0,Counter 1,?,59);SELECT SCOPE_IDENTITY();";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setDouble(1, amountPaid);
            ps.setDouble(2, amountPaid);
            ps.setString(3, currentDate);
            ps.setString(4, currency);
            ps.setLong(5, memberID);

            int id = ps.executeUpdate();
            conn.close();
            return Response
                    .status(201)
                    .entity(id)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public String getCurrency(Long countryID) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://localhost:8080/SEPWebService-Student/webresources/entity.countryentity")
                .path("get")
                .queryParam("id", countryID);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Countryentity result = (Countryentity) response.readEntity(Countryentity.class);
        return result.getCurrency();
    }

    @PUT
    @Path("createEcommerceLineItemRecord")
    @Produces({"application/json"})
    public Response createEcommerceLineItemRecord(@QueryParam("salesRecordID") Long salesRecordID, @QueryParam("itemEntityID") Long itemEntityID,
            @QueryParam("quantity") Integer quantity, @QueryParam("countryID") Long countryID) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/islandfurniture-it07?user=root&password=12345");
            //     String stmt = "UPDATE salesrecordentity_lineitementity set salesRecordID=?, itemEntityID=?, quantity=?, countryID=? WHERE id=?";
            String stmt = "";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setLong(1, salesRecordID);
            ps.setLong(2, itemEntityID);
            ps.setInt(3, quantity);
            ps.setLong(4, countryID);
            int result = ps.executeUpdate();
            if (result > 0) {
                conn.close();
                return Response.status(200).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}

/**
 * PUT method for updating or creating an instance of ECommerce
 *
 * @param content representation for the resource
 * @return an HTTP response with content of the updated or created resource.
 */
