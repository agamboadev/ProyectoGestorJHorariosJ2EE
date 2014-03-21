package modelo;

public class Asignatura {
	private int idAsignatura;
	private String nombre;
	private int durHoras;
	private int idCurso;
	private int idProfesor;
	
	public Asignatura () {
		
	}
	
	public Asignatura (String nombre, int durHoras, int idCurso, int idProfesor) {
		this.setNombre(nombre);
		this.setDurHoras(durHoras);
		this.setIdCurso(idCurso);
		this.setIdProfesor(idProfesor);
	}	

	public int getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(int idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDurHoras() {
		return durHoras;
	}

	public void setDurHoras(int durHoras) {
		this.durHoras = durHoras;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	
	public int getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
	}
}
