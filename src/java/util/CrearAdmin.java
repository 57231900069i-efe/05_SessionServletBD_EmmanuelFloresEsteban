package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import conexion.ConexionMySQL;

public class CrearAdmin {
    public static void main(String[] args) {
        String nombre = "Administrador";
        String apellido1 = "Sistema";
        String apellido2 = "Principal";
        String correo = "admin@utchilapa.edu.mx";
        String contrasena = "Admin123!";
        
        // Generar el hash
        String hash = PasswordUtil.hashPassword(contrasena);
        
        System.out.println("=== CREANDO/ACTUALIZANDO ADMINISTRADOR ===");
        System.out.println("Correo: " + correo);
        System.out.println("Contraseña: " + contrasena);
        System.out.println("Hash generado: " + hash);
        
        try {
            Connection con = ConexionMySQL.getConnection();
            
            // Verificar si ya existe
            String sqlCheck = "SELECT id_usuario FROM usuarios WHERE correo = ?";
            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setString(1, correo);
            var rs = psCheck.executeQuery();
            
            if (rs.next()) {
                // Actualizar existente
                String sqlUpdate = "UPDATE usuarios SET nombre=?, apellido1=?, apellido2=?, " +
                                 "contrasena=?, estado='activo', id_rol=1 WHERE correo=?";
                PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
                psUpdate.setString(1, nombre);
                psUpdate.setString(2, apellido1);
                psUpdate.setString(3, apellido2);
                psUpdate.setString(4, hash);
                psUpdate.setString(5, correo);
                psUpdate.executeUpdate();
                System.out.println("Administrador ACTUALIZADO exitosamente");
                psUpdate.close();
            } else {
                // Insertar nuevo
                String sqlInsert = "INSERT INTO usuarios (nombre, apellido1, apellido2, correo, " +
                                 "contrasena, estado, id_rol) VALUES (?, ?, ?, ?, ?, 'activo', 1)";
                PreparedStatement psInsert = con.prepareStatement(sqlInsert);
                psInsert.setString(1, nombre);
                psInsert.setString(2, apellido1);
                psInsert.setString(3, apellido2);
                psInsert.setString(4, correo);
                psInsert.setString(5, hash);
                psInsert.executeUpdate();
                System.out.println("Administrador CREADO exitosamente");
                psInsert.close();
            }
            
            rs.close();
            psCheck.close();
            con.close();
            
            System.out.println("==========================================");
            System.out.println("Ya puedes iniciar sesión con:");
            System.out.println("Correo: " + correo);
            System.out.println("Contraseña: " + contrasena);
            System.out.println("==========================================");
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}