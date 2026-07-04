package modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String contrasena;
    private String estado; // activo, rechazado, inactivo
    private int idRol; // 1 = superusuario, 2 = usuario
    private String nombreRol; // Para mostrar en vistas
    private String tokenValidacion;
    
    // Constructor vacío
    public Usuario() {
        this.idUsuario = 0;
        this.nombre = "";
        this.apellido1 = "";
        this.apellido2 = "";
        this.correo = "";
        this.contrasena = "";
        this.estado = "inactivo";
        this.idRol = 2;
        this.nombreRol = "";
        this.tokenValidacion = "";
    }
    
    // Constructor con parámetros básicos
    public Usuario(String nombre, String apellido1, String apellido2, 
                   String correo, String contrasena) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.correo = correo;
        this.contrasena = contrasena;
        this.estado = "inactivo";
        this.idRol = 2;
    }
    
    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido1() {
        return apellido1;
    }
    
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    
    public String getApellido2() {
        return apellido2;
    }
    
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getIdRol() {
        return idRol;
    }
    
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }
    
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
    public String getTokenValidacion() {
        return tokenValidacion;
    }
    
    public void setTokenValidacion(String tokenValidacion) {
        this.tokenValidacion = tokenValidacion;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido1 + 
               (apellido2 != null && !apellido2.isEmpty() ? " " + apellido2 : "");
    }
    
    public boolean isSuperUsuario() {
        return idRol == 1;
    }
    
    public boolean isActivo() {
        return "activo".equals(estado);
    }
}