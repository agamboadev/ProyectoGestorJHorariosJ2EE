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
import modelo.Perfil;

import com.mysql.jdbc.Connection;

public class PerfilDAO {
	private Connection con;
	private boolean conectado=false;
    private int intentos=0;
    private PrintWriter out;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private String strUsuario;
    
    public PerfilDAO(){
    	this.conexion();
    	strUsuario = "";
	}
    
    public PerfilDAO(String usuario){
    	this.conexion();
    	strUsuario = usuario;
	}
    
    private void conexion () {
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
    	Perfil perfil = null;
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
