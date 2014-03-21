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
import modelo.AsignaturaCurso;
import modelo.GestorBD;

import com.mysql.jdbc.Connection;

public class AsignaturaDAO {
	private Connection con;
    private PrintWriter out;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private String ruta;
 // Para poder añadir el nombre de la persona que ha realizado las operaciones
    private String strUsuario; 
    
    public AsignaturaDAO () {
    	this.conexion();
    	strUsuario = "Desconocido";
    }

    /**
     * usuario: DNI + nombre + apellido1
     */
    public AsignaturaDAO(String usuario){
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
			out=new PrintWriter(new FileWriter(carpeta + "/" + GestorBD.NOMBRE_FICHERO_LOG,true));			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Asignatura buscarAsignatura (int idAsignatura) {
    	Asignatura asignatura = null;
    	System.out.println("Datos que se le pasan al metodo buscarAsignatura: ");
		System.out.println("-- idAsignatura: " + idAsignatura);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM ASIGNATURA WHERE IDASIGNATURA=?");
			pst.setInt(1, idAsignatura);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				asignatura = new Asignatura();
				asignatura.setIdAsignatura(rs.getInt("IDASIGNATURA"));
				asignatura.setNombre(rs.getString("NOMBRE"));
				asignatura.setDurHoras(rs.getInt("DURHORAS"));
				asignatura.setIdCurso(rs.getInt("IDCURSO"));
				asignatura.setIdProfesor(rs.getInt("IDPROFESOR"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda asignatura con id: " + idAsignatura + ".");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return asignatura;
    }
    
    public Vector<Asignatura> listadoAsignaturasCurso (int idCurso) {
    	Vector<Asignatura> vAsignaturas = new Vector<Asignatura>();
    	Asignatura asignatura = null;
    	System.out.println("Datos que se le pasan al metodo listadoAsignaturasCurso: ");
		System.out.println("-- idCurso: " + idCurso);		
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM ASIGNATURA WHERE IDCURSO=?");    		
    		pst.setInt(1, idCurso);
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			asignatura = new Asignatura();
    			asignatura.setIdAsignatura(rs.getInt("IDASIGNATURA"));
				asignatura.setNombre(rs.getString("NOMBRE"));
				asignatura.setDurHoras(rs.getInt("DURHORAS"));
				asignatura.setIdCurso(rs.getInt("IDCURSO"));
				asignatura.setIdProfesor(rs.getInt("IDPROFESOR"));
    			vAsignaturas.add(asignatura);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de asignaturas curso.");
    		out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vAsignaturas;
    }
    
    // Devuelve un vector de AsignaturaCurso. Contiene los nombres de asignatura y curso en el que el profesor está incluido
    public Vector<AsignaturaCurso> listadoAsignaturasProfesor (int idProfesor) {
    	Vector<AsignaturaCurso> vAsignaturasCurso = new Vector<AsignaturaCurso>();
    	AsignaturaCurso asignaturaCurso = null;
    	System.out.println("Datos que se le pasan al metodo listadoAsignaturasProfesor: ");
		System.out.println("-- idProfesor: " + idProfesor);
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT ASIGNATURA.NOMBRE AS NOMB_ASIGNATURA, CURSO.NOMBRE AS NOMB_CURSO FROM ASIGNATURA " +
    										"INNER JOIN CURSO ON CURSO.IDCURSO = ASIGNATURA.IDCURSO " +
    										"INNER JOIN PERSONA ON PERSONA.IDPERSONA = ASIGNATURA.IDPROFESOR " +
    										"WHERE ASIGNATURA.IDPROFESOR = ?");    		
    		pst.setInt(1, idProfesor);
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			asignaturaCurso = new AsignaturaCurso();
    			asignaturaCurso.setAsignaturaNombre((rs.getString("NOMB_ASIGNATURA")));
    			asignaturaCurso.setCursoNombre((rs.getString("NOMB_CURSO")));
    			vAsignaturasCurso.add(asignaturaCurso);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de asignaturas profesor.");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vAsignaturasCurso;
    }
    
    public boolean insertarAsignatura (Asignatura asignatura) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("INSERT INTO ASIGNATURA (IDASIGNATURA, NOMBRE, DURHORAS, " +
																"IDCURSO, IDPROFESOR) VALUES (?,?,?,?,?)");
			pst.setInt(1, asignatura.getIdAsignatura());
			pst.setString(2, asignatura.getNombre());
			pst.setInt(3, asignatura.getDurHoras());
			pst.setInt(4, asignatura.getIdCurso());	
			pst.setInt(5, asignatura.getIdProfesor());
			if(pst.executeUpdate() < 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": insertada asignatura " + asignatura.getNombre() + 
						" de " + asignatura.getDurHoras() + " del idCurso: " + asignatura.getIdCurso() + 
						" con idProfesor: " + asignatura.getIdProfesor() + ".");
				out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean modificarAsignatura (Asignatura asignatura) {
    	System.out.println("Entra en modificarAsignatura()");
    	System.out.println("*** Datos de entrada: ");
    	System.out.println("-------- idAsignatura : " + asignatura.getIdAsignatura());
    	System.out.println("-------- Nombre : " + asignatura.getNombre());
    	System.out.println("-------- DurHoras : " + asignatura.getDurHoras());
    	System.out.println("-------- idCurso : " + asignatura.getIdCurso());
    	System.out.println("-------- idProfesor : " + asignatura.getIdProfesor());
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("UPDATE ASIGNATURA SET NOMBRE=?, DURHORAS=?, IDPROFESOR=? " +
																"WHERE IDASIGNATURA=?");
			pst.setString(1, asignatura.getNombre());
			pst.setInt(2, asignatura.getDurHoras());
			pst.setInt(3, asignatura.getIdProfesor());
			pst.setInt(4, asignatura.getIdAsignatura());			
			if(pst.executeUpdate() <= 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": modificada asignatura " + asignatura.getNombre() + 
						" de " + asignatura.getDurHoras() + " del idCurso: " + asignatura.getIdCurso() + 
						" con idProfesor: " + asignatura.getIdProfesor() + ".");
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
