/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import CommonInfrastructure.AccountManagement.AccountManagementBeanLocal;
import CommonInfrastructure.SystemSecurity.SystemSecurityBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author BN
 */
public class ECommerce_MemberEditProfileServlet extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                      Client client = ClientBuilder.newClient();
                      WebTarget target = client.target("http://localhost:8080/RESTfulTest/webservices/CarShopServices")
                                                                 .path("updateCar")
                                                                 .queryParam("id", 2L)
                                                                 .queryParam("brand", "abc")
                                                                 .queryParam("model", "abc")
                                                                 .queryParam("price", 1)
                                                                 .queryParam("wheels", 1); 
                     Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

                    Response editProfileSuccess = invocationBuilder.post(null);

    }
}
