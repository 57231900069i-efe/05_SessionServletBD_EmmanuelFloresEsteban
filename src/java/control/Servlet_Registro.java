package control;

import dao.DAOUsuario;
import modelo.Usuario;
import util.EmailService;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SRegistro")
public class Servlet_Registro extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if ("registrar".equals(accion)) {
            registrar(request, response);
        }
    }
    
    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String apellido1 = request.getParameter("apellido1");
        String apellido2 = request.getParameter("apellido2");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String confirmarContrasena = request.getParameter("confirmarContrasena");
        
        // Validaciones
        String error = null;
        
        if (nombre == null || nombre.trim().isEmpty() ||
            apellido1 == null || apellido1.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            contrasena == null || contrasena.trim().isEmpty()) {
            error = "Todos los campos son obligatorios";
        } else if (!contrasena.equals(confirmarContrasena)) {
            error = "Las contraseñas no coinciden";
        } else if (contrasena.length() < 6) {
            error = "La contraseña debe tener al menos 6 caracteres";
        } else {
            DAOUsuario dao = new DAOUsuario();
            if (dao.correoExiste(correo)) {
                error = "El correo electrónico ya está registrado";
            }
        }
        
        if (error != null) {
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("registro.jsp");
            rd.forward(request, response);
            return;
        }
        
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido1(apellido1);
        usuario.setApellido2(apellido2 != null ? apellido2 : "");
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        
        DAOUsuario dao = new DAOUsuario();
        boolean registrado = dao.registrar(usuario);
        
        if (registrado) {
            // Enviar correo de registro exitoso
            String nombreCompleto = nombre + " " + apellido1;
            EmailService.enviarCorreoRegistro(correo, nombreCompleto);
            
            request.setAttribute("exito", "Registro exitoso. Espera a que un administrador active tu cuenta.");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("error", "Error al registrar. Intenta de nuevo.");
            RequestDispatcher rd = request.getRequestDispatcher("registro.jsp");
            rd.forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}