/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import EntityManager.MemberEntity;
import HelperClasses.ShoppingCartLineItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jiayu
 */
@WebServlet(name = "ECommerce_PaymentServlet", urlPatterns = {"/ECommerce_PaymentServlet"})
public class ECommerce_PaymentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession s = request.getSession();
        try {

            Long countryID = (Long) s.getAttribute("countryID");

            Long memberId = (Long) s.getAttribute("memberId");
            List<ShoppingCartLineItem> shoppingCart = (List<ShoppingCartLineItem>) request.getSession().getAttribute("myCart");

            double amountPaid = 0.0;
            for (ShoppingCartLineItem item : shoppingCart) {
                amountPaid += item.getPrice() * item.getQuantity();
            }

            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://localhost:8080/SEPWebService-Student/webresources/commerce/createEcommerceTransactionRecord")
                    .queryParam("memberID", memberId)
                    .queryParam("countryID", countryID)
                    .queryParam("amountPaid", amountPaid);
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response myResponse = invocationBuilder.put(Entity.entity("", "application/json"));

            if (myResponse.getStatus() == Response.Status.CREATED.getStatusCode()) {
                int count = 0;
                for (ShoppingCartLineItem item : shoppingCart) {
                    Client clientNew = ClientBuilder.newClient();
                    WebTarget targetNew = clientNew
                            .target("http://localhost:8080/SEPWebService-Student/webresources/commerce/createEcommerceLineItemRecord")
                            .queryParam("salesRecordID", Long.parseLong(myResponse.readEntity(String.class)))
                            .queryParam("countryID", countryID)
                            .queryParam("itemEntityID", item.getId())
                            .queryParam("quantity", item.getQuantity());
                    Invocation.Builder invocationBuilderNew = targetNew.request(MediaType.APPLICATION_JSON);
                    Response myResponseNew = invocationBuilderNew.put(Entity.entity("", "application/json"));
                    if (myResponseNew.getStatus() == Response.Status.CREATED.getStatusCode()) {
                        count += Integer.parseInt(myResponseNew.readEntity(String.class));
                    }
                }
                if (count == shoppingCart.size()) {
                    shoppingCart.clear();
                    s.setAttribute("myCart", shoppingCart);
                    String successResult = "Thank you for shopping at Island Furniture. You have checkout successfully!";
                    response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?goodMsg=" + successResult);
                } else {
                    String failResult = "There was an error in processing your order. Please try again.";
                    response.sendRedirect("/IS3102_Project-war/B/SG/shoppingCart.jsp?errMsg=" + failResult);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
