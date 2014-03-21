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

import modelo.GestorBD;
import modelo.Horario;
import modelo.Persona;
import modelo.Rango_horas;

import com.mysql.jdbc.Connection;

public class HorarioDAO {
	private Connection con;
    private PrintWriter out;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private String ruta;
 // Para poder añadir el nombre de la persona que ha realizado las operaciones
    private String strUsuario; 
    
    public HorarioDAO () {
    	this.conexion();
    	strUsuario = "Desconocido";
    }

    public HorarioDAO(String usuario){
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
    
    public Horario buscarHorario (int idHorario) {
    	Horario horario = null;
    	System.out.println("Datos que se le pasan al metodo buscarHorario: ");
		System.out.println("-- idHorario: " + idHorario);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM HORARIO WHERE IDHORARIO=?");
			pst.setInt(1, idHorario);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				horario = new Horario();
				horario.setIdHorario(rs.getInt("IDHORARIO"));
				horario.setNumPorciones(rs.getInt("NUMPORCIONES"));
				horario.setMes(rs.getInt("MES"));
				horario.setAnio(rs.getInt("ANIO"));
				horario.setIdCurso(rs.getInt("IDCURSO"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de horario (idHorario= " + idHorario + ").");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return horario;
    }
    
    public Horario buscarHorario (int mes, int anio, int idCurso) {
    	System.out.println("Entra en buscarHorario");
    	Horario horario = null;
    	System.out.println("Datos que se le pasan al metodo buscarHorario: ");
		System.out.println("-- mes: " + mes);
		System.out.println("-- anio: " + anio);
		System.out.println("-- idCurso: " + idCurso);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM HORARIO WHERE MES=? AND ANIO=? AND IDCURSO=?");
			pst.setInt(1, mes);
			pst.setInt(2, anio);
			pst.setInt(3, idCurso);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				horario = new Horario();
				horario.setIdHorario(rs.getInt("IDHORARIO"));
				horario.setNumPorciones(rs.getInt("NUMPORCIONES"));
				horario.setMes(rs.getInt("MES"));
				horario.setAnio(rs.getInt("ANIO"));
				horario.setIdCurso(rs.getInt("IDCURSO"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de horario (mes=" + mes + 
											", anio=" + anio + ", idCurso=" + idCurso + ").");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return horario;
    }
    
    public Vector<Horario> listadoHorario () {
    	Vector<Horario> vHorarios = new Vector<Horario>();
    	Horario horario = null;
    	System.out.println("No se le pasan datos al metodo listadoHorarios: ");
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM HORARIO");    		
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			horario = new Horario();
    			horario.setIdHorario(rs.getInt("IDHORARIO"));
    			horario.setNumPorciones(rs.getInt("NUMPORCIONES"));
    			horario.setMes(rs.getInt("MES"));
    			horario.setAnio(rs.getInt("ANIO"));
    			horario.setIdCurso(rs.getInt("IDCURSO"));
				vHorarios.add(horario);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de horarios.");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vHorarios;
    }
    
    public Vector<Horario> listadoHorario (int idCurso, int anio) {
    	Vector<Horario> vHorarios = new Vector<Horario>();
    	Horario horario = null;
    	System.out.println("Datos que se le pasan al metodo listadoHorarios: ");
    	System.out.println("-- idCurso: " + idCurso);
    	System.out.println("-- anio: " + anio);
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM HORARIO WHERE IDCURSO=? AND ANIO=?");
    		pst.setInt(1, idCurso);
			pst.setInt(2, anio);
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			horario = new Horario();
    			horario.setIdHorario(rs.getInt("IDHORARIO"));
    			horario.setNumPorciones(rs.getInt("NUMPORCIONES"));
    			horario.setMes(rs.getInt("MES"));
    			horario.setAnio(rs.getInt("ANIO"));
    			horario.setIdCurso(rs.getInt("IDCURSO"));
				vHorarios.add(horario);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de horario filtrado por idCurso=" + idCurso + " y anio=" + anio + ".");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vHorarios;
    }
    
    public Vector<Horario> listadoHorario (int idCurso) {
    	Vector<Horario> vHorarios = new Vector<Horario>();
    	Horario horario = null;
    	System.out.println("Datos que se le pasan al metodo listadoHorarios: ");
    	System.out.println("-- idCurso: " + idCurso);
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM HORARIO WHERE IDCURSO=?");
			pst.setInt(1, idCurso);
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			horario = new Horario();
    			horario.setIdHorario(rs.getInt("IDHORARIO"));
    			horario.setNumPorciones(rs.getInt("NUMPORCIONES"));
    			horario.setMes(rs.getInt("MES"));
    			horario.setAnio(rs.getInt("ANIO"));
    			horario.setIdCurso(rs.getInt("IDCURSO"));
				vHorarios.add(horario);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de horario filtrado por idCurso=" + idCurso + ".");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vHorarios;
    }
    
    public boolean insertarHorario (Horario horario) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("INSERT INTO HORARIO (NUMPORCIONES, MES, ANIO, IDCURSO) " +
																" VALUES (?,?,?,?)");
			pst.setInt(1, horario.getNumPorciones());
			pst.setInt(2, horario.getMes());
			pst.setInt(3, horario.getAnio());
			pst.setInt(4, horario.getIdCurso());
			if(pst.executeUpdate() < 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": insertado horario (numPorciones= " + horario.getNumPorciones() + 
						", mes= " + horario.getMes() + ", anio= " + horario.getAnio() + ", idCurso=" + horario.getIdCurso() +").");
				out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean modificarHorario (Horario horario) {
    	System.out.println("Entra en modificarhorario()");
    	System.out.println("*** Datos de entrada: ");
    	System.out.println("-------- idHorario : " + horario.getIdHorario());
    	System.out.println("-------- NumPorciones : " + horario.getNumPorciones());
    	System.out.println("-------- Fecha (mm/aaaa) : " + horario.fechaMesAnio());
    	System.out.println("-------- IdCurso : " + horario.getIdCurso());
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("UPDATE HORARIO SET NUMPORCIONES=?, MES=?, ANIO=?, IDCURSO=? " +
																" WHERE IDHORARIO=?");
			pst.setInt(1, horario.getNumPorciones());
			pst.setInt(2, horario.getMes());
			pst.setInt(3, horario.getAnio());
			pst.setInt(4, horario.getIdCurso());
			if(pst.executeUpdate() <= 0){
				correcto = false;
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": modufucado Horario (numPorciones= " + horario.getNumPorciones() + 
						", mes= " + horario.getMes() + ", anio= " + horario.getAnio() + ", idCurso=" + horario.getIdCurso() +").");
				out.flush();
			}
			pst.close();				
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean eliminarHorario (Horario horario) {
    	boolean correcto = true;
    	try {
    		// Como hay que eliminar en más de una tabla, se desactiva el autocommit temporalmente
			PreparedStatement pstHorario = con.prepareStatement("DELETE FROM HORARIO WHERE IDHORARIO=?");
			pstHorario.setInt(1, horario.getIdHorario());
			if(pstHorario.executeUpdate() <= 0){
				correcto = false;				
			} else {
				String strMes = ServletUtil.diaSemana(horario.getMes());			
				out.println(new java.util.Date() + " - " + strUsuario +  ": elimina el horario de " + strMes + " de " + horario.getAnio() + ".");
	            out.flush(); 
			}
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
