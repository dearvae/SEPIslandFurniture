package service;

import Entity.Countryentity;
import Entity.Lineitementity;
import Entity.Member;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.ws.rs.POST;
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

    @POST
    @Path("createEcommerceTransactionRecord")
    @Produces({"application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response createEcommerceTransactionRecord(@QueryParam("memberID") Long memberID, @QueryParam("amountPaid") double amountPaid,
            @QueryParam("countryID") Long countryID) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date); //2016-11-16 12:08:43
        int countryid = countryID.intValue();
        String currency;
          switch (countryid) {
            case 25:  currency = "SGD";
                     break;
            case 26:  currency = "MYR";
                     break;
            case 27:   currency= "IDR";
                     break;
            case 28:  currency = "USD";
                     break;
            case 29:   currency = "RMB";
                     break;
            case 65:  currency = "EUR";
                     break;
         
            default:  currency = "SGD";
                     break;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/islandfurniture-it07?user=root&password=12345");
             String stmt = "INSERT INTO salesrecordentity (AMOUNTDUE, AMOUNTPAID, AMOUNTPAIDUSINGPOINTS,CREATEDDATE,CURRENCY,LOYALTYPOINTSDEDUCTED,POSNAME,MEMBER_ID,STORE_ID)VALUES (?,?, 0,?,?,0,'Counter 1',?,59)";
           // String stmt = "INSERT INTO salesrecordentity (AMOUNTDUE, AMOUNTPAID, AMOUNTPAIDUSINGPOINTS,CREATEDDATE,CURRENCY,LOYALTYPOINTSDEDUCTED,POSNAME,MEMBER_ID,STORE_ID)VALUES (12,12, 0,'2016-11-16 12:08:01','SGD',0,'Counter 1',23,59);";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setDouble(1, amountPaid);
            ps.setDouble(2, amountPaid);
            ps.setString(3, currentDate);
            ps.setString(4, currency);
            ps.setLong(5, memberID);

            int i = ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                long id = rs.getLong(1);
//                System.out.println("Inserted ID -" + id); // display inserted record
//            }
//            if (i > 0) {
//                PreparedStatement ps2 = conn.prepareStatement(stmt);
//                stmt = "select id from salesrecordentity where createdDate = '2016-11-16 12:08:06'";
//                ps2 = conn.prepareStatement(stmt);
//                //    ps.setString(1, currentDate);
//                ResultSet rs = ps2.executeQuery();
//                rs.close();
//
//                conn.close();
//                return Response
//                        .status(201)
//                        .entity(rs)
//                        .build();
//            }

        } catch (SQLException e) {
            while (e != null) {
                String errorMessage = e.getMessage();
                System.err.println("sql error message:" + errorMessage);

                // This vendor-independent string contains a code.
                String sqlState = e.getSQLState();
                System.err.println("sql state:" + sqlState);

                int errorCode = e.getErrorCode();
                System.err.println("error code:" + errorCode);
                // String driverName = conn.getMetaData().getDriverName();
                // System.err.println("driver name:"+driverName);
                // processDetailError(drivername, errorCode);
                e = e.getNextException();
            }

        }
        return Response.status(Response.Status.NOT_FOUND).build();
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
