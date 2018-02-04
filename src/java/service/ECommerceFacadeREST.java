package service;

import Entity.Countryentity;
import Entity.Itementity;
import Entity.Lineitementity;
import Entity.Member;
import Entity.Memberentity;
import Entity.Salesrecordentity;
import static Entity.Salesrecordentity_.createdDate;
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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


@Stateless
@Path("commerce")
public class ECommerceFacadeREST extends AbstractFacade<Salesrecordentity> {

    @PersistenceContext(unitName = "WebService")
    private EntityManager em;
    
    @Context
    private UriInfo context;

    public ECommerceFacadeREST() {
        super(Salesrecordentity.class);
    }

    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
        @GET
    @Path("checkStore")
    public Response checkStore(@QueryParam("SKU") String SKU) throws SQLException{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/islandfurniture-it07?zeroDateTimeBehavior=convertToNull&user=root&password=12345");
            String stmt = "SELECT s.ID  FROM storeentity s, warehouseentity w, storagebinentity sb, storagebinentity_lineitementity sbli, lineitementity l, itementity i where s.WAREHOUSE_ID=w.ID and w.ID=sb.WAREHOUSE_ID and sb.ID=sbli.StorageBinEntity_ID and sbli.lineItems_ID=l.ID and l.ITEM_ID=i.ID and i.SKU=? and sb.type<>'Outbound'";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, SKU);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long storeID = rs.getLong(1);
            return Response.ok(storeID + "", MediaType.APPLICATION_JSON).build();
    }


    @PUT
    @Path("createEcommerceTransactionRecord")
    @Produces({"application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response createEcommerceTransactionRecord(@QueryParam("memberID") Long memberID, @QueryParam("amountPaid") double amountPaid,
            @QueryParam("countryID") Long countryID) {
        try{
           Date date = new Date();
           Salesrecordentity entity = new Salesrecordentity();
           entity.setMemberId(em.find(Memberentity.class, memberID));
           entity.setAmoutPaid(amountPaid);
           entity.setCurrency(em.find(Countryentity.class,countryID).getCurrency());
           entity.setCreatedDate(date);
           entity.setAmountdue(amountPaid);

          

            super.create(entity);
            
            return Response.status(200)
                           .build();
            
        }catch(Exception ex) {
            ex.printStackTrace();            
        }
    

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("createEcommerceLineItemRecord")
    @Produces({"application/json"})
    public Response createEcommerceLineItemRecord(@QueryParam("salesrecordId") Long salesrecordId, @QueryParam("itemEntityID") Long itemEntityID) {
        try {
            Lineitementity lineitementity = new Lineitementity ();
//            lineitementity.setSalesrecordId();
            lineitementity.setItemId(em.find(Itementity.class, itemEntityID));
            getEntityManager().persist(lineitementity);

            Salesrecordentity sale = em.find(Salesrecordentity.class,salesrecordId);
            sale.getLineitementityList().add(lineitementity);
            super.edit(sale);
            
            return Response.status(200).build();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


}

/**
 * PUT method for updating or creating an instance of ECommerce
 *
 * @param content representation for the resource
 * @return an HTTP response with content of the updated or created resource.
 */
