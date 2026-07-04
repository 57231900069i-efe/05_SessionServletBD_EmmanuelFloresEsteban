<%-- 
    Document   : index
    Created on : 26 jun 2026, 21:44:19
    Author     : Emmanuel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Alumno"%>
<%@page import="dao.DAOAlumno"%>
<%! 
    DAOAlumno lista = new DAOAlumno();
    Alumno edit = null;
%>
<%
    edit = null;
    if (request.getAttribute("edit") != null)
    {
        edit = (Alumno) request.getAttribute("edit");
    }
    String nombreUsuario = (String) session.getAttribute("nombreCompleto");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Control - Sistema Escolar</title>
    <!-- Fuente profesional -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,400;14..32,500;14..32,600;14..32,700&display=swap" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="css/alumnos.css">
</head>
<body>
    <header>
        <div class="header-content">
            <img src="img/logo.png" class="logo" alt="Logo">
            <img src="img/saice.png" class="saice-logo" alt="SAICE">
            <div class="header-usuario">
                <% if (nombreUsuario != null) { %>
                    <span class="nombre-usuario"><i class="fas fa-user-circle"></i> Hola, <%= nombreUsuario %></span>
                    <a href="SLogin?accion=logout" class="btn-logout" onclick="return confirm('¿Cerrar sesión?');">
                        <i class="fas fa-sign-out-alt"></i> Cerrar sesión
                    </a>
                <% } %>
            </div>
        </div>
    </header>

    <section class="titulo-principal">
        <hr>
        <h1>Bienvenido</h1>
        <h2>Al Centro de Control Escolar</h2>
        <hr>
    </section>

    <div class="main-content">
        <div id="form_registro">
            <h2><%= (edit != null) ? "Modificar calificaciones" : "Registro de calificaciones"%></h2>
            <form method="post" action="SAlumno">
                <input type="hidden" name="accion" id="accion" value="<%= (edit != null) ? "Modificar" : "Agregar"%>">
                <input type="hidden" name="tfNLOld" id="tfNLOld" value="<%= (edit != null) ? edit.getNL() : ""%>">
                
                <input type="text" name="tfNL" id="tfNL" value="<%= (edit != null) ? edit.getNL() : ""%>" placeholder="NL" required>
                <input type="text" name="tfNombre" id="tfNombre" value="<%= (edit != null) ? edit.getNombre() : ""%>" placeholder="Nombre" required>
                <input type="text" name="tfPaterno" id="tfPaterno" value="<%= (edit != null) ? edit.getPaterno() : ""%>" placeholder="Apellido Paterno" required>
                <input type="text" name="tfMaterno" id="tfMaterno" value="<%= (edit != null) ? edit.getMaterno() : ""%>" placeholder="Apellido Materno" required>
                
                <input type="submit" id="btnAccion" value="<%= (edit != null) ? "Modificar" : "Agregar"%>"/>
            </form>
        </div>
            
        <h2>Lista de Alumnos</h2>
        <%=lista.mostrar()%>
    </div>
    
    <script src="index.js"></script>
    
    <footer>
        <div class="footer-content">
            <div class="footer-seccion">
                <h3><i class="fas fa-map-marker-alt"></i> Contáctanos</h3>
                <p>Carretera Chilapa-Zitlala S/N</p>
                <p>Tel: 756 475 2111</p>
                <p>Email: contacto@utchilapa.edu.mx</p>
                <p>Horario: Lunes a Viernes 8:00 - 19:00</p>
            </div>
            <div class="footer-seccion">
                <h3><i class="fas fa-university"></i> Unidad Académica</h3>
                <p>Universidad Tecnológica de Chilapa</p>
                <p>Sistema de Control Escolar</p>
                <img src="img/logowhite.png" alt="Logo UT">
            </div>
            <div class="footer-seccion">
                <h3><i class="fas fa-link"></i> Enlaces Rápidos</h3>
                <p>➤ Portal del Estudiante</p>
                <p>➤ Calificaciones</p>
                <p>➤ Sistema SAICE</p>
                <p>➤ Avisos y Comunicados</p>
            </div>
        </div>
        <div class="footer-copyright">
            <p>© 2026 Universidad Tecnológica de Chilapa. Todos los derechos reservados.</p>
        </div>
    </footer>
</body>
</html>