<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="util.PasswordUtil"%>
<%@page import="conexion.ConexionMySQL"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Crear Administrador</title>
        <style>
            body { font-family: Arial; margin: 50px; }
            .exito { color: green; font-weight: bold; }
            .error { color: red; font-weight: bold; }
        </style>
    </head>
    <body>
        <h1>Crear/Actualizar Administrador</h1>
        
        <%
            String accion = request.getParameter("accion");
            
            if ("crear".equals(accion)) {
                String nombre = "Administrador";
                String apellido1 = "Sistema";
                String apellido2 = "Principal";
                String correo = "admin@utchilapa.edu.mx";
                String contrasena = "Admin123!";
                String hash = PasswordUtil.hashPassword(contrasena);
                
                try {
                    Connection con = ConexionMySQL.getConnection();
                    
                    // Eliminar admin anterior si existe
                    PreparedStatement psDelete = con.prepareStatement(
                        "DELETE FROM usuarios WHERE correo = ?"
                    );
                    psDelete.setString(1, correo);
                    psDelete.executeUpdate();
                    psDelete.close();
                    
                    // Insertar nuevo admin
                    PreparedStatement psInsert = con.prepareStatement(
                        "INSERT INTO usuarios (nombre, apellido1, apellido2, correo, " +
                        "contrasena, estado, id_rol) VALUES (?, ?, ?, ?, ?, 'activo', 1)"
                    );
                    psInsert.setString(1, nombre);
                    psInsert.setString(2, apellido1);
                    psInsert.setString(3, apellido2);
                    psInsert.setString(4, correo);
                    psInsert.setString(5, hash);
                    psInsert.executeUpdate();
                    psInsert.close();
                    con.close();
                    
                    out.println("<p class='exito'>✓ Administrador creado exitosamente</p>");
                    out.println("<p><strong>Correo:</strong> " + correo + "</p>");
                    out.println("<p><strong>Contraseña:</strong> " + contrasena + "</p>");
                    out.println("<p><strong>Hash:</strong> " + hash + "</p>");
                    out.println("<p><a href='login.jsp'>Ir al Login</a></p>");
                    
                } catch (Exception e) {
                    out.println("<p class='error'>✗ Error: " + e.getMessage() + "</p>");
                }
            }
        %>
        
        <form method="post">
            <input type="hidden" name="accion" value="crear">
            <button type="submit" style="padding: 10px 20px; font-size: 16px;">
                Crear Administrador por Defecto
            </button>
        </form>
        
        <div style="margin-top: 20px; padding: 10px; background: #f0f0f0;">
            <p><strong>Datos del administrador:</strong></p>
            <p>Correo: admin@utchilapa.edu.mx</p>
            <p>Contraseña: Admin123!</p>
        </div>
    </body>
</html>