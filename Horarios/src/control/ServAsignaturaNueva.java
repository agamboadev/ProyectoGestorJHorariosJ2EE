package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.ServletUtil;

import modelo.Asignatura;
import modelo.Curso;
import modelo.GestorBD;
import modelo.Persona;

public class ServAsignaturaNueva extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServAsignaturaNueva() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServAsignaturaNueva");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		String nombre = request.getParameter("nombre");
		int durHoras = Integer.parseInt(request.getParameter("durHoras"));
		int idCurso = Integer.parseInt(request.getParameter("idCurso"));
		int idProfesor = Integer.parseInt(request.getParameter("selProfesor"));
		
		Asignatura asignatura = new Asignatura(nombre, durHoras, idCurso, idProfesor);
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);
		boolean insertado= gestorBD.insertarAsignatura(asignatura);
		if (insertado) {			
			System.out.println("Asignatura insertada correctamente.");
		} else {
			System.out.println("Error al insertar la asignatura");
		}
		Curso curso = gestorBD.buscarCurso(idCurso);
		if (curso != null) {
			// se cargan en un vector las asignaturas del curso
			Vector<Asignatura> vAsignaturas = new Vector<Asignatura>();
			Vector<Persona> vProfesores = new Vector<Persona>();
			vProfesores = gestorBD.listadoPersonas(GestorBD.PROFESOR);
			vAsignaturas = gestorBD.listadoAsignaturasCurso(idCurso);
			request.setAttribute("curso",curso);
			request.setAttribute("vAsignaturas",vAsignaturas);
			request.setAttribute("vProfesores",vProfesores);
			request.getRequestDispatcher("zonaPrivada/cursoMostrar.jsp").forward(request, response);
		} else {
			System.out.println("-- No se ha encontrado el curso");
			// SE PUEDE CREAR UNA PÁGINA DE ERROR DICIENDO QUE NO SE HA ENCONTRADO EL CURSO
		}
		gestorBD.desconectar();
	}

}
