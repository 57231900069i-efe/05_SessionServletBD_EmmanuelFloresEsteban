/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import dao.DAOAlumno;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import java.util.logging.Level;
import modelo.Alumno;

/**
 *
 * @author Emmanuel
 */
@WebServlet("/SAlumno") public class Servlet_Alumno extends HttpServlet {
    private Alumno alumno;
    private DAOAlumno dao;
    
 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    Alumno edit = null;
    
    try {
        String accion = request.getParameter("accion");
        
        if ("Agregar".equals(accion)) {
            alumno = new Alumno();
            alumno.setNL(Integer.parseInt(request.getParameter("tfNL")));
            alumno.setNombre(request.getParameter("tfNombre"));
            alumno.setPaterno(request.getParameter("tfPaterno"));
            alumno.setMaterno(request.getParameter("tfMaterno"));
            
            dao = new DAOAlumno();
            dao.agregar(alumno);
            
            request.setAttribute("edit", null);
            
        } else if ("Editar".equals(accion)) {
            dao = new DAOAlumno();
            edit = dao.buscar(Integer.parseInt(request.getParameter("tfNL")));
            
            request.setAttribute("edit", edit);
            
        } else if ("Modificar".equals(accion)) {
            alumno = new Alumno();
            alumno.setNL(Integer.parseInt(request.getParameter("tfNL")));
            alumno.setNombre(request.getParameter("tfNombre"));
            alumno.setPaterno(request.getParameter("tfPaterno"));
            alumno.setMaterno(request.getParameter("tfMaterno"));
            
            dao = new DAOAlumno();
            dao.editar(alumno, Integer.parseInt(request.getParameter("tfNLOld")));
            
            request.setAttribute("edit", null);
            
        } else if ("Eliminar".equals(accion)) {
            int nl = Integer.parseInt(request.getParameter("tfNL"));
            
            dao = new DAOAlumno();
            dao.eliminar(nl);
            
            request.setAttribute("edit", null);
        }
        
        // Redirección común para todos los casos
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/alumnos.jsp");
        rd.forward(request, response);
        
    } catch (Exception ex) {
        Logger.getLogger(Servlet_Alumno.class.getName()).log(Level.SEVERE, null, ex);
        request.setAttribute("edit", null);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/alumnos.jsp");
        rd.forward(request, response);
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
