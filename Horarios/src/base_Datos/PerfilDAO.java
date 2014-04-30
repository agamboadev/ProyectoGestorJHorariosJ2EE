package base_Datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import modelo.Perfil;

import com.mysql.jdbc.Connection;

public class PerfilDAO {
	private Connection con;
    
    public PerfilDAO(Connection con){
    	//this.conexion();
    	this.con = con;
	}

    public Perfil buscarPerfil (int idPerfil) {
    	Perfil perfil = null;
    	System.out.println("Datos que se le pasan al metodo buscarPerfil: ");
		System.out.println("-- idPerfil: " + idPerfil);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERFIL WHERE IDPERFIL=?");
			pst.setInt(1, idPerfil);			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				perfil = new Perfil();
				perfil.setIdPerfil(rs.getInt("IDPERFIL"));
				perfil.setNombre(rs.getString("NOMBRE"));
			}
//			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de peril.");
//            out.flush();
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return perfil;
    }
    
    public String nombrePerfil (int idPerfil) {
    	String nombPerfil = "";
    	System.out.println("Datos que se le pasan al metodo nombrePerfil: ");
		System.out.println("-- idPerfil: " + idPerfil);
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERFIL WHERE IDPERFIL=?");
			pst.setInt(1, idPerfil);			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				nombPerfil = rs.getString("NOMBRE");
			}
//			out.println(new java.util.Date() + " - " + strUsuario +  ": busqueda de nombre de perfil.");
//            out.flush();
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return nombPerfil;
    }
    
    public Vector<Perfil> listadoPerfiles () {
    	Vector<Perfil> perfiles = new Vector<Perfil>();;
    	System.out.println("Entra en listadoPerfiles: ");
	
    	try {			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM PERFIL");			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				perfiles.add(new Perfil(rs.getInt("IDPERFIL"), rs.getString("NOMBRE")));				
			}
			for(Perfil perfil : perfiles) {
				System.out.println("--- Perfil: ");
				System.out.println("------ idPerfil: " + perfil.getIdPerfil());
				System.out.println("------ nombre: " + perfil.getNombre());
			}
//			out.println(new java.util.Date() + " - " + strUsuario +  ": listado de perfiles.");
//            out.flush();
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return perfiles;
    }
    
    public void desconectar(){
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
