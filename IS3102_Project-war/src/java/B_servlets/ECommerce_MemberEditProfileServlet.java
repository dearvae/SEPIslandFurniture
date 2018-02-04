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
import javax.ws.rs.client.Entity;
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
@EJB
    private AccountManagementBeanLocal accountManagementBean;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String result = "";
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String newPassword = request.getParameter("password");
        String phone = request.getParameter("phone");
        String country = request.getParameter("country");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        int securityQuestion = Integer.parseInt(request.getParameter("securityQuestion"));
        String securityAnswer = request.getParameter("securityAnswer");
        int age = Integer.parseInt(request.getParameter("age"));
        int income = Integer.parseInt(request.getParameter("income"));
        Entity<?> empty = Entity.text("");
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
        Response editProfileSuccess = invocationBuilder.put(empty);
       
  
        HttpSession s = request.getSession();
          
 
        // not sure need to check
        if (editProfileSuccess.getStatus() == 200) {
        Member member = new Member();
        member.setName(name);
        member.setPhone(phone);
        member.setCity(country);
        member.setAddress(address);
        member.setSecurityQuestion(securityQuestion);
        member.setSecurityAnswer(securityAnswer);
        member.setAge(age);
        member.setIncome(income);
        member.setEmail(email);
        member.setId(id);
        s.setAttribute("member", member);
            result = "Account updated successfully";
          
         if(newPassword!=null&&!newPassword.equals("")){
                
                accountManagementBean.resetMemberPassword(email, newPassword);
                result = "Account updated successfully. Reset Password Successful.";
           }
            response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp?goodMsg=" + result);
        } else {
            
            result = "Account updated unsuccessfully";
            response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp?errMsg=" + result);
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
