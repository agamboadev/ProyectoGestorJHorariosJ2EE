package base_Datos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import modelo.GestorBD;
import modelo.Horario;
import modelo.Persona;
import modelo.Persona_Curso;
import utils.ServletUtil;

import com.mysql.jdbc.Connection;

public class Persona_CursoDAO {
	private Connection con;
    private PrintWriter out;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private String ruta;
 // Para poder añadir el nombre de la persona que ha realizado las operaciones
    private String strUsuario; 
    
    public Persona_CursoDAO () {
    	this.conexion();
    	strUsuario = "Desconocido";
    }

    public Persona_CursoDAO(String usuario){
    	strUsuario = usuario;
    	this.conexion();
	}
    
    private void conexion() {
    	try {
			con =  (Connection) ConnectionFactory.getInstance().getConnection();
			File carpeta = new File(GestorBD.NOMBRE_CARPETA_LOG);
			if (!carpeta.isDirectory()) {
				if (carpeta.mkdirs()) {
					System.out.println("Directorio " + carpeta.toString() + " creado correctamente.");
				}
			}
			out=new PrintWriter(new FileWriter(carpeta + "/" + GestorBD.NOMBRE_FICHERO_LOG,true));//
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Vector<Persona_Curso> buscarPersonasDeCurso (int idCurso) {
    	Vector<Persona_Curso> vPersona_curso = new Vector<Persona_Curso>();
    	Persona_Curso persona_curso = null;
    	System.out.println("Datos que se le pasan al metodo buscarPersonasDeCurso: ");
		System.out.println("-- idCurso: " + idCurso);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERSONA_CURSO WHERE IDCURSO=?");
			pst.setInt(1, idCurso);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				persona_curso = new Persona_Curso();
				persona_curso.setIdPersona(rs.getInt("IDPERSONA"));
				persona_curso.setIdPersona(rs.getInt("IDCURSO"));
				vPersona_curso.add(persona_curso);
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de personas por curso (idCurso=" 
											+ idCurso + ".");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vPersona_curso;
    }
    
    public Persona_Curso buscarPersona_Curso (int idPersona) {
    	System.out.println("Entra en buscarPersona_Curso");
    	Persona_Curso persona_curso = null;
    	System.out.println("Datos que se le pasan al metodo buscarPersona_Curso: ");
		System.out.println("-- idPersona: " + idPersona);		
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERSONA_CURSO WHERE IDPERSONA=?");
			pst.setInt(1, idPersona);			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				persona_curso = new Persona_Curso();
				persona_curso.setIdPersona(rs.getInt("IDPERSONA"));
				persona_curso.setIdCurso(rs.getInt("IDCURSO"));				
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de persona_curso (idPersona=" + idPersona + ").");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return persona_curso;
    }
    
    public boolean insertarPersona_Curso (Persona_Curso persona_curso) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("INSERT INTO PERSONA_CURSO (IDPERSONA, IDCURSO) " +
																" VALUES (?,?)");
			pst.setInt(1, persona_curso.getIdPersona());
			pst.setInt(2, persona_curso.getIdCurso());
			if(pst.executeUpdate() < 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": insertado persona_curso (idPersona=" 
						+ persona_curso.getIdPersona() +", idCurso=" + persona_curso.getIdCurso() + ").");
				out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean modificarPersona_Curso (Persona_Curso persona_curso) {
    	System.out.println("Entra en modificarPersona_Curso()");
    	System.out.println("*** Datos de entrada: ");
    	System.out.println("-------- idPersona : " + persona_curso.getIdPersona());
    	System.out.println("-------- idCurso : " + persona_curso.getIdCurso());    	
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("UPDATE PERSONA_CURSO SET IDCURSO=? " +
																" WHERE IDPERSONA=?");
			pst.setInt(1, persona_curso.getIdCurso());
			pst.setInt(2, persona_curso.getIdPersona());
			if(pst.executeUpdate() <= 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": horario persona_curso (idPersona=" 
						+ persona_curso.getIdPersona() +", idCurso=" + persona_curso.getIdCurso() + ").");
	            out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean eliminarPersona_Curso (int idPersona) {
    	boolean correcto = true;
    	try {
			PreparedStatement pstHorario = con.prepareStatement("DELETE FROM PERSONA_CURSO WHERE IDPERSONA=?");
			pstHorario.setInt(1, idPersona);
			if(pstHorario.executeUpdate() <= 0){
				correcto = false;
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": elimina la persona de persona_curso (idPersona=" 
						+ idPersona + ").");
				out.flush();
			}
			pstHorario.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public void desconectar(){
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
