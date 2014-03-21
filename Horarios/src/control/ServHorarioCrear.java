package control;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Asignatura;
import modelo.GestorBD;
import modelo.Horario;
import modelo.Horario_Asig;
import modelo.Persona;
import modelo.Rango_horas;
import utils.ServletUtil;

public class ServHorarioCrear extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServHorarioCrear() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServHorarioCrear");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		HttpSession sesion = request.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		
		int mes=0;
		int anio=0;
		int numPorciones=0;
		int idCurso=0;
		
		if (request.getParameter("idHorario")!=null) {
			GestorBD gestorBD = new GestorBD(descripcionUsuario);
			Horario horario = gestorBD.buscarHorario(Integer.parseInt(request.getParameter("idHorario")));
			mes = horario.getMes();
			anio = horario.getAnio();
			numPorciones = horario.getNumPorciones();
			idCurso = horario.getIdCurso();
			gestorBD.desconectar();
		} else {
			mes = Integer.parseInt(request.getParameter("selMes"));
			anio = Integer.parseInt(request.getParameter("anio"));
			numPorciones = Integer.parseInt(request.getParameter("numPorciones"));
			idCurso = Integer.parseInt(request.getParameter("selCurso"));
		}
				
		System.out.println("IdCurso: " + idCurso);
		
		StringBuilder conTabla = new StringBuilder();
		conTabla = ServletUtil.contenidoTablaHTML(idCurso, mes, anio, numPorciones, descripcionUsuario, usuarioActivo.getIdPerfil());	
		
		// se cargan en un vector las asignaturas del curso
		GestorBD gestorBD = new GestorBD(descripcionUsuario);
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
