<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>
<%@page import="dao.DAOUsuario"%>
<%@page import="java.util.ArrayList"%>

<%
    // Verificar que haya sesión de superusuario
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || usuario.getIdRol() != 1) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Cargar usuarios
    DAOUsuario daoUsuario = new DAOUsuario();
    ArrayList<Usuario> pendientes = daoUsuario.listarUsuariosPendientes();
    ArrayList<Usuario> todosUsuarios = daoUsuario.listarTodosUsuarios();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración</title>
    <!-- Fuente profesional -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,400;14..32,500;14..32,600;14..32,700&display=swap" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>
    <header>
        <div class="header-content">
            <h1>
                <i class="fas fa-shield-alt"></i>
                Panel de Administración
            </h1>
            <div class="user-info">
                <p>
                    <i class="fas fa-user-circle"></i>
                    Bienvenido, <strong><%= session.getAttribute("nombreCompleto") %></strong>
                </p>
                <p>
                    <i class="fas fa-user-tag"></i>
                    Rol: <strong><%= session.getAttribute("rol") %></strong>
                </p>
                <nav class="nav-links">
                    <a href="alumnos.jsp">
                        <i class="fas fa-book"></i> Alumnos
                    </a>
                    <a href="SLogin?accion=logout" class="btn-logout">
                        <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
                    </a>
                </nav>
            </div>
        </div>
    </header>
    
    <main class="admin-main">
        <% 
            // Mostrar mensajes de sesión
            String exito = (String) session.getAttribute("exito");
            String error = (String) session.getAttribute("error");
            
            if (error != null) { 
        %>
            <div class="mensaje-error"><%= error %></div>
        <% 
                session.removeAttribute("error");
            }
            if (exito != null) { 
        %>
            <div class="mensaje-exito"><%= exito %></div>
        <% 
                session.removeAttribute("exito");
            }
        %>
        
        <!-- Sección de usuarios pendientes -->
        <section class="admin-section">
            <h2>
                <i class="fas fa-clock"></i>
                Usuarios Pendientes de Activación
            </h2>
            
            <% if (pendientes != null && !pendientes.isEmpty()) { %>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th><i class="fas fa-hashtag"></i> ID</th>
                                <th><i class="fas fa-user"></i> Nombre Completo</th>
                                <th><i class="fas fa-envelope"></i> Correo</th>
                                <th><i class="fas fa-circle"></i> Estado</th>
                                <th><i class="fas fa-cog"></i> Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Usuario u : pendientes) { %>
                                <tr>
                                    <td><strong>#<%= u.getIdUsuario() %></strong></td>
                                    <td><%= u.getNombre() %> <%= u.getApellido1() %> <%= u.getApellido2() %></td>
                                    <td><%= u.getCorreo() %></td>
                                    <td><span class="estado-inactivo"><i class="fas fa-clock"></i> <%= u.getEstado() %></span></td>
                                    <td>
                                        <form method="post" action="SAdmin" style="display:inline;">
                                            <input type="hidden" name="accion" value="activar">
                                            <input type="hidden" name="id" value="<%= u.getIdUsuario() %>">
                                            <button type="submit" class="btn-accion btn-activar" onclick="return confirm('¿Activar este usuario?');">
                                                <i class="fas fa-check-circle"></i> Aceptar
                                            </button>
                                        </form>
                                        <form method="post" action="SAdmin" style="display:inline;">
                                            <input type="hidden" name="accion" value="rechazar">
                                            <input type="hidden" name="id" value="<%= u.getIdUsuario() %>">
                                            <button type="submit" class="btn-accion btn-rechazar" onclick="return confirm('¿Rechazar este usuario?');">
                                                <i class="fas fa-times-circle"></i> Rechazar
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <p class="no-data">
                    <i class="fas fa-check-circle" style="color: #15803d;"></i>
                    No hay usuarios pendientes de activación.
                </p>
            <% } %>
        </section>
        
        <!-- Sección de todos los usuarios -->
        <section class="admin-section">
            <h2>
                <i class="fas fa-users"></i>
                Todos los Usuarios Registrados
            </h2>
            
            <% if (todosUsuarios != null && !todosUsuarios.isEmpty()) { %>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th><i class="fas fa-hashtag"></i> ID</th>
                                <th><i class="fas fa-user"></i> Nombre Completo</th>
                                <th><i class="fas fa-envelope"></i> Correo</th>
                                <th><i class="fas fa-circle"></i> Estado</th>
                                <th><i class="fas fa-user-tag"></i> Rol</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Usuario u : todosUsuarios) { %>
                                <tr>
                                    <td><strong>#<%= u.getIdUsuario() %></strong></td>
                                    <td><%= u.getNombre() %> <%= u.getApellido1() %> <%= u.getApellido2() %></td>
                                    <td><%= u.getCorreo() %></td>
                                    <td>
                                        <span class="estado-<%= u.getEstado() %>">
                                            <% if ("activo".equals(u.getEstado())) { %>
                                                <i class="fas fa-check-circle"></i>
                                            <% } else if ("inactivo".equals(u.getEstado())) { %>
                                                <i class="fas fa-clock"></i>
                                            <% } else { %>
                                                <i class="fas fa-times-circle"></i>
                                            <% } %>
                                            <%= u.getEstado() %>
                                        </span>
                                    </td>
                                    <td><%= u.getNombreRol() %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <p class="no-data">
                    <i class="fas fa-info-circle"></i>
                    No hay usuarios registrados.
                </p>
            <% } %>
        </section>
    </main>
    
    <footer>
        <p>© 2026 Universidad Tecnológica de Chilapa. Todos los derechos reservados.</p>
    </footer>
</body>
</html>