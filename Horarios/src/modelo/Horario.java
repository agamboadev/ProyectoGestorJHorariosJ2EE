package modelo;

public class Horario {
	private int idHorario;
	private int numPorciones;
	private int mes;
	private int anio;
	private int idCurso;
	
	public Horario () {
		
	}
	
	public Horario (int idHorario, int numPorciones, int mes, int anio, int idCurso) {
		this.setIdHorario(idHorario);
		this.setNumPorciones(numPorciones);
		this.setMes(mes);
		this.setAnio(anio);
		this.setIdCurso(idCurso);
	}
	
	public Horario (int numPorciones, int mes, int anio, int idCurso) {
		this.setNumPorciones(numPorciones);
		this.setMes(mes);
		this.setAnio(anio);
		this.setIdCurso(idCurso);
	}
	
	public String fechaMesAnio () {
		return this.getMes() + "/" + this.getAnio();
	}

	public int getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}

	public int getNumPorciones() {
		return numPorciones;
	}

	public void setNumPorciones(int numPorciones) {
		this.numPorciones = numPorciones;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
}
