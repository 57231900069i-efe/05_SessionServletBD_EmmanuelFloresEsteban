package control;

import dao.DAOUsuario;
import modelo.Usuario;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SLogin")
public class Servlet_Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        switch (accion) {
            case "login":
                login(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                response.sendRedirect("login.jsp");
                break;
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        System.out.println("=== INTENTO DE LOGIN ===");
        System.out.println("Correo: " + correo);

        if (correo == null || contrasena == null ||
            correo.trim().isEmpty() || contrasena.trim().isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
            return;
        }

        DAOUsuario dao = new DAOUsuario();

        // Un solo método: no importa si es admin o usuario normal,
        // el rol real se lee de la base de datos.
        Usuario usuario = dao.loginGeneral(correo, contrasena);

        if (usuario != null) {
            System.out.println("Login exitoso para: " + usuario.getNombreCompleto()
                    + " - Rol: " + usuario.getNombreRol());

            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreCompleto", usuario.getNombreCompleto());
            session.setAttribute("rol", usuario.getNombreRol());

            // Redirección automática según el rol guardado en la BD
            if (usuario.getIdRol() == 1) {
                System.out.println("Redirigiendo a admin.jsp");
                response.sendRedirect("admin.jsp");
            } else {
                System.out.println("Redirigiendo a alumnos.jsp");
                response.sendRedirect("alumnos.jsp");
            }
        } else {
            System.out.println("Login fallido");
            request.setAttribute("error", "Credenciales inválidas o cuenta no activada");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
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