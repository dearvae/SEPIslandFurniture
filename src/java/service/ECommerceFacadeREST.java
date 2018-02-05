package service;

import Entity.Countryentity;
import Entity.Itementity;
import Entity.Lineitementity;
import Entity.Member;
import Entity.Memberentity;
import Entity.Salesrecordentity;
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


    @PUT
    @Path("createEcommerceTransactionRecord")
    @Produces({"application/json"})
    @Consumes({"application/xml", "application/json"})
    public Response createEcommerceTransactionRecord(@QueryParam("memberId") Long memberID, @QueryParam("amountPaid") double amountPaid,
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
            
          Long salesrecordId = entity.getId();
            
//            return Response.status(200)
//                    .entity(salesrecordId.toString())
//                           .build();
       return Response.ok(salesrecordId.toString(), MediaType.APPLICATION_JSON).build();
            
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

            lineitementity.setItemId(em.find(Itementity.class, itemEntityID));
            getEntityManager().persist(lineitementity);

            Salesrecordentity sale = em.find(Salesrecordentity.class,salesrecordId);
            sale.getLineitementityList().add(lineitementity);
            super.edit(sale);
            
   
       //    return Response.status(200).entity(salesrecordId).build();
         return Response.ok(itemEntityID.toString(), MediaType.APPLICATION_JSON).build();
            
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
