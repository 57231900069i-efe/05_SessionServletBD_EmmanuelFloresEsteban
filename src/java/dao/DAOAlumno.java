/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelo.Alumno;
import conexion.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Emmanuel
 */
public class DAOAlumno 
{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Alumno alumno;
    
    public ArrayList<Alumno> listar()
    {
        ArrayList<Alumno>list = new ArrayList<>();
        String sql="SELECT * FROM alumnos";
        try
         {
                 con=ConexionMySQL.getConnection();
                 ps=con.prepareStatement(sql);
                 rs=ps.executeQuery();
                 while(rs.next())
                 {
                          alumno=new Alumno();
                          alumno.setNL(rs.getInt("NL"));
                          alumno.setNombre(rs.getString("Nombre"));
                          alumno.setPaterno(rs.getString("Paterno"));
                          alumno.setMaterno(rs.getString("Materno"));
                          list.add(alumno);
                 }
                 rs.close();
                 ps.close();
                 con.close();
         }
                 catch (SQLException e) {}
                 return list;
                
     }
        
   
public String mostrar()
{
    String r, fila;
    r = """
        <div class="table-container">
            <table class="tabla-alumnos">
                <thead>
                    <tr>
                        <th><i class="fas fa-hashtag"></i> NL</th>
                        <th><i class="fas fa-user"></i> Nombre</th>
                        <th><i class="fas fa-user"></i> Paterno</th>
                        <th><i class="fas fa-user"></i> Materno</th>
                        <th colspan="2" class="text-center"><i class="fas fa-cog"></i> Opciones</th>
                    </tr>
                </thead>
                <tbody>
    """;
    
    for(Alumno reg : listar())
    {
        fila = """
            <tr class="fila-alumno">
                <td class="nl-cell">%d</td>
                <td>%s</td>
                <td>%s</td>
                <td>%s</td>
                <td class="accion-cell">
                    <form method="post" class="form-accion">
                        <input type="hidden" name="accion" value="Editar">
                        <input type="hidden" name="tfNL" value="%d">
                        <button type="submit" class="btn-editar">
                            <i class="fas fa-edit"></i> Editar
                        </button>
                    </form>
                </td>
                <td class="accion-cell">
                    <form method="post" class="form-accion">
                        <input type="hidden" name="accion" value="Eliminar">
                        <input type="hidden" name="tfNL" value="%d">
                        <button type="submit" class="btn-eliminar" onclick="return confirm('¿Estás seguro de eliminar este registro?');">
                            <i class="fas fa-trash-alt"></i> Eliminar
                        </button>
                    </form>
                </td>
            </tr>
        """;
        r = r + String.format(fila, reg.getNL(), reg.getNombre(), reg.getPaterno(), reg.getMaterno(), reg.getNL(), reg.getNL());
    }
    
    r = r + """
                </tbody>
            </table>
        </div>
    """;
    return r;
}
     
     public Alumno buscar(int nL)
     {
         alumno = null;
         String sql="SELECT * FROM alumnos WHERE NL = " + nL;
         try
         {
                 con=ConexionMySQL.getConnection();
                 ps=con.prepareStatement(sql);
                 rs=ps.executeQuery();
                 
                 while(rs.next())
                 {
                         alumno=new Alumno();
                         alumno.setNL(rs.getInt("NL"));
                         alumno.setNombre(rs.getString("Nombre"));
                         alumno.setPaterno(rs.getString("Paterno"));
                         alumno.setMaterno(rs.getString("Materno"));
                 }
                 rs.close();
                 ps.close();
                 con.close();
         } catch (SQLException e) {}
                 return alumno;
     }
     
     public boolean agregar (Alumno alumno)
     {
        String sql = "INSERT INTO alumnos VALUES(" +
                alumno.getNL() + "," +
                "'" + alumno.getNombre() + "'," +
                "'" + alumno.getPaterno() + "'," +
                "'" + alumno.getMaterno() + "')";
        try
        {
                 con=ConexionMySQL.getConnection();
                 ps=con.prepareStatement(sql);
                 ps.executeUpdate();
                 ps.close();
                 con.close();
        } catch (SQLException e) { }
        return true;
     }
     
     public boolean editar (Alumno alumno, int old)
     {
        String sql = "UPDATE alumnos SET " +
                "NL = " + alumno.getNL() + ", " +
                "Nombre = '" + alumno.getNombre() + "', " +
                "Paterno = '" + alumno.getPaterno() + "', " +
                "Materno = '" + alumno.getMaterno() + "' " +
                "WHERE NL = " + old;
        try
        {
                 con=ConexionMySQL.getConnection();
                 ps=con.prepareStatement(sql);
                 ps.executeUpdate();
                 ps.close();
                 con.close();
        }
        catch (SQLException e) {}
        return true;
     }
     
     public boolean eliminar(int nL)
     {
        String sql="DELETE FROM alumnos WHERE NL = " + nL;
        try
        {
                 con=ConexionMySQL.getConnection();
                 ps=con.prepareStatement(sql);
                 ps.executeUpdate();
                 ps.close();
                 con.close();                 
        }
        catch (SQLException e) {}
        return true;
     }
}
