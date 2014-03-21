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
import modelo.Persona;

import com.mysql.jdbc.Connection;

public class PersonaDAO {
	private Connection con;
    private PrintWriter out;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private String ruta;
 // Para poder añadir el nombre de la persona que ha realizado las operaciones
    private String strUsuario; 
    
    public PersonaDAO () {
    	this.conexion();
    	strUsuario = "Desconocido";
    }

    /**
     * usuario: DNI + nombre + apellido1
     */
    public PersonaDAO(String usuario){
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
    
    public Persona buscarPersona (String dni, String pass) {
    	Persona persona = null;
    	System.out.println("Datos que se le pasan al metodo buscarPersona: ");
		System.out.println("-- DNI: " + dni);
		System.out.println("-- Pass: " + pass);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERSONA WHERE DNI=? AND PASS=?");
			pst.setString(1, dni);
			pst.setString(2, pass);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				persona = new Persona();
				persona.setIdPersona(rs.getInt("IDPERSONA"));
				persona.setDni(rs.getString("DNI"));
				persona.setPass(rs.getString("PASS"));
				persona.setNombre(rs.getString("NOMBRE"));
				persona.setApellido1(rs.getString("APELLIDO1"));
				persona.setApellido2(rs.getString("APELLIDO2"));
				persona.setTelefono(rs.getString("TELEFONO"));
				persona.setMovil(rs.getString("MOVIL"));
				persona.setEmail(rs.getString("EMAIL"));				
				persona.setIdPerfil(rs.getInt("IDPERFIL"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de un persona con dni= " + dni +".");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return persona;
    }
    
    public Persona buscarPersona (String dni) {
    	Persona persona = null;
    	System.out.println("Datos que se le pasan al metodo buscarPersona: ");
		System.out.println("-- DNI: " + dni);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERSONA WHERE DNI=?");
			pst.setString(1, dni);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				persona = new Persona();
				persona.setIdPersona(rs.getInt("IDPERSONA"));
				persona.setDni(rs.getString("DNI"));
				persona.setPass(rs.getString("PASS"));
				persona.setNombre(rs.getString("NOMBRE"));
				persona.setApellido1(rs.getString("APELLIDO1"));
				persona.setApellido2(rs.getString("APELLIDO2"));
				persona.setTelefono(rs.getString("TELEFONO"));
				persona.setMovil(rs.getString("MOVIL"));
				persona.setEmail(rs.getString("EMAIL"));				
				persona.setIdPerfil(rs.getInt("IDPERFIL"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de un persona con dni=" + dni + ".");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return persona;
    }
    
    public Vector<Persona> listadoPersonas (int idPerfil) {
    	Vector<Persona> vPersonas = new Vector<Persona>();
    	Persona persona = null;
    	System.out.println("Datos que se le pasan al metodo listadoPersonas: ");
		System.out.println("-- idPerfil: " + idPerfil);		
    	try {			
    		PreparedStatement pst;
    		
    		if (idPerfil != 0) {
    			pst = con.prepareStatement("SELECT * FROM PERSONA WHERE IDPERFIL=?");
    		} else {
    			pst = con.prepareStatement("SELECT * FROM PERSONA");
    		}
    			pst.setInt(1, idPerfil);
    			ResultSet rs = pst.executeQuery();
    			while (rs.next()) {
    				persona = new Persona(); 
    				persona.setIdPersona(rs.getInt("IDPERSONA"));
    				persona.setDni(rs.getString("DNI"));
    				persona.setPass(rs.getString("PASS"));
    				persona.setNombre(rs.getString("NOMBRE"));
    				persona.setApellido1(rs.getString("APELLIDO1"));
    				persona.setApellido2(rs.getString("APELLIDO2"));
    				persona.setTelefono(rs.getString("TELEFONO"));
    				persona.setMovil(rs.getString("MOVIL"));
    				persona.setEmail(rs.getString("EMAIL"));
    				persona.setIdPerfil(rs.getInt("IDPERFIL"));
    				vPersonas.add(persona);
    			}
    			out.println(new java.util.Date() + " - " + strUsuario +  ": listado de personas.");
                out.flush();
    			rs.close();
    			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vPersonas;
    }
    
    public Vector<Persona> listadoPersonasCurso (int idCurso) {
    	Vector<Persona> vPersonas = new Vector<Persona>();
    	Persona persona = null;
    	System.out.println("Datos que se le pasan al metodo listadoPersonas: ");	
		System.out.println("-- idCurso: " + idCurso);
    	try {					
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM PERSONA INNER JOIN PERSONA_CURSO ON " + 
    								"PERSONA.IDPERSONA = PERSONA_CURSO.IDPERSONA WHERE PERSONA_CURSO.IDCURSO=?");    		
    		pst.setInt(1, idCurso);
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			persona = new Persona(); 
    			persona.setIdPersona(rs.getInt("IDPERSONA"));
    			persona.setDni(rs.getString("DNI"));
    			persona.setPass(rs.getString("PASS"));
    			persona.setNombre(rs.getString("NOMBRE"));
    			persona.setApellido1(rs.getString("APELLIDO1"));
    			persona.setApellido2(rs.getString("APELLIDO2"));
    			persona.setTelefono(rs.getString("TELEFONO"));
    			persona.setMovil(rs.getString("MOVIL"));
    			persona.setEmail(rs.getString("EMAIL"));
    			persona.setIdPerfil(rs.getInt("IDPERFIL"));
    			vPersonas.add(persona);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de personas del curso con idCurso=" + idCurso + ".");
               out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vPersonas;
    }
    
    public boolean insertarPersona (Persona persona) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("INSERT INTO PERSONA  (NOMBRE, APELLIDO1, APELLIDO2, DNI, TELEFONO, " +
																"MOVIL, EMAIL, PASS, IDPERFIL) " +
																"VALUES (?,?,?,?,?,?,?,?,?)");
			pst.setString(1, persona.getNombre());
			pst.setString(2, persona.getApellido1());
			pst.setString(3, persona.getApellido2());
			pst.setString(4, persona.getDni());
			pst.setString(5, persona.getTelefono());
			pst.setString(6, persona.getMovil());
			pst.setString(7, persona.getEmail());
			pst.setString(8, persona.getPass());
			pst.setInt(9, persona.getIdPerfil());			
			if(pst.executeUpdate() <= 0){
				correcto = false;
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": insertada persona (dni=" + persona.getDni()
						+ ", nombre=" + persona.getNombre() + ", apellido1= " + persona.getApellido1() + ").");
				out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean modificarPersona (Persona persona) {
    	System.out.println("Entra en modificarPersona: " + persona.getPass());
    	boolean correcto = true;
    	try {			
    		// Se comprueba si el campo pass está vacío. Si lo está, no se incluye
    		// en la modificación. Si no está vació si se incluye.
    		PreparedStatement pst;
    		if (persona.getPass().equals("")) {
    			pst = con.prepareStatement("UPDATE PERSONA SET NOMBRE=?, APELLIDO1=?, APELLIDO2=?, DNI=?, " +
						" TELEFONO=?, MOVIL=?, EMAIL=?, IDPERFIL=? " +
						" WHERE IDPERSONA=?");
    			pst.setString(1, persona.getNombre());
    			pst.setString(2, persona.getApellido1());
    			pst.setString(3, persona.getApellido2());
    			pst.setString(4, persona.getDni());
    			pst.setString(5, persona.getTelefono());
    			pst.setString(6, persona.getMovil());
    			pst.setString(7, persona.getEmail());
    			pst.setInt(8, persona.getIdPerfil());
    			pst.setInt(9, persona.getIdPersona());
    		} else {
    			pst = con.prepareStatement("UPDATE PERSONA SET NOMBRE=?, PASS=?, APELLIDO1=?, APELLIDO2=?, DNI=?, " +
						" TELEFONO=?, MOVIL=?, EMAIL=?, IDPERFIL=? " +
						" WHERE IDPERSONA=?");
    			pst.setString(1, persona.getNombre());
    			pst.setString(2, persona.getPass());
    			pst.setString(3, persona.getApellido1());
    			pst.setString(4, persona.getApellido2());
    			pst.setString(5, persona.getDni());
    			pst.setString(6, persona.getTelefono());
    			pst.setString(7, persona.getMovil());
    			pst.setString(8, persona.getEmail());
    			pst.setInt(9, persona.getIdPerfil());
    			pst.setInt(10, persona.getIdPersona());
    		}			
			if(pst.executeUpdate() <= 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": modificada persona (dni=" + persona.getDni()
						+ ", nombre=" + persona.getNombre() + ", apellido1= " + persona.getApellido1() + ").");
				out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean eliminarPersona (Persona persona) {
    	boolean correcto = true;
    	try {
    		con.setAutoCommit(false);
    		PreparedStatement pstPersona_Curso = con.prepareStatement("DELETE FROM PERSONA_CURSO WHERE IDPERSONA=?");
    		pstPersona_Curso.setInt(1, persona.getIdPersona());
			if(pstPersona_Curso.executeUpdate() <= 0){
				correcto = false;
			}		
			pstPersona_Curso.close();
			PreparedStatement pstPersona = con.prepareStatement("DELETE FROM PERSONA WHERE IDPERSONA=?");
			pstPersona.setInt(1, persona.getIdPersona());
			if(pstPersona.executeUpdate() <= 0){
				correcto = false;
			}
			if (correcto) {
				out.println(new java.util.Date() + " - " + strUsuario +  ": elimina a " + persona.getNombre() + persona.getApellido1() + persona.getApellido2() + ".");
	            out.flush();
	            con.commit();
			}			
			pstPersona.close();
            con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public Persona profesorAsignatura(int idAsignatura) {
    	Persona persona = null;
    	System.out.println("Datos que se le pasan al metodo profesorAsignatura: ");
		System.out.println("-- idAsignatura: " + idAsignatura);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERSONA WHERE IDPERSONA = (SELECT IDPROFESOR FROM ASIGNATURA WHERE IDASIGNATURA=?)");
			pst.setInt(1, idAsignatura);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				persona = new Persona();
				persona.setIdPersona(rs.getInt("IDPERSONA"));
				persona.setDni(rs.getString("DNI"));
				persona.setPass(rs.getString("PASS"));
				persona.setNombre(rs.getString("NOMBRE"));
				persona.setApellido1(rs.getString("APELLIDO1"));
				persona.setApellido2(rs.getString("APELLIDO2"));
				persona.setTelefono(rs.getString("TELEFONO"));
				persona.setMovil(rs.getString("MOVIL"));
				persona.setEmail(rs.getString("EMAIL"));
				persona.setIdPerfil(rs.getInt("IDPERFIL"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda profesor por asignatura (idAsignatura=" + idAsignatura + ").");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return persona;
    }
    
    public void desconectar(){
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
