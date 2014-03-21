package modelo;

public class AsignaturaCurso {
	private String asignaturaNombre;
	private String cursoNombre;
	
	public AsignaturaCurso () {
		
	}
	public AsignaturaCurso (String asignaturaNombre, String cursoNombre) {
		this.setAsignaturaNombre(asignaturaNombre);
		this.setCursoNombre(cursoNombre);
	}
	
	public String getAsignaturaNombre() {
		return asignaturaNombre;
	}
	public void setAsignaturaNombre(String asignaturaNombre) {
		this.asignaturaNombre = asignaturaNombre;
	}
	public String getCursoNombre() {
		return cursoNombre;
	}
	public void setCursoNombre(String cursoNombre) {
		this.cursoNombre = cursoNombre;
	}
}
