package control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Asignatura;
import modelo.Horario;
import modelo.GestorBD;
import modelo.Horario_Asig;
import modelo.Persona;
import modelo.Rango_horas;
import utils.ServletUtil;

public class ServHorarioTratar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServHorarioTratar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServHorarioTratar");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		HttpSession sesion = request.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		
		int mes = Integer.parseInt(request.getParameter("mes"));
		int anio = Integer.parseInt(request.getParameter("anio"));
		int idCurso = Integer.parseInt(request.getParameter("idCurso"));
		int numPorciones = Integer.parseInt(request.getParameter("numPorciones"));
		String strTabla = request.getParameter("strTabla");
		
		int longitudCadena = strTabla.length();
		String separadorCelda = "&";
		String separadorFilaColumna = "$";
		String separadorTexto = "@";
		String[] strCeldas = strTabla.split(separadorCelda);
		
		System.out.println("strTabla: " + strTabla);
		System.out.println("strCeldas.length: " + strCeldas.length);

		for (int i = 0; i < strCeldas.length; i++) {
			// Si el indice de los 'separadores' es correcto, realiza las operaciones necesarias
			if ((strCeldas[i].indexOf(separadorFilaColumna) > 0) && (strCeldas[i].indexOf(separadorTexto) > 0)) {
				System.out.println(strCeldas[i].toString());
				System.out.println("Indice del caracter '" + separadorFilaColumna + "': " + strCeldas[i].indexOf(separadorFilaColumna));
				System.out.println("Indice del caracter '" + separadorTexto + "': " + strCeldas[i].indexOf(separadorTexto));
				System.out.println("Columna: " + strCeldas[i].substring(strCeldas[i].indexOf(separadorFilaColumna) + 1, strCeldas[i].indexOf(separadorTexto)));
				System.out.println("Texto: " + strCeldas[i].substring(strCeldas[i].indexOf(separadorTexto)+1));
			}			
		}
		System.out.println("FIN");		
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);
		// Se comprueba si se hanordenado las celdas
		// Se salta el primer elemento por que no ccontiene nada
		// Se recorre
		// Se salta el primer elemento por que no contiene nada
		String columna = "";
		Rango_horas rango_horas;
		Horario_Asig horario_Asig;
		System.out.println("********** Datos de inserción del horario en la BBDD: ");
		System.out.println("***************** Mes: " + mes);
		System.out.println("***************** Año: " + anio);
		System.out.println("***************** IdCurso: " + idCurso);
		Horario horario = gestorBD.buscarHorario(mes, anio, idCurso);
		if (horario == null) {
			System.out.println("************+++++++ El horario no existe en la BBDD.");
			horario = new Horario(numPorciones, mes, anio, idCurso);
			// Cuando el horario no existe se inserta
			if (gestorBD.insertarHorario(horario)) {
				System.out.println("**********++++ Horario insertado");
			}
		} else {
			System.out.println("********* El horario ya existe: ");
			System.out.println("***************** idHorario: " + horario.getIdHorario());
			System.out.println("***************** NumPorciones: " + horario.getNumPorciones());
			System.out.println("***************** Mes: " + horario.getMes());
			System.out.println("***************** Anio: " + horario.getAnio());
			System.out.println("***************** idCurso: " + horario.getIdCurso());
		}
		horario = gestorBD.buscarHorario(mes, anio, idCurso);
		System.out.println("********** Horario recogido de la base de datos despues de ser insertado: ");
		System.out.println("***************** idHorario: " + horario.getIdHorario());
		System.out.println("***************** NumPorciones: " + horario.getNumPorciones());
		System.out.println("***************** Mes: " + horario.getMes());
		System.out.println("***************** Anio: " + horario.getAnio());
		System.out.println("***************** idCurso: " + horario.getIdCurso());
		int idHorario = horario.getIdHorario();
		int fila = 0;
		System.out.println("--------------- Va a entrar en el if");
		System.out.println("--------------- strCeldas.length: " + strCeldas.length);
		for (int i = 0; i < strCeldas.length; i++) {
			// Si el indice de los 'separadores' es correcto, realiza las operaciones necesarias
			if ((strCeldas[i].indexOf(separadorFilaColumna) > 0) && (strCeldas[i].indexOf(separadorTexto) > 0)) {
				// Se obtiene la fila
				fila = Integer.parseInt(strCeldas[i].substring(0, strCeldas[i].indexOf(separadorFilaColumna)));
				System.out.println("************** Fila: " + fila);
				
				//Una vez que tenemos el id de la fila, podemos pasar a insertar, o modificar las celdas dependiendo de la columna
				
				columna = strCeldas[i].substring(strCeldas[i].indexOf(separadorFilaColumna) + 1, strCeldas[i].indexOf(separadorTexto));
				System.out.println("************** Columna: " + columna);
				if (columna.equals("0")) {
					System.out.println("************** Columna igual a 0");
					// Si la columna es igual a 0, se trata de los rangos de hora
					rango_horas = null;
					System.out.println("********** Datos de para el rango de horas para buscarlo en la BBDD: ");
					System.out.println("***************** fila: " + fila);
					System.out.println("***************** horario.getIdHorario(): " + idHorario);
					rango_horas =gestorBD.buscarRango_horasFila(idHorario, fila);
					if (rango_horas == null) {
						System.out.println("************** No existe el rango de horas");
						// si no existe en la base de datos, se inserta
						rango_horas = new Rango_horas (strCeldas[i].substring(strCeldas[i].indexOf(separadorTexto)+1), fila, idHorario);
						System.out.println("********** Datos del rango de horas a insertar: ");
						System.out.println("***************** texto: " + rango_horas.getTexto());
						System.out.println("***************** fila: " + rango_horas.getFila());
						System.out.println("***************** IdHorario(): " + rango_horas.getIdHorario());
						if (gestorBD.insertarRango_horas(rango_horas)) {
							System.out.println("**********++++ Rango_horas insertado");
						}
					} else {
						System.out.println("************** Existe el rango de horas");
						// Si existe, se modifica
						rango_horas.setTexto(strCeldas[i].substring(strCeldas[i].indexOf(separadorTexto)+1));
						System.out.println("***************** texto a modificar: " + rango_horas.getTexto());
						if (gestorBD.modificarRango_horas(rango_horas)) {
							System.out.println("**********++++ Rango_horas modificado");
						}
					}
				} else {
					System.out.println("************** Columna distinto a 0");
					// si la columna es distinto de 0, se trata de las asignaturas
					horario_Asig = null;
					int dia = Integer.parseInt(columna);
					int idAsignatura = Integer.parseInt(strCeldas[i].substring(strCeldas[i].indexOf(separadorTexto)+1));
					System.out.println("********** Datos busqueda horario_asig: ");
					System.out.println("***************** dia: " + dia);
					System.out.println("***************** fila: " + fila);
					System.out.println("***************** idHorario: " + idHorario);
					horario_Asig =gestorBD.buscarHorario_Asig(dia, fila, idHorario);
					if (horario_Asig == null) {
						System.out.println("********** Horario no encontrado en la base de datos");
						// si no existe en la base de datos, se inserta
						horario_Asig = new Horario_Asig (dia, fila, idAsignatura,idHorario);
						System.out.println("********** Datos del horario_asig a insertar: ");
						System.out.println("***************** dia: " + horario_Asig.getDia());
						System.out.println("***************** fila: " + horario_Asig.getFila());
						System.out.println("***************** idAsignatura: " + horario_Asig.getIdAsignatura());
						System.out.println("***************** idHorario: " + horario_Asig.getIdHorario());
						if (gestorBD.insertarHorario_Asig(horario_Asig)) {
							System.out.println("**********++++ Horario_asig insertado");
						}
					} else {
						System.out.println("********** Horario encontrado en la base de datos");
						// Si existe, se modifica
						horario_Asig.setIdAsignatura(idAsignatura);
						System.out.println("************* Dato a modificar (idAsignatura): " + horario_Asig.getIdAsignatura());
						if (gestorBD.modificarHorario_Asig(horario_Asig)) {
							System.out.println("**********++++ Horario-asig modificado");
						}
					}
				}
				System.out.println(strCeldas[i].toString());
			}
		}					
		
		// Se crea el contenido de la tabla que va a volver a cargar
		StringBuilder conTabla = new StringBuilder();
		conTabla = ServletUtil.contenidoTablaHTML(idCurso, mes, anio, numPorciones, descripcionUsuario, usuarioActivo.getIdPerfil());
		
		// se cargan en un vector las asignaturas del curso
				Vector<Asignatura> vAsignaturas = new Vector<Asignatura>();
				vAsignaturas = gestorBD.listadoAsignaturasCurso(idCurso);
				//Se crea un HashMap con key idPersona y valor Nombre Ap1 y Ap2 del profesor de las asignatura seleccionadas
				HashMap<Integer, String> mapProfesores = new HashMap<Integer, String>();
				Persona profesor;
				for (Asignatura asig : vAsignaturas) {
					if(mapProfesores.get(asig.getIdAsignatura())==null) {
						System.out.println("****** HashMap: profesor todavia no existe");
						profesor = new Persona();
						profesor = gestorBD.profesorAsignatura(asig.getIdAsignatura());
						if (profesor != null) {
							mapProfesores.put(asig.getIdAsignatura(), profesor.getNombre() + " " + profesor.getApellido1() + " " + profesor.getApellido2());
						} else {
							mapProfesores.put(asig.getIdAsignatura(), "No hay profesor asignado");
						}
					} else {
						System.out.println("****** HashMap: profesor existe");
					}
				}			
		gestorBD.desconectar();
		
		request.setAttribute("idCurso",idCurso);
		request.setAttribute("vAsignaturas",vAsignaturas);
		request.setAttribute("mapProfesores",mapProfesores);
	
		request.setAttribute("mes", mes);
		request.setAttribute("anio", anio);
		
		request.setAttribute("numPorciones", numPorciones);
		request.setAttribute("conTabla", conTabla);
		String address = "zonaPrivada/horarioTratar.jsp";		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
