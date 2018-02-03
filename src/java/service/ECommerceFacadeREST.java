package service;

import Entity.Lineitementity;
import Entity.Member;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
       try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/islandfurniture-it07?user=root&password=12345");
        //    String stmt = "UPDATE salesrecordentity set memberID=?, amountPaid=?, countryID=? WHERE id=?";
           String stmt = "";  
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setLong(1, memberID);
            ps.setDouble(2, amountPaid);
            ps.setLong(3,countryID);
            int result =ps.executeUpdate();
            if(result>0){
                     conn.close();
                return Response.status(200).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();     
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
            ps.setLong(4,countryID);
            int result =ps.executeUpdate();
            if(result>0){
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

