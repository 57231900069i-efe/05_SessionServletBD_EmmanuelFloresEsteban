package dao;

import conexion.ConexionMySQL;
import modelo.Usuario;
import util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOUsuario {
    

    
 public Usuario loginGeneral(String correo, String contrasena) {
    Usuario usuario = null;
    String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                "INNER JOIN roles r ON u.id_rol = r.id_rol " +
                "WHERE u.correo = ? AND u.contrasena = ? " +
                "AND u.estado = 'activo'";

    try {
        Connection con = ConexionMySQL.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, correo);
        ps.setString(2, PasswordUtil.hashPassword(contrasena));

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido1(rs.getString("apellido1"));
            usuario.setApellido2(rs.getString("apellido2"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setEstado(rs.getString("estado"));
            usuario.setIdRol(rs.getInt("id_rol"));
            usuario.setNombreRol(rs.getString("nombre_rol"));
        }

        rs.close();
        ps.close();
        con.close();

    } catch (SQLException e) {
        System.out.println("Error en loginGeneral: " + e.getMessage());
        e.printStackTrace();
    }

    return usuario;
}
    
    /**
     * Login para usuario normal
     * @param correo
     * @param contrasena
     * @return 
     */
    public Usuario login(String correo, String contrasena) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.id_rol = r.id_rol " +
                    "WHERE u.correo = ? AND u.contrasena = ? " +
                    "AND u.estado = 'activo' AND u.id_rol = 2";
        
        try {
            Connection con = ConexionMySQL.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, correo);
            ps.setString(2, PasswordUtil.hashPassword(contrasena));
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));
                usuario.setApellido2(rs.getString("apellido2"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
    
    /**
     * Registrar nuevo usuario
     * @param usuario
     * @return 
     */
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido1, apellido2, correo, contrasena, estado, id_rol, token_validacion) " +
                    "VALUES (?, ?, ?, ?, ?, 'inactivo', 2, ?)";
        
        try {
            Connection con = ConexionMySQL.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido1());
            ps.setString(3, usuario.getApellido2());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, PasswordUtil.hashPassword(usuario.getContrasena()));
            ps.setString(6, PasswordUtil.generateToken());
            
            int resultado = ps.executeUpdate();
            
            ps.close();
            con.close();
            
            return resultado > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Verificar si un correo ya existe
     */
    public boolean correoExiste(String correo) {
        String sql = "SELECT COUNT(*) as total FROM usuarios WHERE correo = ?";
        
        try {
            Connection con = ConexionMySQL.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, correo);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Listar
     * @return usuarios pendientes de activación
     */
    public ArrayList<Usuario> listarUsuariosPendientes() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.id_rol = r.id_rol " +
                    "WHERE u.estado = 'inactivo' " +
                    "ORDER BY u.fecha_registro DESC";
        
        try {
            Connection con = ConexionMySQL.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));
                usuario.setApellido2(rs.getString("apellido2"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
                lista.add(usuario);
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            System.out.println("Error al listar pendientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Activar un usuario
     */
    public boolean activarUsuario(int idUsuario) {
        String sql = "UPDATE usuarios SET estado = 'activo' WHERE id_usuario = ?";
        
        try {
            Connection con = ConexionMySQL.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idUsuario);
            int resultado = ps.executeUpdate();
            
            ps.close();
            con.close();
            
            return resultado > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al activar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Rechazar un usuario
     */
    public boolean rechazarUsuario(int idUsuario) {
        String sql = "UPDATE usuarios SET estado = 'rechazado' WHERE id_usuario = ?";
        
        try {
            Connection con = ConexionMySQL.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idUsuario);
            int resultado = ps.executeUpdate();
            
            ps.close();
            con.close();
            
            return resultado > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al rechazar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Buscar usuario por ID
     */
    public Usuario buscarPorId(int idUsuario) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.id_rol = r.id_rol " +
                    "WHERE u.id_usuario = ?";
        
        try {
            Connection con = ConexionMySQL.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idUsuario);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));
                usuario.setApellido2(rs.getString("apellido2"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
    /**
 * Listar todos los usuarios registrados
 */
public ArrayList<Usuario> listarTodosUsuarios() {
    ArrayList<Usuario> lista = new ArrayList<>();
    String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                "INNER JOIN roles r ON u.id_rol = r.id_rol " +
                "ORDER BY u.fecha_registro DESC";
    
    try {
        Connection con = ConexionMySQL.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido1(rs.getString("apellido1"));
            usuario.setApellido2(rs.getString("apellido2"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setEstado(rs.getString("estado"));
            usuario.setIdRol(rs.getInt("id_rol"));
            usuario.setNombreRol(rs.getString("nombre_rol"));
            lista.add(usuario);
        }
        
        rs.close();
        ps.close();
        con.close();
        
    } catch (SQLException e) {
        System.out.println("Error al listar todos los usuarios: " + e.getMessage());
        e.printStackTrace();
    }
    
    return lista;
}
}
