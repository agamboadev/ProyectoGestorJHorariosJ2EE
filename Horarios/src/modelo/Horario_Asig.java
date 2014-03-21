package modelo;

public class Horario_Asig {
	private int dia;
	private int fila;
	private int idAsignatura;
	private int idHorario;
	
	public Horario_Asig () {
		
	}
	
	public Horario_Asig (int dia, int fila , int idAsignatura, int idHorario) {
		this.setDia(dia);
		this.setFila(fila);
		this.setIdAsignatura(idAsignatura);
		this.setIdHorario(idHorario);
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(int idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public int getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}
}
