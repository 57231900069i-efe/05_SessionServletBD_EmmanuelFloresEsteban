package control;

import dao.DAOUsuario;
import modelo.Usuario;
import util.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SAdmin", urlPatterns = {"/SAdmin"})
public class Servlet_Admin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario admin = (Usuario) session.getAttribute("usuario");
        if (admin == null || admin.getIdRol() != 1) {
            response.sendRedirect("login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        String idStr = request.getParameter("id");

        System.out.println("=== SAdmin - Acción: " + accion + " ===");
        System.out.println("ID: " + idStr);

        if (accion == null || idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("error", "Datos incompletos");
            response.sendRedirect("admin.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            DAOUsuario dao = new DAOUsuario();

            // Obtenemos los datos del usuario ANTES de cambiar su estado,
            // para poder enviarle el correo con su nombre y dirección.
            Usuario usuario = dao.buscarPorId(id);

            if (usuario == null) {
                session.setAttribute("error", "Usuario no encontrado");
                response.sendRedirect("admin.jsp");
                return;
            }

            String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido1();

            if ("activar".equals(accion)) {
                boolean resultado = dao.activarUsuario(id);
                if (resultado) {
                    session.setAttribute("exito", "Usuario activado exitosamente");
                    System.out.println("Usuario " + id + " activado correctamente");

                    boolean correoEnviado = EmailService.enviarCorreoActivacion(usuario.getCorreo(), nombreCompleto);
                    if (!correoEnviado) {
                        System.out.println("Advertencia: el usuario fue activado pero el correo no se pudo enviar");
                    }
                } else {
                    session.setAttribute("error", "Error al activar usuario");
                }
            } else if ("rechazar".equals(accion)) {
                boolean resultado = dao.rechazarUsuario(id);
                if (resultado) {
                    session.setAttribute("exito", "Usuario rechazado");
                    System.out.println("Usuario " + id + " rechazado correctamente");

                    boolean correoEnviado = EmailService.enviarCorreoRechazo(usuario.getCorreo(), nombreCompleto);
                    if (!correoEnviado) {
                        System.out.println("Advertencia: el usuario fue rechazado pero el correo no se pudo enviar");
                    }
                } else {
                    session.setAttribute("error", "Error al rechazar usuario");
                }
            } else {
                session.setAttribute("error", "Acción no válida: " + accion);
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID de usuario no válido");
            System.out.println("Error: ID no es un número - " + idStr);
        } catch (Exception e) {
            session.setAttribute("error", "Error: " + e.getMessage());
            System.out.println("Error en SAdmin: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect("admin.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("admin.jsp");
    }
}