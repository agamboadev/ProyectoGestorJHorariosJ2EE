package modelo;

import java.io.Serializable;

public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idPerfil;
	private String nombre;
	
	public Perfil () {
		
	}
	
	public Perfil (int idPerfil) {
		this.setIdPerfil(idPerfil);
//		GestorBD gestorBD = new GestorBD();
//		try {
//			gestorBD.conectar();
//			this.setNombre(gestorBD.nombrePerfil(idPerfil));
//			gestorBD.desconectar();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	public Perfil (int idPerfil, String nombre) {
		this.setIdPerfil(idPerfil);
		this.setNombre(nombre);
	}
	
	public int getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
