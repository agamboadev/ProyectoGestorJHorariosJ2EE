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

import utils.ServletUtil;

import modelo.Asignatura;
import modelo.GestorBD;
import modelo.Horario;
import modelo.Rango_horas;

import com.mysql.jdbc.Connection;

public class Rango_horasDAO {
	private Connection con;
    private PrintWriter out;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private String ruta;
 // Para poder añadir el nombre de la persona que ha realizado las operaciones
    private String strUsuario; 
    
    public Rango_horasDAO () {
    	this.conexion();
    	strUsuario = "Desconocido";
    }

    public Rango_horasDAO(String usuario){
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
    
    public Rango_horas buscarRango_horas (int idRango_horas) {
    	Rango_horas rango_horas = null;
    	System.out.println("Datos que se le pasan al metodo buscarRango_horas: ");
		System.out.println("-- idRango_horas: " + idRango_horas);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM RANGO_HORAS WHERE IDRANGO_HORAS=?");
			pst.setInt(1, idRango_horas);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				rango_horas = new Rango_horas();
				rango_horas.setIdRango_horas(rs.getInt("IDRANGO_HORAS"));				
				rango_horas.setTexto(rs.getString("TEXTO"));				
				rango_horas.setFila(rs.getInt("FILA"));
				rango_horas.setIdHorario(rs.getInt("IDHORARIO"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda rango de horas.");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return rango_horas;
    }
    
    public Rango_horas buscarRango_horasFila (int idHorario, int fila) {
    	Rango_horas rango_horas = null;
    	System.out.println("Datos que se le pasan al metodo buscarRango_horasFila: ");
		System.out.println("-- idHorario: " + idHorario);
		System.out.println("-- fila: " + fila);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM RANGO_HORAS WHERE IDHORARIO=? AND FILA=?");
			pst.setInt(1, idHorario);
			pst.setInt(2, fila);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				rango_horas = new Rango_horas();
				rango_horas.setIdRango_horas(rs.getInt("IDRANGO_HORAS"));				
				rango_horas.setTexto(rs.getString("TEXTO"));				
				rango_horas.setFila(rs.getInt("FILA"));
				rango_horas.setIdHorario(rs.getInt("IDHORARIO"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda rango de horas.");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return rango_horas;
    }
    
    public Vector<Rango_horas> listadoRango_horas () {
    	Vector<Rango_horas> vRango_horas = new Vector<Rango_horas>();
    	Rango_horas rango_horas = null;
    	System.out.println("No se le pasan datos al metodo listadoRango_horas: ");
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM RANGO_HORAS");    		
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			rango_horas = new Rango_horas();
    			rango_horas.setIdRango_horas(rs.getInt("IDRANGO_HORAS"));				
				rango_horas.setTexto(rs.getString("TEXTO"));				
				rango_horas.setFila(rs.getInt("FILA"));
				rango_horas.setIdHorario(rs.getInt("IDHORARIO"));
				vRango_horas.add(rango_horas);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de rango de horas.");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vRango_horas;
    }
    
    public boolean insertarRango_horas (Rango_horas rango_horas) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("INSERT INTO RANGO_HORAS (TEXTO, FILA, IDHORARIO) " +
																" VALUES (?,?,?)");
			pst.setString(1, rango_horas.getTexto());
			pst.setInt(2, rango_horas.getFila());			
			pst.setInt(3, rango_horas.getIdHorario());
			if(pst.executeUpdate() < 0){
				correcto = false;
			}		
			pst.close();
			out.println(new java.util.Date() + " - " + strUsuario +  ": insertado rango de horas.");
            out.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean modificarRango_horas (Rango_horas rango_horas) {
    	System.out.println("Entra en modificarRango_horas()");
    	System.out.println("*** Datos de entrada: ");
    	System.out.println("-------- idRango_horas : " + rango_horas.getIdRango_horas());
    	System.out.println("-------- texto : " + rango_horas.getTexto());
    	System.out.println("-------- fila : " + rango_horas.getFila());
    	System.out.println("-------- idHorario : " + rango_horas.getIdHorario());
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("UPDATE RANGO_HORAS SET TEXTO=?, FILA=?, IDHORARIO=? " +
																"WHERE IDRANGO_HORAS=?");
			pst.setString(1, rango_horas.getTexto());
			pst.setInt(2, rango_horas.getFila());
			pst.setInt(3, rango_horas.getIdHorario());
			pst.setInt(4, rango_horas.getIdRango_horas());
			if(pst.executeUpdate() <= 0){
				correcto = false;
			}		
			pst.close();
			out.println(new java.util.Date() + " - " + strUsuario +  ": rango de horas modificada.");
            out.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean eliminarRango_horas (int idHorario) {
    	boolean correcto = true;
    	try {    		 
			PreparedStatement pst = con.prepareStatement("DELETE FROM RANGO_HORAS WHERE IDHORARIO=?");
			pst.setInt(1, idHorario);
			if(pst.executeUpdate() <= 0){
				correcto = false;
			}		
			pst.close();			
            out.flush();         
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
