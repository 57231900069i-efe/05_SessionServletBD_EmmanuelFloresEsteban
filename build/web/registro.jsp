<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Sistema Escolar</title>
    <!-- Fuente profesional y moderna -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,400;14..32,500;14..32,600;14..32,700&display=swap" rel="stylesheet">
    <!-- Font Awesome para iconos (opcional, pero da un toque profesional) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="css/registro.css">
</head>
<body>
    <header>
        <div class="header-content">
            <h1>Sistema de Control Escolar</h1>
        </div>
    </header>
    
    <section class="registro-section">
        <h2>Registro de Usuario</h2>
        
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) { 
        %>
            <div class="mensaje-error"><%= error %></div>
        <% } %>
        
        <form method="post" action="SRegistro">
            <input type="hidden" name="accion" value="registrar">
            
            <div class="form-group">
                <label><i class="fas fa-user" style="margin-right: 6px; color: #64748b;"></i>Nombre</label>
                <input type="text" name="nombre" required placeholder="Nombre(s)" autocomplete="given-name">
            </div>
            
            <div class="form-group">
                <label><i class="fas fa-user" style="margin-right: 6px; color: #64748b;"></i>Primer Apellido</label>
                <input type="text" name="apellido1" required placeholder="Primer apellido" autocomplete="family-name">
            </div>
            
            <div class="form-group">
                <label><i class="fas fa-user" style="margin-right: 6px; color: #64748b;"></i>Segundo Apellido</label>
                <input type="text" name="apellido2" placeholder="Segundo apellido (opcional)" autocomplete="additional-name">
            </div>
            
            <div class="form-group">
                <label><i class="fas fa-envelope" style="margin-right: 6px; color: #64748b;"></i>Correo electrónico</label>
                <input type="email" name="correo" required placeholder="usuario@ejemplo.com" autocomplete="email">
            </div>
            
            <div class="form-group">
                <label><i class="fas fa-lock" style="margin-right: 6px; color: #64748b;"></i>Contraseña</label>
                <input type="password" name="contrasena" required placeholder="Mínimo 6 caracteres" autocomplete="new-password" minlength="6">
            </div>
            
            <div class="form-group">
                <label><i class="fas fa-check-circle" style="margin-right: 6px; color: #64748b;"></i>Confirmar Contraseña</label>
                <input type="password" name="confirmarContrasena" required placeholder="Repite la contraseña" autocomplete="new-password" minlength="6">
            </div>
            
            <button type="submit">
                <i class="fas fa-user-plus" style="margin-right: 8px;"></i> Registrarse
            </button>
        </form>
        
        <p>¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión</a></p>
    </section>
</body>
</html>