package modelo;

public class Curso {
	private int idCurso;
	private String nombre;
	private int durHoras;
	
	public Curso () {
		
	}
	
	public Curso (String nombre, int durHoras) {
		this.setNombre(nombre);
		this.setDurHoras(durHoras);
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
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
}
