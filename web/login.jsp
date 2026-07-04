<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sistema Escolar</title>
    <!-- Fuente profesional y moderna -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,400;14..32,500;14..32,600;14..32,700&display=swap" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
    <header>
        <div class="header-content">
            <h1>Universidad Tecnológica de Chilapa</h1>
            <h2>Sistema de Control Escolar</h2>
        </div>
    </header>

    <section class="login-container">
        <%
            String error = (String) request.getAttribute("error");
            String exito = (String) request.getAttribute("exito");

            if (error != null) {
        %>
            <div class="mensaje-error">
                <strong>Error:</strong> <%= error %>
            </div>
        <%
            }
            if (exito != null) {
        %>
            <div class="mensaje-exito">
                <strong>Éxito:</strong> <%= exito %>
            </div>
        <% } %>

        <div class="login-forms">
            <div class="login-box user-login">
                <h3>
                    <i class="fas fa-sign-in-alt" style="margin-right: 10px; color: #2a4f7e;"></i>
                    Iniciar Sesión
                </h3>
                <form method="post" action="SLogin">
                    <input type="hidden" name="accion" value="login">

                    <div class="form-group">
                        <label>
                            <i class="fas fa-envelope" style="margin-right: 6px; color: #64748b;"></i>
                            Correo electrónico
                        </label>
                        <input type="email" name="correo" required
                               placeholder="tucorreo@ejemplo.com" autocomplete="email">
                    </div>

                    <div class="form-group">
                        <label>
                            <i class="fas fa-lock" style="margin-right: 6px; color: #64748b;"></i>
                            Contraseña
                        </label>
                        <input type="password" name="contrasena" required
                               placeholder="Tu contraseña" autocomplete="current-password">
                    </div>

                    <button type="submit" class="btn-user">
                        <i class="fas fa-sign-in-alt" style="margin-right: 8px;"></i>
                        Iniciar Sesión
                    </button>
                </form>

                <div class="registro-link">
                    <p>¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a></p>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <p>© 2026 Universidad Tecnológica de Chilapa. Todos los derechos reservados.</p>
    </footer>
</body>
</html>