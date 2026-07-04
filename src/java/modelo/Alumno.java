package modelo;

public class Alumno 
{
    private int nl;
    private String nombre;
    private String paterno;
    private String materno;


    public Alumno()
    {
        nl = 0;
        nombre = "";
        paterno = "";
        materno = "";
    }

   public Alumno(int nl, String nombre, String paterno, String materno)
   {
        this.nl = nl;
        this.nombre = nombre;
        this.paterno = paterno ;
        this.materno = materno ;
    }

    public int getNL() 
    {
        return nl;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public String getPaterno() 
    {
        return paterno;
    }

    public String getMaterno() 
    {
        return materno;
    }

    public void setNL(int nl) 
    {
        this.nl = nl;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public void setPaterno(String paterno) 
    {
        this.paterno = paterno;
    }

    public void setMaterno(String materno) 
    {
        this.materno = materno;
    }

}