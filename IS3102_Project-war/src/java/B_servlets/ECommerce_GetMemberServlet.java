/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import HelperClasses.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jiayu
 */
public class ECommerce_GetMemberServlet extends HttpServlet {
    
    
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            HttpSession s = request.getSession();
            String email = request.getParameter ("email");
            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://localhost:8080/SEPWebService-Student/webresources/entity.memberentity/getmemberbyemail")
                    .queryParam("email", email);

            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response cat = invocationBuilder.get();
            if (cat.getStatus() == 200) {
            Member member =cat.readEntity(Member.class);
            s.setAttribute("member", member);
           response.sendRedirect("/IS3102_Project-war/B/SG/memberlogin.jsp");
           }     
   }
}
