/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import CommonInfrastructure.AccountManagement.AccountManagementBeanLocal;
import CommonInfrastructure.SystemSecurity.SystemSecurityBeanLocal;
import HelperClasses.Member;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author BN
 */
@WebServlet(name = "ECommerce_MemberEditProfileServlet", urlPatterns = {"/ECommerce_MemberEditProfileServlet"})
public class ECommerce_MemberEditProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        HttpSession s = request.getSession();
//           String id = (String) s.getAttribute("id");
//            String name = (String) s.getAttribute("name");
//           String phone = (String) s.getAttribute("phone");
//        String country = (String) s.getAttribute("country");
//        String address = (String) s.getAttribute("address");
//        int securityQuestion = (int) s.getAttribute("securityQuestion");
//        String securityAnswer = (String) s.getAttribute("securityAnswer");
//        int age = (int) s.getAttribute("age");
//        double income =(double) s.getAttribute("income");
        String result = "";
//
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String country = request.getParameter("country");
        String address = request.getParameter("address");
        int securityQuestion = Integer.parseInt(request.getParameter("securityQuestion"));
        String securityAnswer = request.getParameter("securityAnswer");
        int age = Integer.parseInt(request.getParameter("age"));
        double income = Double.parseDouble(request.getParameter("income"));

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/SEPWebService-Student/webresources/entity.memberentity/updatemember")
                .queryParam("id", id)
                .queryParam("name", name)
                .queryParam("phone", phone)
                .queryParam("country", country)
                .queryParam("address", address)
                .queryParam("securityQuestion", securityQuestion)
                .queryParam("securityAnswer", securityAnswer)
                .queryParam("age", age)
                .queryParam("income", income);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response editProfileSuccess = invocationBuilder.put;

        // not sure need to check
        if (editProfileSuccess.getStatus() == 200) {
            result = "Account updated successfully";
            response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp?goodMsg=" + result);
        } else {
            result = "Account updated unsuccessfully";
            response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp?errMsg=" + result);
        }

    }
}
