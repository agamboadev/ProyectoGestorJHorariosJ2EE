package modelo;

import java.io.Serializable;

public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idPersona;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private String telefono;
	private String movil;
	private String email;
	private String pass;
	private int idPerfil;
//	private Perfil perfil;
	
	public Persona () {
		
	}
	
	public Persona (String dni, String pass, String nombre, String apellido1, String apellido2, String telefono, String movil
					, String email, int idPerfil) {
		this.setNombre(nombre);
		this.setApellido1(apellido1);
		this.setApellido2(apellido2);
		this.setDni(dni);
		this.setTelefono(telefono);
		this.setMovil(movil);
		this.setEmail(email);
		this.setPass(pass);		
//		this.setPerfil(new Perfil(idPerfil));
		this.setIdPerfil(idPerfil);
	}
	
	public String descripcionUsuario () {
		return this.getDni() + " " + this.getNombre() + " " + this.getApellido1();
	}
	
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

//	public Perfil getPerfil() {
//		return perfil;
//	}
//
//	public void setPerfil(Perfil perfil) {
//		this.perfil = perfil;
//	}
	
	public int getIdPerfil() {
		return idPerfil;
	}
	
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Override
	public String toString() {
		StringBuilder strbdCadena = new StringBuilder();
		strbdCadena.append("IdPersona: " + this.getIdPersona() + "; ");
		strbdCadena.append("Dni: " + this.getDni() + "; ");
		strbdCadena.append("Nombre: " + this.getNombre() + "; ");
		strbdCadena.append("Apellido1: " + this.getApellido1() + "; ");
		strbdCadena.append("Apellido2: " + this.getApellido2() + "; ");
		strbdCadena.append("Telefono: " + this.getTelefono() + "; ");
		strbdCadena.append("Movil: " + this.getMovil() + "; ");
		strbdCadena.append("Email: " + this.getEmail() + "; ");
		strbdCadena.append("Pass: " + this.getPass() + "; ");
		strbdCadena.append("IdPerfil: " + this.getIdPerfil() + "; ");
		return super.toString();
	}
}
