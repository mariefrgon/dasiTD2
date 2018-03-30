/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mfgonzalezf
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String action = request.getParameter("action");
            
            
                Service s = new Service();  
            if(action.equals("ConsulterDetailsPersonne")){
                Service.Personne p = s.consulterDetailPersonne(Long.parseLong(request.getParameter("id")));
                detailPersonne(out,p);
            }else{
                List<Service.Personne> p = s.consulterListePersonnes();
                printListePersonne(out,p);
            } 
           
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

    private static void printListePersonne(PrintWriter out, List<Service.Personne> pers) {
    
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = new JsonArray();
        
        for(Service.Personne p : pers){
            JsonObject jsonPersonne = new JsonObject();
            jsonPersonne.addProperty("id", p.getId());
            jsonPersonne.addProperty("civilite", p.getCivilite());
            jsonPersonne.addProperty("nom", p.getNom());
            jsonPersonne.addProperty("prenom", p.getPrenom());
            jsonPersonne.addProperty("mail", p.getMail());
            jsonPersonne.addProperty("adresse", p.getAdresse());
            jsonList.add(jsonPersonne);
        }
        JsonObject container = new JsonObject();
        container.add("personnes", jsonList);
        out.println(gson.toJson(container));
    
    }
    
    private static void detailPersonne(PrintWriter out, Service.Personne p) {
    
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        JsonObject jsonPersonne = new JsonObject();
        jsonPersonne.addProperty("id", p.getId());
        jsonPersonne.addProperty("civilite", p.getCivilite());
        jsonPersonne.addProperty("nom", p.getNom());
        jsonPersonne.addProperty("prenom", p.getPrenom());
        jsonPersonne.addProperty("dateNaissance", Service.USER_DATE_FORMAT.format(p.getDateNaissance()));
        jsonPersonne.addProperty("mail", p.getMail());
        jsonPersonne.addProperty("adresse", p.getAdresse());
        
        JsonObject container = new JsonObject();
        container.add("personne", jsonPersonne);
        out.println(gson.toJson(container));
    
    }

}
