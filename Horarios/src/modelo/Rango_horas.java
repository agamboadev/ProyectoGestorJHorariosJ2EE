package modelo;

public class Rango_horas {
	private int idRango_horas;
	private String texto;
	private int fila;	
	private int idHorario;
	
	public Rango_horas () {
		
	}
	
	public Rango_horas (int idRango_horas, String texto, int fila, int idHorario) {
		this.setIdRango_horas(idRango_horas);
		this.setTexto(texto);
		this.setFila(fila);
		this.setIdHorario(idHorario);
	}
	
	public Rango_horas (String texto, int fila, int idHorario) {
		this.setTexto(texto);
		this.setFila(fila);
		this.setIdHorario(idHorario);
	}

	public int getIdRango_horas() {
		return idRango_horas;
	}

	public void setIdRango_horas(int idRango_horas) {
		this.idRango_horas = idRango_horas;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}
}
