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

import modelo.Asignatura;
import modelo.Curso;
import modelo.GestorBD;
import modelo.Persona;

import com.mysql.jdbc.Connection;

public class CursoDAO {
	private Connection con;
    private PrintWriter out;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
 // Para poder añadir el nombre de la persona que ha realizado las operaciones
    private String strUsuario; 
    
	public CursoDAO(){
		this.conexion();
		strUsuario = "";
	}
	
	public CursoDAO(String usuario){
		this.conexion();
		strUsuario = usuario;
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
	
	public Curso buscarCurso (int idCurso) {
    	Curso curso = null;
    	System.out.println("Datos que se le pasan al metodo buscarCurso: ");
		System.out.println("-- idCurso: " + idCurso);		
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM CURSO WHERE IDCURSO=?");
			pst.setInt(1, idCurso);			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				curso = new Curso();
				curso.setIdCurso(rs.getInt("IDCURSO"));
				curso.setNombre(rs.getString("NOMBRE"));
				curso.setDurHoras(rs.getInt("DURHORAS"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de curso con id: " + idCurso + ".");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return curso;
    }
    
    public Vector<Curso> listadoCursos () {
    	Vector<Curso> vCursos = new Vector<Curso>();
    	Curso curso = null;    		
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM CURSO");
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			curso = new Curso(); 
    			curso.setIdCurso(rs.getInt("IDCURSO"));
				curso.setNombre(rs.getString("NOMBRE"));
				curso.setDurHoras(rs.getInt("DURHORAS"));
    			vCursos.add(curso);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de cursos.");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vCursos;
    }
    
    public Vector<Curso> listadoCursos (int idProfesor) {
    	Vector<Curso> vCursos = new Vector<Curso>();
    	Curso curso = null;    		
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT CURSO.IDCURSO, CURSO.NOMBRE, CURSO.DURHORAS FROM CURSO INNER JOIN ASIGNATURA ON CURSO.IDCURSO = ASIGNATURA.IDCURSO WHERE IDPROFESOR=? GROUP BY ASIGNATURA.IDCURSO");
    		pst.setInt(1, idProfesor);	
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			curso = new Curso(); 
    			curso.setIdCurso(rs.getInt("IDCURSO"));
				curso.setNombre(rs.getString("NOMBRE"));
				curso.setDurHoras(rs.getInt("DURHORAS"));
    			vCursos.add(curso);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de cursos con idProfesor: " + idProfesor + ".");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vCursos;
    }
    
    public Curso obtenerCursoAlumno (int idAlumno) {
    	Curso curso = null;    		
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT CURSO.IDCURSO, CURSO.NOMBRE, CURSO.DURHORAS FROM CURSO INNER JOIN PERSONA_CURSO ON PERSONA_CURSO.IDCURSO = CURSO.IDCURSO WHERE PERSONA_CURSO.IDPERSONA=?");
    		pst.setInt(1, idAlumno);	
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			curso = new Curso(); 
    			curso.setIdCurso(rs.getInt("IDCURSO"));
				curso.setNombre(rs.getString("NOMBRE"));
				curso.setDurHoras(rs.getInt("DURHORAS"));
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": obtener curso con idAlumno: " + idAlumno + ".");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return curso;
    }
    
    public boolean insertarCurso(Curso curso) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("INSERT INTO CURSO  (NOMBRE, DURHORAS)" +
																"VALUES (?,?)");
			pst.setString(1, curso.getNombre());
			pst.setInt(2, curso.getDurHoras());			
			if(pst.executeUpdate() < 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": insertado curso " + curso.getNombre() + " de " + curso.getDurHoras() + " horas.");
	            out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean eliminarCurso (Curso curso) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("DELETE FROM CURSO WHERE IDCURSO=?");
			pst.setInt(1, curso.getIdCurso());
			if(pst.executeUpdate() <= 0){
				correcto = false;
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": elimina el curso " + curso.getNombre() + ".");
	            out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean modificarCurso (Curso curso) {
    	System.out.println("Entra en modificarCurso()");
    	System.out.println("*** Datos de entrada: ");
    	System.out.println("-------- idCurso : " + curso.getIdCurso());
    	System.out.println("-------- Nombre : " + curso.getNombre());
    	System.out.println("-------- DurHoras : " + curso.getDurHoras());    	
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("UPDATE CURSO SET NOMBRE=?, DURHORAS=? " +
																"WHERE IDCURSO=?");
			pst.setString(1, curso.getNombre());
			pst.setInt(2, curso.getDurHoras());			
			pst.setInt(3, curso.getIdCurso());			
			if(pst.executeUpdate() <= 0){
				correcto = false;
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": modificado curso (" + curso.getIdCurso() + ", " 
						+ curso.getNombre() + ", " + curso.getIdCurso() + ").");
				out.flush();
			}
			pst.close();			
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
