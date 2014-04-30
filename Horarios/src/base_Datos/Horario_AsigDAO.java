package base_Datos;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import modelo.Horario_Asig;

import com.mysql.jdbc.Connection;

public class Horario_AsigDAO {
	private Connection con;
    private PrintWriter out;
 // Para poder añadir el nombre de la persona que ha realizado las operaciones
    private String strUsuario; 
    
    public Horario_AsigDAO (Connection con, PrintWriter out) {
    	this.con = con;
    	this.out = out;
    	strUsuario = "Desconocido";
    }

    public Horario_AsigDAO(Connection con, PrintWriter out, String usuario){
    	strUsuario = usuario;
    	this.con = con;
    	this.out = out;
	}
    
    public Horario_Asig buscarHorario_Asig (int dia, int fila, int idHorario) {
    	Horario_Asig horario_Asig = null;
    	System.out.println("Datos que se le pasan al metodo buscarHorario_Asig: ");
    	System.out.println("-- dia: " + dia);
    	System.out.println("-- fila: " + fila);
		System.out.println("-- idHorario: " + idHorario);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM HORARIO_ASIG WHERE DIA=? AND FILA=? AND IDHORARIO=?");
			pst.setInt(1, dia);
			pst.setInt(2, fila);
			pst.setInt(3, idHorario);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				horario_Asig = new Horario_Asig();				
				horario_Asig.setDia(rs.getInt("DIA"));
				horario_Asig.setFila(rs.getInt("FILA"));
				horario_Asig.setIdAsignatura(rs.getInt("IDASIGNATURA"));
				horario_Asig.setIdHorario(rs.getInt("IDHORARIO"));
			}
			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de horario asignatura por dia=" + dia + ", fila=" 
														+ fila + ", idHorario=" + idHorario + ".");
            out.flush();
			rs.close();					
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return horario_Asig;
    }
    
    public Vector<Horario_Asig> listadoHorario_Asig () {
    	Vector<Horario_Asig> vHorario_Asig = new Vector<Horario_Asig>();
    	Horario_Asig horario_Asig = null;
    	System.out.println("No se le pasan datos al metodo listadoHorario_Aig: ");
    	try {			
    		PreparedStatement pst;
    		pst = con.prepareStatement("SELECT * FROM HORARIO_ASIG");    		
    		ResultSet rs = pst.executeQuery();
    		while (rs.next()) {
    			horario_Asig = new Horario_Asig();			
				horario_Asig.setDia(rs.getInt("DIA"));
				horario_Asig.setFila(rs.getInt("FILA"));
				horario_Asig.setIdAsignatura(rs.getInt("IDASIGNATURA"));
				horario_Asig.setIdHorario(rs.getInt("IDHORARIO"));
    			vHorario_Asig.add(horario_Asig);
    		}
    		out.println(new java.util.Date() + " - " + strUsuario +  ": listado de horarios-asignatura (dia=" + 
    											horario_Asig.getDia() + ", fila=" + horario_Asig.getFila() + 
    											", idHorario=" + horario_Asig.getIdHorario() + 
    											",idAsignatura=" + horario_Asig.getIdAsignatura() + " ).");
            out.flush();
    		rs.close();
    		pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return vHorario_Asig;
    }
    
    public boolean insertarHorario_Asig (Horario_Asig horario_Asig) {
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("INSERT INTO HORARIO_ASIG (DIA, FILA, IDASIGNATURA, IDHORARIO) " +
																" VALUES (?,?,?,?)");
			pst.setInt(1, horario_Asig.getDia());
			pst.setInt(2, horario_Asig.getFila());
			pst.setInt(3, horario_Asig.getIdAsignatura());
			pst.setInt(4, horario_Asig.getIdHorario());
			if(pst.executeUpdate() < 0){
				correcto = false;				
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": insertado horario-asignatura (dia=" + 
						horario_Asig.getDia() + ", fila=" + horario_Asig.getFila() + 
						", idHorario=" + horario_Asig.getIdHorario() + 
						",idAsignatura=" + horario_Asig.getIdAsignatura() + " ).");
				out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean modificarHorario_Asig (Horario_Asig horario_Asig) {
    	System.out.println("Entra en modificarHorario_Asig()");
    	System.out.println("*** Datos de entrada: ");
    	System.out.println("-------- dia : " + horario_Asig.getDia());
    	System.out.println("-------- fila : " + horario_Asig.getFila());
    	System.out.println("-------- idAsignatura: " + horario_Asig.getIdAsignatura());
    	System.out.println("-------- idHorario : " + horario_Asig.getIdHorario());
    	boolean correcto = true;
    	try {			
			PreparedStatement pst = con.prepareStatement("UPDATE HORARIO_ASIG SET IDASIGNATURA=? " +
																"WHERE DIA=? AND FILA=? AND IDHORARIO=?");
			pst.setInt(1, horario_Asig.getIdAsignatura());
			pst.setInt(2, horario_Asig.getDia());
			pst.setInt(3, horario_Asig.getFila());			
			pst.setInt(4, horario_Asig.getIdHorario());			
			if(pst.executeUpdate() <= 0){
				correcto = false;					
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": horario-asignatura modificada (dia=" + 
						horario_Asig.getDia() + ", fila=" + horario_Asig.getFila() + 
						", idHorario=" + horario_Asig.getIdHorario() + 
						",idAsignatura=" + horario_Asig.getIdAsignatura() + " ).");
				out.flush();
			}
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    public boolean eliminarHorario_asig (int idHorario) {
    	boolean correcto = true;
    	try {    		 
			PreparedStatement pst = con.prepareStatement("DELETE FROM HORARIO_ASIG WHERE IDHORARIO=?");
			pst.setInt(1, idHorario);
			if(pst.executeUpdate() <= 0){
				correcto = false;
			} else {
				out.println(new java.util.Date() + " - " + strUsuario +  ": eliminar horario-asignatura (idHorario=" + idHorario + "."); 
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
